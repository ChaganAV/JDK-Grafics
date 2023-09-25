import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 555;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 200;

    JButton btnStart = new JButton("Новая игра");
    JButton btnExit = new JButton("Выход");
    Map map;
    SettingWindow setting;
    GameWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLocation(WINDOW_POSX,WINDOW_POSY);
        setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Крестики-нолики");
        setResizable(false);

        map = new Map();
        setting = new SettingWindow(this);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setting.setVisible(true);
            }
        });

        JPanel panBottom = new JPanel(new GridLayout(1,2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);
        add(panBottom, BorderLayout.SOUTH);
        add(map);

        setVisible(true);
    }
    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        map.startNewGame(mode,fSzX,fSzY,wLen);
    }

}
