import java.awt.*;

public class Room {
    public static int worldWidth = Values.width;
    public static int worldHeight = Values.height;
    public static int fieldSize = Values.blockSize;

    public Field[][] fields;

    public Room() {
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

    public void physic() {          //WHAT IS IT
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
