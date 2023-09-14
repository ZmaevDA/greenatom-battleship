package entity;

public enum FieldType {
    AIRCRAFT_CARRIER(4),
    CRUISER(3),
    DESTROYER(2),
    MOTOR_BOAT(1),
    EMPTY(0),
    MISS(-1),

    BLOCKED(-2);

    private final int length;

    FieldType(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
