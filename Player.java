public class Player {
    // initialize name and set starting score to 0
    private String name;
    private int score = 0;

    // setter and getter for player's name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // each turn, this method is called to add selected number to player's score
    public void addPoints(int points) {
        score += points;
    }

    // method to reset score to 0 if it is above 30
    public void resetScore() {
        score = 0;
    }

    // getter for score
    public int getScore() {
        return score;
    }
}