import java.io.File;
import java.util.Scanner;

/** Load helps to work with different levels of game (10 levels for now).
 * Levels contained in *.jhg files, which are scanned by the class and
 * setting game parameters like the way, speed of creepers,
 * amount of creepers to win, and starting amount of coins */

public class Load {
    public void loadLevel(File loadPath) {
        try {
            Scanner scanner = new Scanner(loadPath);
            while (scanner.hasNext()) {
                GamePanel.coins = scanner.nextInt();
                Values.creeperWalkSpeed = scanner.nextInt();
                GamePanel.killsToWin = scanner.nextInt();
                int i = 0;
                while (i < GamePanel.world.fields.length) {
                    int j = 0;
                    while (j < GamePanel.world.fields[0].length) {
                        GamePanel.world.fields[i][j].groundId = scanner.nextInt();
                        j++;
                    }

                    i++;
                }

                i = 0;
                while (i < GamePanel.world.fields.length) {
                    int j = 0;
                    while (j < GamePanel.world.fields[0].length) {
                        GamePanel.world.fields[i][j].airId = scanner.nextInt();
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
