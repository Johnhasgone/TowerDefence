import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Save {
    public void loadSave(File loadPath) {
        try {
            Scanner scanner = new Scanner(loadPath);
            while (scanner.hasNext()) {
                GamePanel.coins = scanner.nextInt();
                Values.walkSpeed = scanner.nextInt();
                GamePanel.killsToWin = scanner.nextInt();
                int i = 0;
                while (i < GamePanel.room.blocks.length) {
                    int j = 0;
                    while (j < GamePanel.room.blocks[0].length) {
                        GamePanel.room.blocks[i][j].groundId = scanner.nextInt();
                        j++;
                    }

                    i++;
                }

                i = 0;
                while (i < GamePanel.room.blocks.length) {
                    int j = 0;
                    while (j < GamePanel.room.blocks[0].length) {
                        GamePanel.room.blocks[i][j].airId = scanner.nextInt();
                        j++;
                    }

                    i++;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
