import java.io.File;
import java.util.Scanner;

public class Save {
    public void loadSave(File loadPath) {
        try {
            Scanner scanner = new Scanner(loadPath);
            while (scanner.hasNext()) {
                GamePanel.coins = scanner.nextInt();
                Values.creeperWalkSpeed = scanner.nextInt();
                GamePanel.killsToWin = scanner.nextInt();
                int i = 0;
                while (i < GamePanel.room.fields.length) {
                    int j = 0;
                    while (j < GamePanel.room.fields[0].length) {
                        GamePanel.room.fields[i][j].groundId = scanner.nextInt();
                        j++;
                    }

                    i++;
                }

                i = 0;
                while (i < GamePanel.room.fields.length) {
                    int j = 0;
                    while (j < GamePanel.room.fields[0].length) {
                        GamePanel.room.fields[i][j].airId = scanner.nextInt();
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
