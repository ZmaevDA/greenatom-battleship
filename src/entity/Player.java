package entity;

public class Player {
    private final String nickname;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Адмирал " + nickname;
    }
}
