package ticketmanagement.models;

/**
 * Человек (опционально у билета).
 */
public class Person {
    private final float height;
    private final EyeColor eyeColor;
    private final HairColor hairColor;
    private final Country nationality;

    public Person(float height, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public float getHeight() {
        return height;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return String.format("Person{height=%.2f, eyeColor=%s, hairColor=%s, nationality=%s}",
                height, eyeColor, hairColor, nationality);
    }
}
