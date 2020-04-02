import java.awt.*;

public class Store {
    public static int shopWidth = 8;
    public static int buttonSize = 60;
    public static int cellSpace = 2;
    public static int awayFromRoom = 29;
    public static int iconSize = 18;
    public static int iconSpace = 3;
    public static int iconTextY = 15;
    public static int itemIn = 4;
    public static int heldId = -1;
    public static int realId = -1;
    public static int[] buttonId = {Values.airDefender1, Values.airDefender2, Values.airDefender3, Values.air, Values.air, Values.air, Values.air, Values.airTrash};
    public static int[] buttonPrice = {10, 20, 30, 0, 0, 0, 0, 0};

    public Rectangle[] buttons = new Rectangle[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;
    public Rectangle buttonKills;

    public boolean holdsItem = false;

    public Store() {
        define();
    }

    public void click(int mouseButton) {
        if (mouseButton == 1) {
            int i = 0;
            while (i < buttons.length) {
                if (buttons[i].contains(GamePanel.mse)) {
                    if (buttonId[i] != Values.air) {
                        if (buttonId[i] == Values.airTrash) {
                            holdsItem = false;
                        } else {
                            heldId = buttonId[i];
                            realId = i;                 //rewrite
                            holdsItem = true;
                        }
                    }
                }
                i++;
            }
//Setting the tower on the ground
            if (holdsItem) {
                if (GamePanel.coins >= buttonPrice[realId]) {
                    i = 0;
                    while (i < GamePanel.room.blocks.length) {
                        int j = 0;
                        while (j < GamePanel.room.blocks[i].length) {
                            if (GamePanel.room.blocks[i][j].contains(GamePanel.mse)) {
                                if (GamePanel.room.blocks[i][j].groundId != Values.road && GamePanel.room.blocks[i][j].airId == Values.air) {
                                    GamePanel.room.blocks[i][j].airId = heldId;
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
    }

    private void define() {
        int i = 0;
        while (i < buttons.length) {
            buttons[i] = new Rectangle(GamePanel.myWidth/2 - (shopWidth*(buttonSize + cellSpace))/2 +
                    (buttonSize + cellSpace)*i, GamePanel.room.blocks[GamePanel.room.worldHeight - 1][0].y
                    + GamePanel.room.blockSize + awayFromRoom + cellSpace, buttonSize, buttonSize);         // CHANGE THE PLACE OF BUTTONS and quantity
//rewright this shit
            i++;
        }

        buttonHealth = new Rectangle(GamePanel.room.blocks[0][0].x - 1, buttons[0].y, iconSize, iconSize);
        buttonCoins = new Rectangle(GamePanel.room.blocks[0][0].x - 1, buttons[0].y + buttons[0].height - (iconSize + 1)*2, iconSize, iconSize);
        buttonKills = new Rectangle(GamePanel.room.blocks[0][0].x - 1, buttons[0].y + buttons[0].height - iconSize, iconSize, iconSize);
    }

    public void draw(Graphics g) {

        int i = 0;
        while (i < buttons.length) {
            if (buttons[i].contains(GamePanel.mse)) {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height);
            }

            g.drawImage(GamePanel.tilesetRes[0], buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, null);
            if (buttonId[i] != Values.air) {
                g.drawImage(GamePanel.tilesetAir[buttonId[i]], buttons[i].x + itemIn, buttons[i].y + itemIn, buttons[i].width - itemIn*2, buttons[i].height - itemIn*2, null);
            }
            if (buttonPrice[i] > 0) {
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.setColor(new Color(255, 255, 255));
                g.drawString(buttonPrice[i] + "", buttons[i].x + itemIn, buttons[i].y + iconTextY);
            }
            i++;
        }
        g.drawImage(GamePanel.tilesetRes[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(GamePanel.tilesetRes[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
        g.drawImage(GamePanel.tilesetRes[3], buttonKills.x, buttonKills.y, buttonKills.width, buttonKills.height, null);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        g.drawString("" + GamePanel.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconTextY);
        g.drawString("" + GamePanel.coins, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
        g.drawString("" + GamePanel.killed, buttonKills.x + buttonKills.width + iconSpace, buttonKills.y + iconTextY);

        if (holdsItem) {
            g.drawImage(GamePanel.tilesetAir[heldId], GamePanel.mse.x - (buttons[0].width - itemIn*2)/2 + itemIn, GamePanel.mse.y - (buttons[0].width - itemIn*2)/2 + itemIn, buttons[0].width - itemIn*2, buttons[0].height - itemIn*2, null);
        }
    }
}
