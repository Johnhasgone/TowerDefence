import java.awt.*;

/** Field is a place to set defenders, to make roads to move enemies
 * Two indexes of fields -  groundId to differ space and way,
 * airId - to differ empty space from space with defender */

public class Field extends Rectangle {
    public Rectangle defenderSquare;
    public int defenderSquareSize = 120;
    public int groundId;
    public int airId;
    public static int shotStrength;
    public int shot = -1;
    public boolean shooting = false;
    public int healthLosingTime = 100, healthLosingFrame = 0;


    public Field(int x, int y, int width, int height, int groundId, int airId) {
        setBounds(x , y, width, height);
        defenderSquare = new Rectangle(x - defenderSquareSize /2, y - defenderSquareSize /2,
                                width + defenderSquareSize, height + defenderSquareSize); // область поражения для башни
        this.groundId = groundId;
        this.airId = airId;
    }

    public void draw(Graphics g) {
        g.drawImage(GamePanel.groundImages[groundId], x, y, width, height, null);

        if (airId != Values.air)
            g.drawImage(GamePanel.airImages[airId], x, y, width, height, null);
    }
    // процессы при стрельбе башни
    public void physic() {
        if (shot != -1 && defenderSquare.intersects(GamePanel.creepers[shot]))
            shooting = true;
        else
            shooting = false;
        if (!shooting) {
            if (airId == Values.airDefender1 || airId == Values.airDefender2 || airId == Values.airDefender3) {
                int i = 0;
                while (i < GamePanel.creepers.length) {
                    if (GamePanel.creepers[i].inGame) {
                        if (defenderSquare.intersects(GamePanel.creepers[i])) {
                            shooting = true;
                            shot = i;
                            if (airId == Values.airDefender1)
                                shotStrength = Values.shotStrength1;
                            else if (airId == Values.airDefender2)
                                shotStrength = Values.shotStrength2;
                            else if (airId == Values.airDefender3)
                                shotStrength = Values.shotStrength3;
                        }
                    }
                    i++;
                }
            }
        }
        // уменьшение здоровья врага при стрельбе башни
        if (shooting) {
            if (healthLosingFrame >= healthLosingTime) {
                if (airId == Values.airDefender1)
                    GamePanel.creepers[shot].EnemyLooseHealth(Values.shotStrength1);
                if (airId == Values.airDefender2)
                    GamePanel.creepers[shot].EnemyLooseHealth(Values.shotStrength2);
                if (airId == Values.airDefender3)
                    GamePanel.creepers[shot].EnemyLooseHealth(Values.shotStrength3);
                healthLosingFrame = 0;
            } else
                healthLosingFrame++;


            if (!GamePanel.creepers[shot].inGame) {
                shooting = false;
                shot = -1;
                GamePanel.hasWon();
            }
        }
    }

    public void getMoney(int mobId) {
        GamePanel.coins += Values.deathReward[mobId];
    }
    // прорисовка стрельбы
    public void fight(Graphics g) {
        if (shooting) {
            if (airId == Values.airDefender1)
                g.setColor(Color.GREEN);
            if (airId == Values.airDefender2)
                g.setColor(Color.YELLOW);
            if (airId == Values.airDefender3)
                g.setColor(Color.RED);
            g.drawLine(x + width/2, y + height/2,
                    GamePanel.creepers[shot].x + GamePanel.creepers[shot].width/2,
                    GamePanel.creepers[shot].y + GamePanel.creepers[shot].height/2);

        }
    }
}
