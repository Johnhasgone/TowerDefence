import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public static String title = "TowerDefence - Kill'Em All!";
    public static Dimension size = new Dimension(700,600);

    public MainWindow() {

        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    public void initComponents() {
        setLayout(new GridLayout(1, 1, 0, 0));

        GamePanel panel = new GamePanel(this);
        add(panel);
    }
}
