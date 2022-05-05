package t1u7a1n2.labwork;

/**
 * The class-field in LabWork class
 */
public class Coordinates {
    private Long x; //Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates() {
    }
    public Coordinates(Long x, Double y) throws RuntimeException {
        if (x != null && y!= null) {
            this.x = x;
            this.y = y;
        } else {
            throw new RuntimeException("Coordinates value can't be null.");
        }
    }

    public void setX(Long x) throws RuntimeException {
        if (x != null) {
            this.x = x;
        }else {
            throw new RuntimeException("X can't be null.");
        }
    }
    public Long getX() {
        return x;
    }

    public void setY(Double Y) throws RuntimeException {
        if (y != null) {
            this.y = y;
        } else {
            throw new RuntimeException("Y can't be null.");
        }
    }
    public Double getY() {
        return y;
    }
}
