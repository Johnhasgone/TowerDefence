import java.awt.*;

/** Block is a place to set towers, to make roads to move enemies
 * Two types of blocks -  */

public class Block extends Rectangle {
    public Rectangle towerSquare;
    public int towerSquareSize = 120;           //replace to Values, use different values for different towers(sniper for bigger ones)
    public int groundId;
    public int airId;
    public static int shotStrength;
    public int shot = -1;
    public boolean shooting = false;
    public int loseTime = 100, loseFrame = 0;       //health loosing time


    public Block(int x, int y, int width, int height, int groundId, int airId) {
        setBounds(x , y, width, height);                 // LOOK

        towerSquare = new Rectangle(x - towerSquareSize/2, y - towerSquareSize/2, width + towerSquareSize, height + towerSquareSize);
        this.groundId = groundId;
        this.airId = airId;
    }

    public void draw(Graphics g) {
        g.drawImage(GamePanel.tilesetGround[groundId], x, y, width, height, null);                // LOOK

        if (airId != Values.air) {
            g.drawImage(GamePanel.tilesetAir[airId], x, y, width, height, null);


        }
    }

    public void physic() {
        if (shot != -1 && towerSquare.intersects(GamePanel.mobs[shot])) {
            shooting = true;
        } else {
            shooting = false;
        }
        if (!shooting) {
            if (airId == Values.airDefender1 || airId == Values.airDefender2 || airId == Values.airDefender3) {
                int i = 0;
                while (i < GamePanel.mobs.length) {
                    if (GamePanel.mobs[i].inGame) {
                        if (towerSquare.intersects(GamePanel.mobs[i])) {
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
            if (loseFrame >= loseTime) {
                if (airId == Values.airDefender1)
                    GamePanel.mobs[shot].EnemyLooseHealth(Values.shotStrength1);
                if (airId == Values.airDefender2)
                    GamePanel.mobs[shot].EnemyLooseHealth(Values.shotStrength2);
                if (airId == Values.airDefender3)
                    GamePanel.mobs[shot].EnemyLooseHealth(Values.shotStrength3);
                loseFrame = 0;
            } else {
                loseFrame++;
            }


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
//        if (GamePanel.isDebug) {
//            if (airId == Values.airDefender) {
//                g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
//            }
//        }
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
