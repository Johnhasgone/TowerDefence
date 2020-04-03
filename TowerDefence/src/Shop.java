import java.awt.*;

/** Shop with three types of defenders, different prices and different strength of shot.
 * Contains icons for life, coins, creepers to kill on the level and number of level
 * Choosing a defender goes with a click on it and grabbing to place to set*/

public class Shop {
    public static int shopWidth = 4; //4 предмета в магазине
    public static int buttonSize = 80;
    public static int cellSpace = 2; //пространство между иконками в Store
    public static int awayFromRoom = 32; // расположение Store
    public static int iconSize = 18; // для расположения иконок монет и здоровья
    public static int iconSpace = 3;
    public static int iconTextY = 15;
    public static int itemIn = 4;
    public static int heldId = -1;
    public static int realId = -1;
    public static int[] buttonId = {Values.airDefender1, Values.airDefender2, Values.airDefender3, Values.airTrash};
    public static int[] buttonPrice = {10, 20, 30, 0};

    public Rectangle[] buttons = new Rectangle[shopWidth];
    public Rectangle buttonHealth; //добавление иконки здоровья
    public Rectangle buttonCoins; //добавление иконки для монет
    public Rectangle buttonKills;

    public boolean holdsItem = false;

    public Shop() {
        define();
    }

    public void click(int mouseButton) {

        /* holding the defender */

        if (mouseButton == 1) {
            int i = 0;
            while (i < buttons.length) {
                if (buttons[i].contains(GamePanel.mouseEvent)) {
                    if (buttonId[i] != Values.air) {
                        if (buttonId[i] == Values.airTrash) {
                            holdsItem = false;
                        } else {
                            heldId = buttonId[i];
                            realId = i;
                            holdsItem = true;
                        }
                    }
                }
                i++;
            }

            /* Setting the defender on the ground */

            if (holdsItem) {
                if (GamePanel.coins >= buttonPrice[realId]) {
                    i = 0;
                    while (i < GamePanel.world.fields.length) {
                        int j = 0;
                        while (j < GamePanel.world.fields[i].length) {
                            if (GamePanel.world.fields[i][j].contains(GamePanel.mouseEvent)) {
                                if (GamePanel.world.fields[i][j].groundId != Values.way && GamePanel.world.fields[i][j].airId == Values.air) {
                                    GamePanel.world.fields[i][j].airId = heldId;
                                    GamePanel.coins -= buttonPrice[realId];
                                }
                            }
                            j++;
                        }

                        i++;
                    }
                }
            }
        }

        /* Removing the defender */

        if (mouseButton == 3) {
            if (!holdsItem) {
                int i = 0;
                while (i < GamePanel.world.fields.length) {
                    int j = 0;
                    while (j < GamePanel.world.fields[i].length) {
                        if (GamePanel.world.fields[i][j].contains(GamePanel.mouseEvent)) {
                            if (GamePanel.world.fields[i][j].groundId != Values.way && GamePanel.world.fields[i][j].airId != Values.air) {
                                GamePanel.world.fields[i][j].airId = Values.air;
                            }
                        }
                        j++;
                    }

                    i++;
                }

            }
        }
    }
    // инициализация и настройка размера кнопок для магазина
    private void define() {
        int i = 0;
        while (i < buttons.length) {
            buttons[i] = new Rectangle(GamePanel.myWidth - 3 * awayFromRoom - cellSpace,(shopWidth * (buttonSize + cellSpace))/4 +
                    (buttonSize + cellSpace) * i,  buttonSize, buttonSize);
            i++;
        }
        // инициализация и настройка размера иконок
        buttonHealth = new Rectangle(buttons[0].x, buttons[3].y + buttons[3].height - (iconSize + 3) * 3+ 3 * awayFromRoom, iconSize, iconSize);
        buttonCoins = new Rectangle(buttons[0].x, buttons[3].y + buttons[3].height - (iconSize + 3) * 2 + 3 * awayFromRoom , iconSize, iconSize);
        buttonKills = new Rectangle(buttons[0].x, buttons[3].y + buttons[3].height - iconSize - 2 + 3 * awayFromRoom , iconSize, iconSize);
    }

    public void draw(Graphics g) {

        int i = 0;
        while (i < buttons.length) {
            if (buttons[i].contains(GamePanel.mouseEvent)) {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height);
            }

            g.drawImage(GamePanel.resImages[0], buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, null);
            if (buttonId[i] != Values.air) {
                g.drawImage(GamePanel.airImages[buttonId[i]], buttons[i].x + itemIn, buttons[i].y + itemIn, buttons[i].width - itemIn*2, buttons[i].height - itemIn*2, null);
            }
            if (buttonPrice[i] > 0) {
                g.setFont(new Font("Helvetica", Font.BOLD, 14));
                g.setColor(new Color(255, 255, 255));
                g.drawString(buttonPrice[i] + "", buttons[i].x + itemIn, buttons[i].y + iconTextY);
            }
            i++;
        }
        g.drawImage(GamePanel.resImages[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(GamePanel.resImages[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
        g.drawImage(GamePanel.resImages[3], buttonKills.x, buttonKills.y, buttonKills.width, buttonKills.height, null);
        g.setFont(new Font("Helvetica", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        g.drawString("" + GamePanel.gamerHealth, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconTextY);
        g.drawString("" + GamePanel.coins, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
        g.drawString("" + (GamePanel.killsToWin - GamePanel.killed), buttonKills.x + buttonKills.width + iconSpace, buttonKills.y + iconTextY);
        g.drawString("Level: " + GamePanel.level, buttonKills.x, buttonKills.y +iconSize + iconTextY);

        if (holdsItem) {
            g.drawImage(GamePanel.airImages[heldId], GamePanel.mouseEvent.x - (buttons[0].width - itemIn*2)/2 + itemIn, GamePanel.mouseEvent.y - (buttons[0].width - itemIn*2)/2 + itemIn, buttons[0].width - itemIn*2, buttons[0].height - itemIn*2, null);
        }
    }
}
