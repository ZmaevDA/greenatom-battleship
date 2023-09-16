package entity;

import state.ShipType;

public class Ship {
    private int hp;
    private ShipType shipType;

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
}