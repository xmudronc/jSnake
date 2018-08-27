package cz.mendelu;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;


public class App 
{
    private static final Hero hero = new Hero();
    private static final Map map = new Map();
    private static final ArrayList<Enemy> enemies = new ArrayList<>();
    private static boolean thrStop = false;
    private static boolean thrRunning = false;
    private static Character prevButton = 'X';
    private static Object obj = new Object();
    private static long delay;
    private static long easy = 200;
    private static long medium = 125;
    private static long hard = 50;
    private static long score = 0;
    private static Character gameState = 'P'; //P in progress, W win, L lose

    public static long getScore()
    {
        return score;
    }

    public static void incScore(int plus)
    {
        score+=(plus + (200-delay)/10);
    }

    public static void setStop(boolean in)
    {
        thrStop = in;
    }

    public static boolean getStop()
    {
        return thrStop;
    }

    public static void fillEnemies()
    {
        Random rand = new Random();
        ArrayList<Position> positions = new ArrayList<>();
        while (positions.size() <= 100)
        {
            int  x = rand.nextInt(80) + 1;
            int  y = rand.nextInt(40) + 1;

            boolean loopOut = false;
            for (Position _position : positions)
            {
                if (_position.getX() == x && _position.getY() == y)
                {
                    loopOut = true;
                    break;
                }
            }

            if (!loopOut)
            {
                positions.add(new Position(x, y));
            }
        }

        for (Position _position : positions)
        {
            int ascii = rand.nextInt(2) + 1;
            String asciiStr = " ";
            if (ascii == 1)
            {
                asciiStr = "" + ((char) 72);
            } 
            else if (ascii == 2)
            {
                asciiStr = "" + ((char) 73);
            } 
            else
            {
                asciiStr = "" + ((char) 88);
            }
            enemies.add(new Enemy(_position, asciiStr));
        }
    }

    public static Enemy getEnemy(int x, int y)
    {
        Enemy out = new Enemy(999, 999);

        for (Enemy _enemy : enemies)
        {
            if ((_enemy.getPosition().getX() == x) && (_enemy.getPosition().getY() == y))
            {
                out = _enemy;
            }
        }

        return out;
    }

    public static Hero getHeroFromTail(int x, int y)
    {
        Hero out = new Hero(999, 999);

        Hero tmpHero = hero;
        while (tmpHero.getTail() != null)
        {
            if ((tmpHero.getTail().getPosition().getX() == x) && (tmpHero.getTail().getPosition().getY() == y))
            {
                out = tmpHero.getTail();
                break;
            }
            else
            {
                tmpHero = tmpHero.getTail();
            }
        }

        return out;
    }

    public static String draw()
    {
        String out = "";

        for (int y = 0; y < map.getMap().size(); y++)
        {
            for (int x = 0; x < map.getMap().get(0).size(); x++)
            {
                if ((hero.getPosition().getX() == x) && (hero.getPosition().getY() == y))
                {
                    out+=hero.getSprite();
                }
                else if ((getHeroFromTail(x, y).getPosition().getX() == x) && (getHeroFromTail(x, y).getPosition().getY() == y))
                { 
                    out+=getHeroFromTail(x, y).getSprite();
                }
                else if ((getEnemy(x, y).getPosition().getX() == x) && (getEnemy(x, y).getPosition().getY() == y))
                {
                    out+=getEnemy(x, y).getSprite();
                }
                else
                {
                    if (x == map.getMap().get(0).size() - 1)
                    {
                        out+=map.getMap().get(y).get(x);
                        out+="\n";
                    }
                    else
                    {
                        out+=map.getMap().get(y).get(x);
                    }
                }                
            }
        }

        return out;
    }

    public static boolean checkWall(char LRUD)
    {
        boolean out = false;

        if (LRUD == 'L')
        {
            if (hero.getPosition().getX() != 1)
            {
                out = true;
            }
        }
        else if (LRUD == 'R')
        {
            
            if (hero.getPosition().getX() != 80)
            {
                out = true;
            }
        }
        else if (LRUD == 'U')
        {
            
            if (hero.getPosition().getY() != 1)
            {
                out = true;
            }
        }
        else if (LRUD == 'D')
        {
            
            if (hero.getPosition().getY() != 40)
            {
                out = true;
            }
        }

        return out;
    }

