import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    private static final String CHOICE_MODE_GAME = "Выберите режим игры";
    private static final String LABEL_HUMAN_HUMAN = "Человек и Человек";
    private static final String LABEL_HUMAN_AI = "Человек и компьютер";
    private static final String STRING_NEW_GAME = "Новая игра";
    private static final String CHOICE_SIZE_SPACE = "Выберите размеры поля";
    private static final String CHOICE_WIDTH_VICTORY = "Выберите длину для победы";
    private static final String SELECTED_SIZE_SPACE = "Установленная длина:";
    JPanel mainPanel;
    JLabel labelMode;
    JLabel labelHead;
    JRadioButton btnHuman;
    JRadioButton btnAI;
    ButtonGroup btnGroup;
    JButton btnStart = new JButton(STRING_NEW_GAME);
    SettingWindow(GameWindow gameWindow){

        mainPanel = new JPanel(new GridLayout(1,2));
        labelMode = new JLabel(CHOICE_MODE_GAME);
        btnHuman = new JRadioButton("Человек VS Человек");
        btnAI = new JRadioButton("Человек VS Компьютер");
        btnGroup = new ButtonGroup();

        btnGroup.add(btnHuman);
        btnGroup.add(btnAI);
        mainPanel.add(btnHuman);
        mainPanel.add(btnAI);


        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(0,3,3,3);
                setVisible(false);
            }
        });
        add(mainPanel,BorderLayout.NORTH);
        add(btnStart, BorderLayout.SOUTH);
    }
}
