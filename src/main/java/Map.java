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
    private static final Random RANDOM = new Random();
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private int fieldSizeX = 3;
    private int fieldSizeY = 3;
    private char[][] field;
    private final int DOT_PADDING = 1;
    private boolean isGameOver;
    private boolean isInitialized;


    // состояния игрового поля
    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер";
    private static final String MSG_DRAW = "Ничья";
    private static int LINE_VICTORY = 3;

    Map() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
        isInitialized = false;
    }

    /**
     * Обновление по результатам хода игрока
     * @param e событие мышки
     */
    private void update(MouseEvent e){
        if(isGameOver || !isInitialized) return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if(!isValidCell(cellX,cellY) || !isEmptyCell(cellX,cellY)) return;

        field[cellY][cellX] = HUMAN_DOT;
        System.out.printf("getX=%d, getY=%d, cellWidth=%d, cellHeight=%d, x=%d, y=%d\n",
                e.getX(), e.getY(), cellWidth, cellHeight, cellX,cellY);
        repaint();

        if(checkEndGame(HUMAN_DOT,STATE_WIN_HUMAN)) return;
        repaint();

        aiTurn();
        repaint();
        if(checkEndGame(AI_DOT,STATE_WIN_AI)) return;
    }

    /**
     * старт новой игры
     * инициализирует игровое поле
     * @param mode режим игры Игра с человеком или компьютером
     * @param fSzX размер ширины поля
     * @param fSzY размер высоты поля
     * @param wLen длина строки для Победы
     */
    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin Length: %d",
                mode,fSzX,fSzY,wLen);
        initMap(fSzX,wLen);
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    /**
     * Завершение игры по итогам проверки
     * @param dot фишка
     * @param gameOverType
     * @return
     */
    private boolean checkEndGame(int dot, int gameOverType){
        if (checkVictory(dot)){
            this.gameOverType = gameOverType;
            isGameOver = true;
            repaint();
            return true;
        }
        if(isMapFull()){
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }
    /**
     * Отрисовка поля
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    /**
     * Инициализация игрового поля
     * @param size количество ячеек
     */
    private void initMap(int size, int sizeLineVictory){
        fieldSizeY = size;
        fieldSizeX = size;
        LINE_VICTORY = sizeLineVictory;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }
    private boolean isValidCell(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y <fieldSizeY;
    }
    private boolean isEmptyCell(int x, int y){
        return field[y][x] == EMPTY_DOT;
    }
    private void aiTurn(){
        int x, y;
        do{
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        }while (!isEmptyCell(x,y));
        field[y][x] = AI_DOT;
        repaint();
    }

    /**
     * Проверка
     * @param c
     * @return
     */
    private boolean checkVictory(int c){
        if(checkByHorizont(c)) return true;
        if(checkByVertical(c)) return true;
        if(checkByDiagonalRight(c)) return true;
        if(checkByDiagonalLeft(c)) return true;
        return false;
    }

    /**
     * Проверка по горизонтали, по оси X
     * @param c индекс фишки
     * @return возврат булево
     */
    private boolean checkByHorizont(int c){
        int count = 0;
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == c) count++;
            }
            if(count == LINE_VICTORY)
                return true;
            else{
                count = 0;
            }
        }
        return false;
    }

    /**
     * проверка линии по вертикали, по оси Y
     * @param c индекс фишки
     * @return возврат булево
     */
    private boolean checkByVertical(int c){
        int count = 0;
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if(field[y][x] == c) count++;
            }
            if(count == LINE_VICTORY)
                return true;
            else{
                count = 0;
            }
        }
        return false;
    }

    /**
     * Проверка по диагонали направо
     * @param c индекс фишки
     * @return возвратит булево
     */
    private boolean checkByDiagonalRight(int c){
        int count = 0;
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if(y == x){
                    if(field[y][x] == c){
                        count++;
                        if(count == LINE_VICTORY) return true;
                    }
                    else{
                        count = 0;
                    }
                } else{
                    continue;
                }
            }
        }
        return false;
    }

    /**
     * Проверка по диагонали налево
     * @param c индекс фишки
     * @return возврат булево
     */
    private boolean checkByDiagonalLeft(int c){
        int count = 0;
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if(y+x == LINE_VICTORY-1){
                    if(field[y][x] == c){
                        count++;
                        if(count == LINE_VICTORY) return true;
                    }else{
                        count = 0;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Проверка на наличие пустых полей
     * @return возврат булево
     */
    private boolean isMapFull(){
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if(field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    /**
     * Непосредственно отрисовка поля
     * @param g графический элемент
     */
    private void render(Graphics g){
        if(!isInitialized) return;
        this.panelWidth = getWidth();
        this.panelHeight = getHeight();
        cellHeight = panelHeight / fieldSizeY;
        cellWidth = panelWidth / fieldSizeX;

        g.setColor(Color.BLACK);
        // горизонтальная
        for(int h = 0; h < fieldSizeX; h++){
            int y = h * cellHeight;
            g.drawLine(0,y,panelWidth,y);
        }
        // вертикальная
        for (int w = 0; w < fieldSizeY; w++) {
            int x = w * cellHeight;
            g.drawLine(x,0,x,panelHeight);
        }
        // отрисовка
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if(field[y][x] == EMPTY_DOT) continue;
                if(field[y][x] == HUMAN_DOT){
                    g.setColor(Color.blue);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }else if(field[y][x] == AI_DOT){
                    g.setColor(Color.yellow);
                    g.fillOval(x*cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }else {
                    throw new RuntimeException("Unexpected value: " + field[y][x]+
                            " in cell: x="+x+ " y="+y);
                }
            }
        }
        if(isGameOver){
            showMessageGameOver(g);
        }
    }

    /**
     * Вывод сообщение о результате игры
     * @param g графический элемент
     */
    private void showMessageGameOver(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0, 200, getWidth(),70);
        g.setColor(Color.yellow);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch (gameOverType){
            case STATE_DRAW:
                g.drawString(MSG_DRAW,180, getHeight()/2); break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI, 20, getHeight()/2);break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN,70, getHeight()/2); break;
            default:
                throw new RuntimeException("Unexected gameOver state: " + gameOverType);
        }
    }
}
