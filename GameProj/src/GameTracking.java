public class GameTracking {
    private Boolean gameOn;

    public GameTracking() {
        gameOn = false;
    }

    public void setGameOn (Boolean temp) {
            gameOn = temp;
    }
    public Boolean getGameOn() {
        return gameOn;
    }
}
