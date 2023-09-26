import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Map extends JPanel {
    private int panelWidth;
    private int panelHeight;
    private int cellHeight;
    private int cellWidth;
    private static final Random RANDOM = null;
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private int fieldSizeX = 3;
    private int fieldSizeY = 3;
    private char[] field;
    Map() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }
    private void update(MouseEvent e){
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        System.out.printf("getX=%d, getY=%d, cellWidth=%d, cellHeight=%d, x=%d, y=%d\n",
                e.getX(), e.getY(), cellWidth, cellHeight, cellX,cellY);
        repaint();
    }
    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin Length: %d",
                mode,fSzX,fSzY,wLen);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }


    private void render(Graphics g){
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellHeight = panelHeight / 3;
        cellWidth = panelWidth / 3;

        g.setColor(Color.BLACK);
        // горизонтальная
        for(int h = 0; h < 3; h++){
            int y = h * cellHeight;
            g.drawLine(0,y,panelWidth,y);
        }
        // вертикальная
        for (int w = 0; w < 3; w++) {
            int x = w * cellHeight;
            g.drawLine(x,0,x,panelHeight);
        }

    }
}
