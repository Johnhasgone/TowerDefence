import java.awt.*;

public class Enemy extends Rectangle {
    public int xC, yC;
    public int mobSize = Room.blockSize;
    public int mobWalk = 0;
    public int upward = 0, downward = 1, left = 3, right = 2;
    public int direction = right;
    public int mobId = Values.mobAir;
    public boolean inGame = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasLeft = false;
    public boolean hasRight = false;
    public int health;
    public int healthSpace = 1, healthHeight = 6;

    public Enemy() {

    }

    public void spawnMob(int mobId) {
        int i = 0;
        while (i < GamePanel.room.blocks.length) {
            if (GamePanel.room.blocks[i][0].groundId == Values.road) {
                setBounds(GamePanel.room.blocks[i][0].x, GamePanel.room.blocks[i][0].y, mobSize, mobSize);
                xC = 0;
                yC = i;
            }
            i++;
        }

        this.mobId = mobId;
        this.health = mobSize;
        inGame = true;
    }

    public void deleteMob() {
        inGame = false;
        direction = right;
        mobWalk = 0;
    }
    public void killMob() {
        inGame = false;
        direction = right;
        mobWalk = 0;

        GamePanel.room.blocks[0][0].getMoney(mobId);
    }

    public void looseHealth() {
        GamePanel.health--;
    }

    public int walkFrame = 0, walkSpeed = 5;
    public void physic() {
        if (walkFrame >= walkSpeed) {
            if (direction == right) {
                x++;
            } else if (direction == upward) {
                y--;
            } else if (direction == downward) {
                y++;
            } else if (direction == left) {
                x--;
            }
            mobWalk++;

            if (mobWalk == Room.blockSize) {
                if (direction == right) {
                    xC++;
                    hasRight = true;
                } else if (direction == left) {
                    xC--;
                    hasLeft = true;
                }
                else if (direction == upward) {
                    yC--;
                    hasUpward = true;
                } else if (direction == downward) {
                    yC++;
                    hasDownward = true;
                }
                if (!hasUpward) {
                    try {
                        if (GamePanel.room.blocks[yC + 1][xC].groundId == Values.road) {
                            direction = downward;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }
                if (!hasDownward) {
                    try {
                        if (GamePanel.room.blocks[yC - 1][xC].groundId == Values.road) {
                            direction = upward;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }
                if (!hasLeft) {
                    try {
                        if (xC != Room.worldWidth - 1 && GamePanel.room.blocks[yC][xC + 1].groundId == Values.road) {
                            direction = right;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }

                if (!hasRight) {
                    try {
                        if (GamePanel.room.blocks[yC][xC - 1].groundId == Values.road) {
                            direction = left;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                                         //TRY CATCH
                }

                if (GamePanel.room.blocks[yC][xC].airId == Values.airEarth) {
                    deleteMob();
                    looseHealth();
                }

                hasUpward = false;
                hasDownward = false;
                hasLeft = false;
                hasRight = false;
                mobWalk = 0;
            }
            walkFrame = 0;
        } else {
            walkFrame++;
        }
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(GamePanel.tilesetMob[mobId], x, y, width, height, null);
//Health bar
            g.setColor(Color.BLACK);
            g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

            g.setColor(Color.MAGENTA);
            g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x, y - (healthSpace + healthHeight), health - 1, healthHeight - 1);
        }
    }

    public void EnemyLooseHealth(int i) {
        health -= i;

        if (health == 0)
            killMob();
    }
}
