package Helper;

public class GameActivity extends TimerActivity{
    public void increaseLevel(int currentLevel, String game)
    {
        sharedPref db = new sharedPref(this);
        if(game.equalsIgnoreCase("alphabet"))
        {
            if(db.getAlphabet() < currentLevel)
                db.setAlphabet(db.getAlphabet()+1);
        }
        else if(game.equalsIgnoreCase("animal"))
        {
            if(db.getAnimal() < currentLevel)
                db.setAnimal(db.getAnimal()+1);
        }
        else if(game.equalsIgnoreCase("shape"))
        {
            if(db.getShapes() < currentLevel)
                db.setShapes(db.getShapes() + 1);
        }
        else if(game.equalsIgnoreCase("color"))
        {
            if(db.getColors() < currentLevel)
                db.setColors(db.getColors()+1);
        }
        else if(game.equalsIgnoreCase("number"))
        {
            if(db.getNumber() < currentLevel)
                db.setNumber(db.getNumber()+1);
        }
    }
}
