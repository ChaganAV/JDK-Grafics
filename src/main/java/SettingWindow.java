import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    private static final String CHOICE_MODE_GAME = "Выберите режим игры";
    private static final String LABEL_HUMAN_HUMAN = "Человек и Человек";
    private final int MODE_HH = 1;
    private static final String LABEL_HUMAN_AI = "Человек и компьютер";
    private final int MODE_HI = 0;
    private static final String STRING_NEW_GAME = "Новая игра";
    private static final String SELECT_SIZE_SPACE = "Установленный размер поля:";
    private static final String CHOICE_SIZE_SPACE = "Выберите размеры поля";
    private static final String CHOICE_WIDTH_VICTORY = "Выберите длину для победы";
    private static final String SELECTED_SIZE_SPACE = "Установленная длина:";
    private static final int SPACE_MIN_SIZE = 3;
    private static final int SPACE_MAX_SIZE = 10;
    JRadioButton btnHumanHuman;
    JRadioButton btnHumanAI;
    ButtonGroup btnGroup;
    JButton btnStart = new JButton(STRING_NEW_GAME);
    SettingWindow(GameWindow gameWindow){
        int centerWindowX = gameWindow.getX() + gameWindow.getWidth()/2;
        int centerWindowY = gameWindow.getY() + gameWindow.getHeight()/2;
        int modeGame = 0;

        setLocation(centerWindowX - WINDOW_WIDTH/2,centerWindowY - WINDOW_HEIGHT/2);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        JPanel panelChoiceMode = (JPanel) createPanelChoiceMode();
        JPanel panelSize = (JPanel) createPanelSizeSpace();
        if (btnHumanAI.isSelected())
            modeGame = MODE_HI;
        else{
            modeGame = MODE_HH;
        }
        int finalModeGame = modeGame;
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameWindow.startNewGame(finalModeGame,3,3,3);
                setVisible(false);
            }
        });
        add(panelChoiceMode,BorderLayout.NORTH);
        add(panelSize);
        add(btnStart, BorderLayout.SOUTH);
    }
    private Component createPanelChoiceMode(){
        JPanel panel = new JPanel(new GridLayout(3,1));
        JLabel label = new JLabel(CHOICE_MODE_GAME);
        btnHumanHuman = new JRadioButton("Человек и Человек");
        btnHumanAI = new JRadioButton("Человек и Компьютер");
        btnHumanAI.setSelected(true);
        btnGroup = new ButtonGroup();

        btnGroup.add(btnHumanAI);
        btnGroup.add(btnHumanHuman);
        panel.add(label);
        panel.add(btnHumanHuman);
        panel.add(btnHumanAI);
        return panel;
    }
    private Component createPanelSizeSpace(){
        JPanel panel = new JPanel(new GridLayout(3,1));
        JLabel labelChoice = new JLabel(CHOICE_SIZE_SPACE);
        JLabel labelSize = new JLabel(SELECT_SIZE_SPACE + " " + SPACE_MIN_SIZE);
        JSlider slider = new JSlider(SPACE_MIN_SIZE,SPACE_MAX_SIZE,SPACE_MIN_SIZE);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int sliderValue = slider.getValue();
                labelSize.setText(SELECT_SIZE_SPACE + " " + sliderValue);
            }
        });
        panel.add(labelChoice);
        panel.add(labelSize);
        panel.add(slider);

        return panel;
    }
}
