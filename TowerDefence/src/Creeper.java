import java.awt.*;

/** We have 3 types of creepers, they move on the fields with groundId equals "way"
 * up to the field with airId == airEarth. Then player loses lives depending on what creeper is on the earth field.
 * Killing of different creepers gives different amount of money*/

public class Creeper extends Rectangle {
    public int xField, yField;
    public int creeperSize = Room.fieldSize;
    public int creeperWalk = 0;
    public int up = 0, down = 1, left = 3, right = 2;
    public int direction = right;
    public int creeperId = Values.creeperAir;
    public boolean inGame = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasLeft = false;
    public boolean hasRight = false;
    public int creeperHealth;
    public int healthSpace = 1, healthHeight = 6;

    public Creeper() {

    }

    public void spawnCreeper(int mobId) {
        int i = 0;
        while (i < GamePanel.room.fields.length) {
            if (GamePanel.room.fields[i][0].groundId == Values.way) {
                setBounds(GamePanel.room.fields[i][0].x, GamePanel.room.fields[i][0].y, creeperSize, creeperSize);
                xField = 0;
                yField = i;
            }
            i++;
        }

        this.creeperId = mobId;
        this.creeperHealth = creeperSize;
        inGame = true;
    }

    public void deleteCreeper() {
        inGame = false;
        direction = right;
        creeperWalk = 0;
    }
    public void killCreeper() {
        inGame = false;
        direction = right;
        creeperWalk = 0;
        GamePanel.killed++;
        GamePanel.room.fields[0][0].getMoney(creeperId);
    }

    public void gamerLoseHealth() {
        if (creeperId == Values.mobGreen)
            GamePanel.gamerHealth--;
        if (creeperId == Values.mobYellow) {
            GamePanel.gamerHealth -= 2;
        }
        if (creeperId == Values.mobRed) {
            GamePanel.gamerHealth -= 10;
        }
    }

    public int creeperWalkFrame = 0, creeperWalkSpeed = Values.creeperWalkSpeed;
    public void creeperPhysic() {
        if (creeperWalkFrame >= creeperWalkSpeed) {
            if (direction == right) {
                x++;
            } else if (direction == up) {
                y--;
            } else if (direction == down) {
                y++;
            } else if (direction == left) {
                x--;
            }
            creeperWalk++;

            if (creeperWalk == Room.fieldSize) {
                if (direction == right) {
                    xField++;
                    hasRight = true;
                } else if (direction == left) {
                    xField--;
                    hasLeft = true;
                }
                else if (direction == up) {
                    yField--;
                    hasUpward = true;
                } else if (direction == down) {
                    yField++;
                    hasDownward = true;
                }
                if (!hasUpward) {
                    try {
                        if (GamePanel.room.fields[yField + 1][xField].groundId == Values.way) {
                            direction = down;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }
                if (!hasDownward) {
                    try {
                        if (GamePanel.room.fields[yField - 1][xField].groundId == Values.way) {
                            direction = up;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }
                if (!hasLeft) {
                    try {
                        if (xField != Room.worldWidth - 1 && GamePanel.room.fields[yField][xField + 1].groundId == Values.way) {
                            direction = right;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }

                if (!hasRight) {
                    try {
                        if (GamePanel.room.fields[yField][xField - 1].groundId == Values.way) {
                            direction = left;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }

                if (GamePanel.room.fields[yField][xField].airId == Values.airEarth) {
                    deleteCreeper();
                    gamerLoseHealth();
                }

                hasUpward = false;
                hasDownward = false;
                hasLeft = false;
                hasRight = false;
                creeperWalk = 0;
            }
            creeperWalkFrame = 0;
        } else {
            creeperWalkFrame++;
        }
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(GamePanel.tilesetMob[creeperId], x, y, width, height, null);
            g.setColor(Color.BLACK);
            g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

            g.setColor(Color.GREEN);
            g.fillRect(x, y - (healthSpace + healthHeight), creeperHealth, healthHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x, y - (healthSpace + healthHeight), creeperHealth - 1, healthHeight - 1);
        }
    }

    public void EnemyLooseHealth(int i) {
        creeperHealth -= i;

        if (creeperHealth <= 0)
            killCreeper();
    }
}
