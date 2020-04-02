import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class KeyHandle implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        GamePanel.store.click(mouseEvent.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        GamePanel.mse = new Point(mouseEvent.getX() - (MainWindow.size.width - GamePanel.myWidth)/2,
                mouseEvent.getY() - (MainWindow.size.height - GamePanel.myHeight + Store.awayFromRoom)/2);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        GamePanel.mse = new Point(mouseEvent.getX() - (MainWindow.size.width - GamePanel.myWidth)/2,
                                  mouseEvent.getY() - (MainWindow.size.height - GamePanel.myHeight + Store.awayFromRoom)/2);
    }
}