    public static void gameOver(JFrame frame)
    {
        if (gameState == 'W')
                {
                    frame.setVisible(false);

                    JFrame winFrame = new JFrame();
                    winFrame.setTitle("win");
                    winFrame.setSize(300, 100);
                    winFrame.setLayout(new FlowLayout());
                    TextArea text = new TextArea(2, 20);
                    text.setText("YOU WIN \nscore: " + App.score);
                    text.setBackground(Color.white);
                    text.setEditable(false);
                    text.setEnabled(false);
                    Button button = new Button("OK");
                    button.addActionListener(new ActionListener(){
                    
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    button.setLabel("OK");
                    winFrame.add(text);
                    winFrame.add(button);
                    winFrame.setVisible(true);
                }
                else if (gameState == 'L')
                {
                    frame.setVisible(false);

                    JFrame loseFrame = new JFrame();
                    loseFrame.setTitle("lose");
                    loseFrame.setSize(300, 100);
                    loseFrame.setLayout(new FlowLayout());
                    TextArea text = new TextArea(2, 20);
                    text.setText("YOU LOSE \nscore: " + App.score);
                    text.setBackground(Color.white);
                    text.setEditable(false);                    
                    text.setEnabled(false);
                    Button button = new Button("OK");
                    button.addActionListener(new ActionListener(){
                    
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    button.setLabel("OK");
                    loseFrame.add(text);
                    loseFrame.add(button);
                    loseFrame.setVisible(true);
                }
    }

    public static void difficulty()
    {
        final JFrame dFrame = new JFrame();
        dFrame.setTitle("difficulty");
        dFrame.setSize(300, 100);

        dFrame.setLayout(new FlowLayout());
        Button buttonE = new Button("EASY");
        buttonE.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                delay = easy;
                dFrame.setVisible(false);
                play();
            }
        });
        Button buttonM = new Button("MEDIUM");
        buttonM.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                delay = medium;
                dFrame.setVisible(false);
                play();
            }
        });
        Button buttonH = new Button("HARD");
        buttonH.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                delay = hard;
                dFrame.setVisible(false);
                play();
            }
        });
        Button buttonX = new Button("EXIT");
        buttonX.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                dFrame.setVisible(false);
                System.exit(0);
            }
        });
        buttonE.setLabel("EASY");
        buttonM.setLabel("MEDIUM");
        buttonH.setLabel("HARD");
        buttonX.setLabel("EXIT");
        dFrame.add(buttonE);
        dFrame.add(buttonM);
        dFrame.add(buttonH);
        dFrame.add(buttonX);
        dFrame.setVisible(true);
    }

    public static void play()
    {
        fillEnemies();

        final JFrame frame = new JFrame();
        frame.setTitle("move");
        frame.setSize(640, 720);

        final FlowLayout layout = new FlowLayout();
        frame.setLayout(layout);

        final TextArea console = new TextArea(42, 84);
        console.setEditable(false);
        console.setBackground(Color.white);
        console.setFont(new Font("monospaced", Font.PLAIN, 12));
        console.setText(draw());
        console.setEnabled(false);
        frame.add(console);

        final TextArea score = new TextArea(1, 84);
        score.setEditable(false);
        score.setBackground(Color.white);
        score.setFont(new Font("monospaced", Font.PLAIN, 12));
        score.setText("score: " + App.score);
        score.setEnabled(false);
        frame.add(score);

        frame.addKeyListener(new KeyListener(){
        
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                

                Thread thrR = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (obj)
                        {
                            setStop(false);
                            while (!getStop())
                            {
                                if (checkWall('R'))
                                {
                                    hero.moveR();
                                    if (getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getPosition().getX() != 999)
                                    {
                                        hero.incTail();
                                        Character tmp = getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getSprite().charAt(0);
                                        incScore((int) tmp);
                                        enemies.remove(getEnemy(hero.getPosition().getX(), hero.getPosition().getY()));
                                        if (enemies.size() <= 0)
                                        {
                                            setStop(true);
                                            gameState = 'W';
                                            gameOver(frame);
                                        }
                                    } 
                                    else if (hero.getTail() != null)
                                    { 
                                        if (hero.biteTail(hero.getPosition().getX(), hero.getPosition().getY()))
                                        {
                                            setStop(true);
                                            gameState = 'L';
                                            gameOver(frame);
                                        }
                                    }
                                    console.setText(draw());
                                    score.setText("score: " + App.score);
                                }
                                else
                                {
                                    setStop(true);
                                    gameState = 'L';
                                    gameOver(frame);
                                }  
                                try {
									TimeUnit.MILLISECONDS.sleep(delay);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                               
                            }
                            //System.out.println("thrR out of loop");
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                });
                Thread thrL = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (obj)
                        {
                            setStop(false);
                            while (!getStop())
                            {
                                if (checkWall('L'))
                                {
                                    hero.moveL();
                                    if (getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getPosition().getX() != 999)
                                    {
                                        hero.incTail();
                                        Character tmp = getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getSprite().charAt(0);
                                        incScore((int) tmp);
                                        enemies.remove(getEnemy(hero.getPosition().getX(), hero.getPosition().getY()));
                                        if (enemies.size() <= 0)
                                        {
                                            setStop(true);
                                            gameState = 'W';
                                            gameOver(frame);
                                        }
                                    } 
                                    else if (hero.getTail() != null)
                                    { 
                                        if (hero.biteTail(hero.getPosition().getX(), hero.getPosition().getY()))
                                        {
                                            setStop(true);
                                            gameState = 'L';
                                            gameOver(frame);
                                        }
                                    } 
                                    console.setText(draw());
                                    score.setText("score: " + App.score);
                                }
                                else
                                {
                                    setStop(true);
                                    gameState = 'L';
                                    gameOver(frame);
                                }  
                                try {
									TimeUnit.MILLISECONDS.sleep(delay);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                               
                            }
                            //System.out.println("thrL out of loop");
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                });
                Thread thrU = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (obj)
                        {
                            setStop(false);
                            while (!getStop())
                            {
                                if (checkWall('U'))
                                {
                                    hero.moveU();
                                    if (getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getPosition().getX() != 999)
                                    {
                                        hero.incTail();
                                        Character tmp = getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getSprite().charAt(0);
                                        incScore((int) tmp);
                                        enemies.remove(getEnemy(hero.getPosition().getX(), hero.getPosition().getY()));
                                        if (enemies.size() <= 0)
                                        {
                                            setStop(true);
                                            gameState = 'W';
                                            gameOver(frame);
                                        }
                                    } 
                                    else if (hero.getTail() != null)
                                    { 
                                        if (hero.biteTail(hero.getPosition().getX(), hero.getPosition().getY()))
                                        {
                                            setStop(true);
                                            gameState = 'L';
                                            gameOver(frame);
                                        }
                                    }
                                    console.setText(draw());
                                    score.setText("score: " + App.score);
                                }
                                else
                                {
                                    setStop(true);
                                    gameState = 'L';
                                    gameOver(frame);
                                }  
                                try {
									TimeUnit.MILLISECONDS.sleep(delay);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                              
                            }
                            //System.out.println("thrU out of loop");
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                });
                Thread thrD = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (obj)
                        {
                            setStop(false);
                            while (!getStop())
                            {
                                if (checkWall('D'))
                                {
                                    hero.moveD();
                                    if (getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getPosition().getX() != 999)
                                    {
                                        hero.incTail();
                                        Character tmp = getEnemy(hero.getPosition().getX(), hero.getPosition().getY()).getSprite().charAt(0);
                                        incScore((int) tmp);
                                        enemies.remove(getEnemy(hero.getPosition().getX(), hero.getPosition().getY()));
                                        if (enemies.size() <= 0)
                                        {
                                            setStop(true);
                                            gameState = 'W';
                                            gameOver(frame);
                                        }
                                    } 
                                    else if (hero.getTail() != null)
                                    { 
                                        if (hero.biteTail(hero.getPosition().getX(), hero.getPosition().getY()))
                                        {
                                            setStop(true);
                                            gameState = 'L';
                                            gameOver(frame);
                                        }
                                    }
                                    console.setText(draw());
                                    score.setText("score: " + App.score);
                                }
                                else
                                {
                                    setStop(true);
                                    gameState = 'L';
                                    gameOver(frame);
                                }  
                                try {
									TimeUnit.MILLISECONDS.sleep(delay);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                           
                            }
                            //System.out.println("thrD out of loop");
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                });

                if ((e.getKeyChar() == 'q') || (e.getKeyCode() == 27))
                {
                    frame.setVisible(false);
                    System.exit(0);
                }
                else if ((e.getKeyChar() == 'd') || (e.getKeyChar() == '6') || (e.getKeyCode() == 39))
                {
                    if (!thrRunning)
                    {
                        prevButton = 'd';
                        setStop(false);
                        thrR.start();
                        thrRunning = true;
                    }
                    else if (thrRunning && prevButton != 'd' && prevButton != 'a')
                    {
                        setStop(true);
                        thrRunning = false;

                        prevButton = 'd';
                        thrR.start();
                        thrRunning = true;
                    }
                }
                else if ((e.getKeyChar() == 'a') || (e.getKeyChar() == '4') || (e.getKeyCode() == 37))
                {
                    if (!thrRunning)
                    {
                        prevButton = 'a';
                        setStop(false);
                        thrL.start();
                        thrRunning = true;
                    }
                    else if (prevButton != 'a' && prevButton != 'd')
                    {
                        setStop(true);
                        thrRunning = false;

                        prevButton = 'a';
                        thrL.start();
                        thrRunning = true;
                    }
                }
                else if ((e.getKeyChar() == 'w') || (e.getKeyChar() == '8') || (e.getKeyCode() == 38))
                {
                    if (!thrRunning)
                    {
                        prevButton = 'w';
                        setStop(false);
                        thrU.start();
                        thrRunning = true;
                    }
                    else if (prevButton != 'w' && prevButton != 's')
                    {
                        setStop(true);
                        thrRunning = false;
                        
                        prevButton = 'w';
                        thrU.start();
                        thrRunning = true;
                    }
                }
                else if ((e.getKeyChar() == 's') || (e.getKeyChar() == '2') || (e.getKeyCode() == 40))
                {
                    if (!thrRunning)
                    {
                        prevButton = 's';
                        setStop(false);
                        thrD.start();
                        thrRunning = true;
                    }
                    else if (prevButton != 's' && prevButton != 'w')
                    { 
                        setStop(true);
                        thrRunning = false;
                        
                        prevButton = 's'; 
                        thrD.start();
                        thrRunning = true;
                    }
                }
                //System.out.println(gameState);
            }
        });

        frame.setVisible(true);
    }

    public static void main( String[] args )
    {
        difficulty();
    }
}
