package labWork;

import java.io.Serializable;

/**
 * The class-field in Person class
 */
public class Location implements Serializable {
//    private static final long serialVersionUID = 1L;
    private Integer x; //Поле не может быть null
    private float y;
    private Double z;
    private String name; //Длина строки не должна быть больше 236, Поле может быть null

    public Location() {
    }
    public Location(Integer x, float y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Location(Integer x, float y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public void setX(Integer x) {
        if (x != null) {
            this.x = x;
        } else {
            throw new RuntimeException("X can't be null.");
        }
    }

    public Integer getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setName(String name) {
        if (name.length() <= 236) {
            this.name = name;
        } else {
            throw new RuntimeException("Location's name can't be more 236 char.");
        }
    }
    
    public String getName() {
        return name;
    }
}
