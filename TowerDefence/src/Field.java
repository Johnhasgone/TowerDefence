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
                                width + defenderSquareSize, height + defenderSquareSize);
        this.groundId = groundId;
        this.airId = airId;
    }

    public void draw(Graphics g) {
        g.drawImage(GamePanel.tilesetGround[groundId], x, y, width, height, null);

        if (airId != Values.air)
            g.drawImage(GamePanel.tilesetAir[airId], x, y, width, height, null);
    }

    public void physic() {
        if (shot != -1 && defenderSquare.intersects(GamePanel.mobs[shot]))
            shooting = true;
        else
            shooting = false;
        if (!shooting) {
            if (airId == Values.airDefender1 || airId == Values.airDefender2 || airId == Values.airDefender3) {
                int i = 0;
                while (i < GamePanel.mobs.length) {
                    if (GamePanel.mobs[i].inGame) {
                        if (defenderSquare.intersects(GamePanel.mobs[i])) {
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

        if (shooting) {
            if (healthLosingFrame >= healthLosingTime) {
                if (airId == Values.airDefender1)
                    GamePanel.mobs[shot].EnemyLooseHealth(Values.shotStrength1);
                if (airId == Values.airDefender2)
                    GamePanel.mobs[shot].EnemyLooseHealth(Values.shotStrength2);
                if (airId == Values.airDefender3)
                    GamePanel.mobs[shot].EnemyLooseHealth(Values.shotStrength3);
                healthLosingFrame = 0;
            } else
                healthLosingFrame++;


            if (!GamePanel.mobs[shot].inGame) {
                shooting = false;
                shot = -1;
                GamePanel.hasWon();
            }
        }
    }

    public void getMoney(int mobId) {
        GamePanel.coins += Values.deathReward[mobId];
    }

    public void fight(Graphics g) {
        if (shooting) {
            if (airId == Values.airDefender1)
                g.setColor(Color.GREEN);
            if (airId == Values.airDefender2)
                g.setColor(Color.YELLOW);
            if (airId == Values.airDefender3)
                g.setColor(Color.RED);
            g.drawLine(x + width/2, y + height/2,
                    GamePanel.mobs[shot].x + GamePanel.mobs[shot].width/2,
                    GamePanel.mobs[shot].y + GamePanel.mobs[shot].height/2);

        }
    }
}
