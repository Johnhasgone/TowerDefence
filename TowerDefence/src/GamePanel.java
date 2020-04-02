import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GamePanel extends JPanel implements Runnable {
    public Thread thread = new Thread(this);

    public static int myWidth, myHeight;
    public static int coins;
    public static int health;
    public static int killed = 0, killsToWin = 0, level = 1;
    public static int winTime = 4000, winFrame = 0;

    public static boolean isFirst = true;
    public static boolean isDebug = false;
    public static boolean isWinner = false;

    public static Point mse = new Point(0, 0);

    public static Room room;
    public static Save save;
    public static Store store;
    public static Enemy[] mobs = new Enemy[100];                //set number of enemies in load file

    public static Image[] tilesetGround = new Image[100];
    public static Image[] tilesetAir = new Image[100];
    public static Image[] tilesetRes = new Image[100];          //not 100
    public static Image[] tilesetMob = new Image[100];


    public GamePanel(Frame frame) {
        //super(new BorderLayout());
        frame.addMouseListener(new KeyHandle());
        frame.addMouseMotionListener(new KeyHandle());
        thread.start();
    }

    public static void hasWon() {
        if (killed == killsToWin) {
            isWinner = true;
            killed = 0;
        }
    }

    public void define() {
        room = new Room();
        save = new Save();
        store = new Store();
        health = 100;



        tilesetGround[0] = new ImageIcon("images/space.png").getImage();
        tilesetGround[1] = new ImageIcon("images/spaceWay.jpg").getImage();
        tilesetAir[0] = new ImageIcon("images/earth.png").getImage();
        tilesetAir[1] = new ImageIcon("images/trash.png").getImage();
        tilesetAir[2] = new ImageIcon("images/defender.png").getImage();
        tilesetAir[3] = new ImageIcon("images/defender2.png").getImage();
        tilesetAir[4] = new ImageIcon("images/defender3.png").getImage();

        tilesetRes[0] = new ImageIcon("images/cell.png").getImage();
        tilesetRes[1] = new ImageIcon("images/life.png").getImage();
        tilesetRes[2] = new ImageIcon("images/coin.png").getImage();
        tilesetRes[3] = new ImageIcon("images/mob.png").getImage();

        tilesetMob[0] = new ImageIcon("images/mob.png").getImage();

//        int i = 0;
//        while (i < tilesetGround.length) {
//
//            tilesetGround[i] = createImage(new FilteredImageSource(tilesetGround[i].getSource(),
//                                            new CropImageFilter(0, 52*i, 52, 52)));
//            i++;
//        }
//        i = 0;
//        while (i < tilesetAir.length) {
//            tilesetAir[i] = new ImageIcon("images/tilesetAir.jpg").getImage();
//            tilesetAir[i] = createImage(new FilteredImageSource(tilesetAir[i].getSource(),
//                    new CropImageFilter(0, 52*i, 52, 52)));
//            i++;
//        }

        save.loadSave(new File("save/mission" + level + ".jhg"));

        int i = 0;
        while (i < mobs.length) {
            mobs[i] = new Enemy();
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
        g.fillRect(0, 0, getWidth(), getHeight());     //LOOK
        g.setColor(new Color(10, 20, 70));
        g.drawLine(room.blocks[0][0].x - 1, 0, room.blocks[0][0].x - 1,
                room.blocks[Room.worldHeight-1][0].y + Room.blockSize + 1); // DRAWING THE LEFT LINE
        g.drawLine(room.blocks[0][Room.worldWidth - 1].x + Room.blockSize + 1, 0,
                room.blocks[0][Room.worldWidth - 1].x + Room.blockSize + 1,
                room.blocks[Room.worldHeight-1][Room.worldWidth - 1].y + Room.blockSize + 1); // DRAWING THE right LINE
        g.drawLine(room.blocks[0][0].x - 1, room.blocks[Room.worldHeight-1][0].y + Room.blockSize + 1,
                room.blocks[0][Room.worldWidth - 1].x + Room.blockSize + 1,
                room.blocks[Room.worldHeight-1][Room.worldWidth - 1].y + Room.blockSize + 1); // DRAWING THE BOTTOM LINE

        room.draw(g);                                          // Drawing the room

        int i = 0;
        while (i < mobs.length) {
            if (mobs[i].inGame) {
                mobs[i].draw(g);
            }
            i++;
        }

        store.draw(g);

        if (health < 1) {
            g.setColor(new Color(240, 20, 20));
            //g.fillRect(0, 0, myWidth, myHeight); // CHANGE COLOR OF EVERYTHING
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            g.drawString("GAME OVER", 340, 300);
        }

        if (isWinner) {
            g.setColor(Color.WHITE);
            //g.fillRect(0, 0, myWidth, myHeight); // CHANGE COLOR OF EVERYTHING
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            if (level == Values.maxLevel) {
                g.drawString("YOU WON THE WHOLE GAME!!!", 340, 300);
            } else {
                g.drawString("YOU WIN!!!", 340, 300);                   // Window with exit or next level
            }
        }
    }

    public int spawnTime = Values.spawnTime, spawnFrame = Values.spawnFrame;
    public void mobSpawner() {
        if (spawnFrame >= spawnTime) {
            int i = 0;
            while (i < mobs.length) {
                if (!mobs[i].inGame) {
                    mobs[i].spawnMob(Values.mobGreen);
                    break;
                }
                i++;
            }

            spawnFrame = 0;
        } else {
            spawnFrame++;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!isFirst && health > 0 && !isWinner) {
                room.physic();
                mobSpawner();
                int i = 0;
                while (i < mobs.length) {
                    if (mobs[i].inGame) {
                        mobs[i].physic();
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
            repaint();                              //LOOK

            try {
                Thread.sleep(1);
            } catch (Exception e) {}
        }
    }
}
