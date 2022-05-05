package labWork;

import java.io.Serializable;

/**
 * The class-field in LabWork class
 */
public class Person implements Serializable {
//    private static final long serialVersionUID = 1L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private float weight; //Значение поля должно быть больше 0
    private Location location; //Поле может быть null

    public Person() {
    }
    public Person(String name, float weight, Location location) {
        this.name = name;
        this.weight = weight;
        this.location = location;
    }

    public void setName(String name) throws RuntimeException {
        if (name != null && name.length() != 0) {
            this.name= name;
        } else {
            throw new RuntimeException("Person's name can't be null or empty line.");
        }
    }
    public String getName() {
        return name;
    }

    public void setWeight(float weight)  throws  RuntimeException {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new RuntimeException("Weight can't be less than 0.");
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }
}
