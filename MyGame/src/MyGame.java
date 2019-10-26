import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyGame extends JPanel implements ActionListener {
    private final int SIZE = 310;
    private final int DOT_SIZE = 16;
    private int ALL_DOTS = 601;
    private Image dot;
    private Image cactus;
    private int[] cactusX = new int[801];
    private int[] cactusY = new int[801];
    private int[] x = new int[801];
    private int[] y = new int[801];
    private int dots;
    private Timer timer;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean win = false;
    private boolean replay = false;
    public static int sloo = 2;
    int delay;
    int slooz = 2;

    private int r, r1, s, s1, s2, p, p1;

    public MyGame() {
        s = 1;
        Color bg2 = new Color(0, 65, 255);
        setBackground(bg2);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void restart() {
        inGame = true;
        replay = true;
        s = 1;
        s2 = s;
        if (sloo == 1) {
            delay = 300;
            ALL_DOTS = 401;
        }
        if (sloo == 2) {
            delay = 150;
            ALL_DOTS = 601;
        }
        if (sloo == 3) {
            delay = 50;
            ALL_DOTS = 801;
        }
        slooz = sloo;
        initGame();
    }

    public void checkCactus() {
        for (int i = 0; i < 2; i++) {
            for (int j = s; j < s1; j++) {
                if (cactusX[j] < 16) {
                    s++;
                    p++;
                }
                if (x[i] == cactusX[j] && y[i] == cactusY[j]) {
                    inGame = false;
                    s = 1;
                    s1 = 1;
                    s2 = 1;
                }
                if (x[i] == cactusX[j] && y[i] == cactusY[j]) {
                    inGame = false;
                    s = 1;
                    s1 = 1;
                    s2 = 1;
                }
                if (p == ALL_DOTS - 1) {
                    inGame = false;
                    win = true;
                    s = 1;
                    s1 = 1;
                    s2 = 1;
                }
            }
            if ((int) (Math.random() * 4) == 0)
                createCactus();
        }
    }

    public void initGame() {

        dots = 2;

        for (int i = 0; i < dots; i++) {
            x[i] = 48;
            y[i] = 6 * DOT_SIZE + i * DOT_SIZE;
        }
        if (!replay) {
            timer = new Timer(150, this);
            timer.start();
        } else {
            timer.setDelay(delay);
            timer.start();
        }
        createCactus();
    }

    public void createCactus() {
        r1 = (int) ((Math.random() * 5) + 1);
        r = (int) ((Math.random() * 21 - 2));
        s1 = s2 + r1;
        if (s1 > ALL_DOTS - 1)
            s1 = ALL_DOTS;
        for (int i = s2; i < s1; i++) {
            cactusX[i] = x[1] + DOT_SIZE * 20;
            cactusY[i] = (DOT_SIZE * r) + (3 * DOT_SIZE);
        }
        s2 = s1;
    }


    public void loadImages() {
        ImageIcon iia = new ImageIcon(getClass().getResource("/res/cactus.png"));
        cactus = iia.getImage();
        ImageIcon iid = new ImageIcon(getClass().getResource("/res/dot.png"));
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String sr;
        String sl;
        if (win && !inGame) {
            String str = "Поздравляем ";
            String str1 = "Вы прошли игру!!!";
            String str2 = "Нажмите enter, если хотите повторить!!!";
            p = 0;
            g.setColor(Color.BLACK);
            g.drawString(str, 125, SIZE / 2);
            g.drawString(str1, 105, SIZE / 2 + 40);
            g.drawString(str2, 90, SIZE / 2 + 80);
        } else {
            if (inGame) {
                for (int i = s; i < s1; i++) {
                    g.drawImage(cactus, cactusX[i], cactusY[i], this);
                }
                g.drawImage(dot, 46, y[0], this);
                g.drawImage(dot, 46, y[1], this);
                sr = "Счёт = " + p;
                sl = "Ваш уровень - " + slooz + "     Цель - " + (ALL_DOTS - 1);
                g.setColor(Color.BLACK);
                g.drawString(sl, 20, 20);
                g.drawString(sr, 270, 20);
            } else {
                if (p != 0)
                    p1 = p;
                String str = "Игра окончена ";
                String str2 = "Вас счёт = " + p1;
                String str1 = "Что бы начать снова - нажмите Enter";
                g.setColor(Color.BLACK);

                g.drawString(str, 125, SIZE / 2);
                g.drawString(str2, 125, SIZE / 2 + 40);
                g.drawString(str1, 95, SIZE / 2 + 80);
                p = 0;
            }
        }
    }

    public void move() {
        if (down && ((y[0] + DOT_SIZE) < DOT_SIZE * 20)) {
            for (int i = 0; i < 2; i++) {
                y[i] += DOT_SIZE;
                down = false;
            }
        } else down = false;
        if (up && ((y[1] - DOT_SIZE) > 2 * DOT_SIZE)) {
            for (int i = 0; i < 2; i++) {
                y[i] -= DOT_SIZE;
                up = false;
            }
        } else up = false;
        for (int i = s; i < s1; i++) {
            cactusX[i] -= DOT_SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkCactus();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
            }
            if (key == KeyEvent.VK_ENTER && (!inGame || win)) {
                replay = true;
                delay = 0;
                restart();
            }
        }
    }

    public static class DifWindow extends JFrame {
        private JButton di1;
        private JButton di2;
        private JButton di3;

        public DifWindow() {
            Container con = getContentPane();
            con.setLayout(new FlowLayout());
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JPanel jp = new JPanel();
            JLabel jl = new JLabel("Выберите сложность");
            JLabel j2 = new JLabel("после проигрыша");
            di1 = new JButton("1 уровень");
            di2 = new JButton("2 уровень");
            di3 = new JButton("3 уровень");
            con.add(jl);
            con.add(j2);
            con.add(di1);
            di1.addActionListener(new ListenerAction(1));
            con.add(di2);
            di2.addActionListener(new ListenerAction(2));
            con.add(di3);
            di3.addActionListener(new ListenerAction(3));
            con.add(jp, BorderLayout.NORTH);
            setSize(150, 200);
            setLocation(10, 150);
            setVisible(true);
        }

        class ListenerAction implements ActionListener {
            private int dif;

            ListenerAction(int dif1) {
                dif = dif1;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                sloo = dif;
            }
        }
    }

    public static class MainWindow extends JFrame {

        public MainWindow() { //основные настройки
            setTitle("Прыжки");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(420, 420);
            setLocation(150, 150);
            add(new MyGame());
            setVisible(true);
        }


    }

    public static void main(String[] args) {
        DifWindow df = new DifWindow();
        MainWindow mv = new MainWindow();
    }
}
