import java.awt.*;

public class Room {
    public static int worldWidth = Values.width;
    public static int worldHeight = Values.height;
    public static int blockSize = Values.blockSize;

    public Block[][] blocks;

    public Room() {
        define();
    }

    private void define() {
        blocks = new Block[worldHeight][worldWidth];

        int i = 0;
        while (i < blocks.length) {
            int j = 0;
            while (j < blocks[i].length) {
                blocks[i][j] = new Block(GamePanel.myWidth / 2 - worldWidth * blockSize / 2 + j * blockSize,
                                         i * blockSize, blockSize, blockSize, Values.ground, Values.air);
                j++;
            }
            i++;
        }
    }

    public void draw(Graphics g) {
        int i = 0;
        while (i < blocks.length) {
            int j = 0;
            while (j < blocks[i].length) {
                blocks[i][j].draw(g);
                j++;
            }
            i++;
        }
        i = 0;
        while (i < blocks.length) {
            int j = 0;
            while (j < blocks[i].length) {
                blocks[i][j].fight(g);
                j++;
            }
            i++;
        }
    }

    public void physic() {          //WHAT IS IT
        int i = 0;
        while (i < blocks.length) {
            int j = 0;
            while (j < blocks[i].length) {
                blocks[i][j].physic();
                j++;
            }
            i++;
        }
    }
}
