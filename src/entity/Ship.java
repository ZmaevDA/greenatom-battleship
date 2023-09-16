package entity;

import state.ShipType;

public class Ship {
    private int hp;
    private final ShipType shipType;

    public Ship(int hp, ShipType shipType) {
        this.hp = hp;
        this.shipType = shipType;
    }

    public int getHp() {
        return hp;
    }

    public void decreaseHp() {
        this.hp--;
    }

    public ShipType getShipType() {
        return shipType;
    }

    @Override
    public String toString() {
        switch (shipType) {
            case AIRCRAFT_CARRIER:
                return "Авианосец";
            case CRUISER:
                return "Крейсер";
            case DESTROYER:
                return "Эсминец";
            case MOTOR_BOAT:
                return "Катер";
            default:
                return shipType.toString();
        }
    }
}