package Helper;

public class gameMenuHelper {
    static String difficulty;
    static int level;
    static String game;
    public gameMenuHelper()
    {
    }
    public gameMenuHelper(String difficulty, String game)
    {
        this.difficulty = difficulty;
        this.game = game;
    }
    public void setDifficulty(String difficulty)
    {
        this.difficulty = difficulty;
    }

    public void setGame(String games)
    {
        game = games;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }

    public String getGame()
    {
        return game;
    }
    public String getDifficulty() {
        if (difficulty == null || difficulty.isEmpty()) {
            return easyDiff();
        }
        return difficulty;
    }
    public int getLevel() {return level;}

    public String easyDiff(){return "EASY";}
    public String mediumDiff(){return  "MEDIUM";}
    public String hardDiff(){return "HARD";}

    public String alphabet() {return "ALPHABETS";}
    public String number() {return "NUMBERS";}
    public String colors() {return "COLOR";}
    public String shape() {return "SHAPE";}
    public String animal() {return "ANIMAL";}
}
