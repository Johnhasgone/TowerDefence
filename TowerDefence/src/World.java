import java.awt.*;

/** World contains fields with game objects, initialises and sets them */

public class World {
    public static int worldWidth = Values.width;
    public static int worldHeight = Values.height;
    public static int fieldSize = Values.blockSize;

    public Field[][] fields;

    public World() {
        define();
    }

    private void define() {
        fields = new Field[worldHeight][worldWidth];

        int i = 0;
        while (i < fields.length) {
            int j = 0;
            while (j < fields[i].length) {
                fields[i][j] = new Field(GamePanel.myWidth / 2 - worldWidth * fieldSize / 2 + j * fieldSize,
                                         i * fieldSize, fieldSize, fieldSize, Values.ground, Values.air);
                j++;
            }
            i++;
        }
    }

    public void draw(Graphics g) {
        int i = 0;
        while (i < fields.length) {
            int j = 0;
            while (j < fields[i].length) {
                fields[i][j].draw(g);
                j++;
            }
            i++;
        }
        i = 0;
        while (i < fields.length) {
            int j = 0;
            while (j < fields[i].length) {
                fields[i][j].fight(g);
                j++;
            }
            i++;
        }
    }

    public void physic() {
        int i = 0;
        while (i < fields.length) {
            int j = 0;
            while (j < fields[i].length) {
                fields[i][j].physic();
                j++;
            }
            i++;
        }
    }
}
