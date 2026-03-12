package ticketmanagement;

public class Person {
    private float height; 
    private Color eyeColor; 
    private Color hairColor; 
    private Country nationality; 

    public Person(float height, Color eyeColor, Color hairColor, Country nationality) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public float getHeight() { return height; }
    public Color getEyeColor() { return eyeColor; }
    public Color getHairColor() { return hairColor; }
    public Country getNationality() { return nationality; }

    @Override
    public String toString() {
        return String.format("Person{height=%.2f, eyeColor=%s, hairColor=%s, nationality=%s}",
                height, eyeColor, hairColor, nationality);
    }
}