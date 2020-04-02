import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.regex.Pattern;

public class MainWindow extends JFrame {

    public static String title = "TowerDefence - Kill'Em All!";
    public static Dimension size = new Dimension(1000,800);
    private JPanel buttonPanel;
    public GamePanel gameScreen;

    public MainWindow() {
        setLayout(new BorderLayout());
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    public void initComponents() {
        //setLayout(new GridLayout(1, 1, 0, 0));
        buttonPanel = new JPanel(new GridLayout(1, 9));
        gameScreen = new GamePanel(this);
        add(buttonPanel, BorderLayout.NORTH);
        setButtons();
        add(gameScreen, BorderLayout.CENTER);
    }

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

    public ActionListener getActionListener(String action) {
        switch (action) {
            case "level":
                return actionEvent -> {
                        GamePanel.level = Integer.parseInt(setLevel());
                        gameScreen.define();
                };
            case "restart":
                return actionEvent -> {
                        gameScreen.define();

                };
            case "exit":
                return actionEvent -> {
                    System.exit(0);
                };
            default:
                return actionEvent -> System.exit(0);
        }
    }

    public String setLevel() {
        Object[] possibilities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        return (String) JOptionPane.showInputDialog(this, "Choose the level: ",
                "Level", JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("images/iconDialog.png"), possibilities, "1");
    }
}
