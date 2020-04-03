import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/** Drawing the main window of the game. Contains: button panel to operate the game (levels, restart, exit),
 * JPanel with all the game images and physics, and option pane to chose the level */

public class MainWindow extends JFrame {

    public static String title = "TowerDefence - Kill'Em All!"; //заголовок окна, используется в конструкторе класса Frame
    public static Dimension size = new Dimension(1200,730);
    private JPanel buttonPanel; // создание верхней панели с кнопками
    public GamePanel gameScreen;

    public MainWindow() { //конструктор класса Frame
        setLayout(new BorderLayout());
        setTitle(title); // определить заголовок
        setSize(size);  // определить размер окна
        setResizable(false);  // можно ли изменять размер
        setLocationRelativeTo(null); // относительное ли положение
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // как выйти из программы

        initComponents();

        setVisible(true);
    }

    public void initComponents() {
        buttonPanel = new JPanel(new GridLayout(1, 3)); // для заполнения ВСЕЙ зоны Frame объектами
        gameScreen = new GamePanel(this);
        add(buttonPanel, BorderLayout.NORTH);
        setButtons();
        add(gameScreen, BorderLayout.CENTER);
    }
    // настройка кнопок управления в верхней части окна
    public void setButtons() {
        JButton level;
        JButton restart;
        JButton exit;


        level = new JButton("Choose level");
        level.addActionListener(getActionListener("level"));

        restart = new JButton("Restart");
        restart.addActionListener(getActionListener("restart"));

        exit = new JButton("Exit");
        exit.addActionListener(getActionListener("exit"));

        buttonPanel.add(level, 0, 0);
        buttonPanel.add(restart, 0, 1);
        buttonPanel.add(exit, 0, 2);

    }
    // обработка событий для кнопок
    public ActionListener getActionListener(String action) {
        switch (action) {
            case "level":
                return actionEvent -> {
                        GamePanel.level = Integer.parseInt(setLevel());
                        gameScreen.define();
                };
            case "restart":
                return actionEvent -> gameScreen.define();
            case "exit":
                return actionEvent -> System.exit(0);
            default:
                return actionEvent -> System.exit(0);
        }
    }
    // выбор уровня во всплывающем окне (choose level)
    public String setLevel() {
        Object[] possibilities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String ret = (String) JOptionPane.showInputDialog(this, "Choose the level: ",
                "Level", JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("images/iconDialog.png"), possibilities, "1");
        return ret == null ? "1" : ret;
    }
}
