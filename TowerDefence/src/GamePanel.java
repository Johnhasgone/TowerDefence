import javax.swing.*;
import java.awt.*;
import java.io.File;

/** Main running component with all the game's physics.  */

public class GamePanel extends JPanel implements Runnable {
    public Thread thread = new Thread(this);

    public static int myWidth, myHeight;
    public static int coins;
    public static int gamerHealth;
    public static int killed = 0, killsToWin = 0, level = 1;
    public static int winTime = 4000, winFrame = 0;

    public static boolean isFirst = true;
    public static boolean isWinner = false;

    public static Point mouseEvent = new Point(0, 0);

    public static World world;
    public static Load load;
    public static Shop shop;
    public static Creeper[] creepers = new Creeper[100];

    public static Image[] groundImages = new Image[2];
    public static Image[] airImages = new Image[5];
    public static Image[] resImages = new Image[4];
    public static Image[] creeperImages = new Image[3];


    public GamePanel(Frame frame) {
        frame.addMouseListener(new KeyHandler());
        frame.addMouseMotionListener(new KeyHandler());
        thread.start();
    }

    public static void hasWon() {
        if (killed == killsToWin) {
            isWinner = true;
            killed = 0;
        }
    }

    public void define() {
        world = new World();
        load = new Load();
        shop = new Shop();
        gamerHealth = Values.playerHealth;



        groundImages[0] = new ImageIcon("images/space.png").getImage();
        groundImages[1] = new ImageIcon("images/spaceWay.jpg").getImage();
        airImages[0] = new ImageIcon("images/earth.png").getImage();
        airImages[1] = new ImageIcon("images/trash.png").getImage();
        airImages[2] = new ImageIcon("images/defender.png").getImage();
        airImages[3] = new ImageIcon("images/defender2.png").getImage();
        airImages[4] = new ImageIcon("images/defender3.png").getImage();

        resImages[0] = new ImageIcon("images/cell.png").getImage();
        resImages[1] = new ImageIcon("images/life.png").getImage();
        resImages[2] = new ImageIcon("images/coin.png").getImage();
        resImages[3] = new ImageIcon("images/mob.png").getImage();

        creeperImages[0] = new ImageIcon("images/mob.png").getImage();
        creeperImages[1] = new ImageIcon("images/doom.png").getImage();
        creeperImages[2] = new ImageIcon("images/ghost.png").getImage();

        load.loadLevel(new File("save/level" + level + ".jhg"));

        int i = 0;
        while (i < creepers.length) {
            creepers[i] = new Creeper();
            i++;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isFirst) {
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        g.setColor(new Color(56, 100, 67));
        g.fillRect(0, 0, getWidth(), getHeight());
        world.draw(g);

        int i = 0;
        while (i < creepers.length) {
            if (creepers[i].inGame) {
                creepers[i].draw(g);
            }
            i++;
        }

        shop.draw(g);

        /* prints "GAME OVER */

        if (gamerHealth < 1) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(Color.RED);
            g.setFont(new Font("Helvetica", Font.BOLD, 26));
            g.drawString(Values.over, MainWindow.size.width/2 - Values.over.length()/2*17, MainWindow.size.height/2);
        }

        /* prints Congratulations to player */

        if (isWinner) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Helvetica", Font.BOLD, 26));
            if (level == Values.maxLevel) {
                g.drawString(Values.absoluteWinner, MainWindow.size.width/2 - Values.absoluteWinner.length()/2*15, 300);
            } else {
                g.drawString(Values.partWinner, MainWindow.size.width/2 - Values.partWinner.length()/2*15, 300);
            }
        }
    }

    public int spawnTime = Values.spawnTime, spawnFrame = Values.spawnFrame;
    public void creeperSpawner() {
        if (spawnFrame >= spawnTime) {
            int i = 0;
            while (i < creepers.length) {
                if (!creepers[i].inGame) {
                    if (level == 5 && Math.random() < 0.2) {
                        creepers[i].spawnCreeper(Values.creeperBoss);
                    }
                    else if(Math.random() < 0.5 || i%2 == 1) {
                        creepers[i].spawnCreeper(Values.creeperInvader);
                    }
                    else if (Math.random() >= 0.5 || i%2 == 0) {
                        creepers[i].spawnCreeper(Values.creeperDoom);
                    }
                    break;
                }
                i++;
            }

            spawnFrame = 0;
        } else {
            spawnFrame++;
        }
    }


    public void run() {
        while (true) {
            if (!isFirst && gamerHealth > 0 && !isWinner) {
                world.physic();
                creeperSpawner();
                int i = 0;
                while (i < creepers.length) {
                    if (creepers[i].inGame) {
                        try {
                            creepers[i].creeperPhysic();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
                }
            } else {
                if (isWinner) {
                    if (winFrame >= winTime) {
                        if (level == Values.maxLevel) {
                            System.exit(0);
                        } else {
                            level++;
                            define();
                            isWinner = false;

                        }
                        winFrame = 0;
                    } else {
                        winFrame++;
                    }
                }
            }
            repaint();

            try {
                Thread.sleep(1);
            } catch (Exception e) {}
        }
    }
}
