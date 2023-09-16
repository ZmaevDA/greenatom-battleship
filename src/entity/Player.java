package entity;

import java.util.List;

public class Player {
    private String nickname;
    private List<Ship> shipList;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Адмирал " + nickname;
    }
}
