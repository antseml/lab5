package ticketmanagement;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileManager {

    public List<Ticket> loadFromFile(String fileName) throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        StringBuilder json = new StringBuilder();
    
        try (Scanner scanner = new Scanner(new File(fileName), "UTF-8")) {
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Файл не найден: " + fileName);
        } catch (SecurityException e) {
            throw new Exception("Нет прав на чтение файла: " + fileName);
        }

        String jsonStr = json.toString().trim();
        if (jsonStr.isEmpty() || jsonStr.equals("[]")) return tickets;

        jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
        String[] objects = splitJsonObjects(jsonStr);
    
        for (String obj : objects) {
            try {
                Ticket ticket = parseTicket(obj);
                if (ticket != null) tickets.add(ticket);
            } catch (Exception e) {
                System.err.println("Ошибка парсинга билета: " + e.getMessage());
            }
        }
    
        return tickets;
    }

    public void saveToFile(String fileName, Vector<Ticket> tickets) throws Exception {
        StringBuilder json = new StringBuilder("[\n");
        
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            json.append("  {");
            json.append("\"id\":").append(t.getId()).append(",");
            json.append("\"name\":\"").append(escapeJson(t.getName())).append("\",");
            json.append("\"coordinates\":{");
            json.append("\"x\":").append(t.getCoordinates().getX()).append(",");
            json.append("\"y\":").append(t.getCoordinates().getY());
            json.append("},");
            json.append("\"creationDate\":\"").append(t.getCreationDate()).append("\",");
            json.append("\"price\":").append(t.getPrice()).append(",");
            json.append("\"comment\":\"").append(escapeJson(t.getComment())).append("\",");
            json.append("\"type\":\"").append(t.getType()).append("\"");
            
            if (t.getPerson() != null) {
                Person p = t.getPerson();
                json.append(",\"person\":{");
                json.append("\"height\":").append(p.getHeight()).append(",");
                json.append("\"eyeColor\":\"").append(p.getEyeColor()).append("\",");
                json.append("\"hairColor\":\"").append(p.getHairColor()).append("\",");
                json.append("\"nationality\":\"").append(p.getNationality()).append("\"");
                json.append("}");
            } else {
                json.append(",\"person\":null");
            }
            
            json.append("}");
            if (i < tickets.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
            bos.write(json.toString().getBytes());
        } catch (FileNotFoundException e) {
            throw new Exception("Не удалось создать файл: " + fileName);
        } catch (SecurityException e) {
            throw new Exception("Нет прав на запись в файл: " + fileName);
        }
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String[] splitJsonObjects(String json) {
        List<String> objects = new ArrayList<>();
        int depth = 0;
        int start = 0;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') depth++;
            if (c == '}') depth--;
            if (depth == 0 && c == '}' && i + 1 < json.length() && json.charAt(i + 1) == ',') {
                objects.add(json.substring(start, i + 1));
                start = i + 2;
                i++;
            }
        }
        if (start < json.length()) {
            objects.add(json.substring(start));
        }
        
        return objects.toArray(new String[0]);
    }

    private Ticket parseTicket(String json) {
    try {
        json = json.trim();
        
        Long id = null;
        String name = null;
        Double x = null;
        long y = 0;
        LocalDate creationDate = null;
        int price = 0;
        String comment = null;
        TicketType type = null;
        Person person = null;
    
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
        
        String[] fields = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        
        for (String field : fields) {
            String[] keyValue = field.split(":", 2);
            if (keyValue.length < 2) continue;
            
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim();
            
            switch (key) {
                case "id":
                    id = Long.parseLong(value);
                    break;
                case "name":
                    name = value.replace("\"", "");
                    break;
                case "coordinates":
                    if (value.startsWith("{")) {
                        String coordStr = value.substring(1, value.length() - 1);
                        String[] coordFields = coordStr.split(",");
                        for (String cf : coordFields) {
                            String[] ckv = cf.split(":");
                            if (ckv.length < 2) continue;
                            String ckey = ckv[0].trim().replace("\"", "");
                            String cvalue = ckv[1].trim();
                            if (ckey.equals("x")) {
                                x = Double.parseDouble(cvalue);
                            } else if (ckey.equals("y")) {
                                y = Long.parseLong(cvalue);
                            }
                        }
                    }
                    break;
                case "creationDate":
                    creationDate = LocalDate.parse(value.replace("\"", ""));
                    break;
                case "price":
                    price = Integer.parseInt(value);
                    break;
                case "comment":
                    comment = value.replace("\"", "");
                    break;
                case "type":
                    type = TicketType.valueOf(value.replace("\"", ""));
                    break;
                case "person":
                    if (!value.equals("null")) {
                        person = parsePerson(value);
                    }
                    break;
            }
        }
        
            if (id != null && name != null && x != null && creationDate != null && price > 0 && comment != null && type != null) {
                Coordinates coordinates = new Coordinates(x, y);
                return new Ticket(id, name, coordinates, creationDate, price, comment, type, person);
            }
        } catch (Exception e) {
            System.err.println("Ошибка парсинга: " + e.getMessage());
        }
        return null;
    }

    private Person parsePerson(String personStr) {
        try {
            personStr = personStr.trim();
            if (personStr.startsWith("{")) personStr = personStr.substring(1);
            if (personStr.endsWith("}")) personStr = personStr.substring(0, personStr.length() - 1);
            
            Map<String, String> fields = new HashMap<>();
            String[] pairs = personStr.split(",");
            for (String pair : pairs) {
                String[] kv = pair.split(":");
                if (kv.length == 2) {
                    fields.put(kv[0].trim().replace("\"", ""), kv[1].trim().replace("\"", ""));
                }
            }
            
            float height = Float.parseFloat(fields.get("height"));
            Color eyeColor = Color.valueOf(fields.get("eyeColor"));
            Color hairColor = Color.valueOf(fields.get("hairColor"));
            Country nationality = Country.valueOf(fields.get("nationality"));
            
            return new Person(height, eyeColor, hairColor, nationality);
        } catch (Exception e) {
            return null;
        }
    }
}