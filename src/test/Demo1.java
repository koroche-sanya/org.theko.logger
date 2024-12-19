package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.theko.logger.GlobalLogger;
import org.theko.logger.LogLevel;

public class Demo1 extends JFrame {

    public class GameLogger {
        static {
            // Set the output stream for logs
            GlobalLogger.getLoggerOutput().setOutputStream(System.out);
            GlobalLogger.getLoggerOutput().setPattern("[HH:mm:ss] [Thread: -thread] [-class > -method] > -message");
        }

        // Method to log general game info
        public static void log(String message) {
            GlobalLogger.log(LogLevel.DEBUG, message);
        }

        // Method to log errors in the game
        public static void logError(String message, Throwable t) {
            GlobalLogger.log(LogLevel.ERROR, message + ", caused by: " + t.getClass());
        }
    }

    public class Starship {
        private int x, y, width, height;
        private Color color;
        private int speed;

        public Starship(int x, int y, int width, int height, int speed, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.speed = speed;
            this.color = color;
        }

        // Method to move the starship based on key inputs
        public void moveLeft() {
            x -= speed;
        }

        public void moveRight() {
            x += speed;
        }

        public void moveUp() {
            y -= speed;
        }

        public void moveDown() {
            y += speed;
        }

        // Method to render the starship on screen
        public void render(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }

        // Getters
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public class Bullet {
        private int x, y, width, height;
        private Color color;
        private int speed;

        public Bullet(int x, int y, int width, int height, int speed, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.speed = speed;
            this.color = color;
        }

        // Method to move the bullet (downwards)
        public void move() {
            y += speed;
        }

        // Method to render the bullet
        public void render(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }

        // Collision detection with the starship
        public boolean collidesWith(Starship starship) {
            Rectangle bulletRect = new Rectangle(x, y, width, height);
            Rectangle starshipRect = new Rectangle(starship.getX(), starship.getY(), starship.width, starship.height);
            return bulletRect.intersects(starshipRect);
        }

        // Getters
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    private Starship starship;
    private ArrayList<Bullet> bullets;
    private Timer gameTimer;
    private long startTime;

    public Demo1() {
        setTitle("Starship Survival Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize starship and bullets list
        starship = new Starship(375, 500, 50, 50, 10, Color.BLUE);
        bullets = new ArrayList<>();

        // Key listener for starship movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    starship.moveLeft();
                    GameLogger.log("Player moved left");
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    starship.moveRight();
                    GameLogger.log("Player moved right");
                } else if (keyCode == KeyEvent.VK_UP) {
                    starship.moveUp();
                    GameLogger.log("Player moved up");
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    starship.moveDown();
                    GameLogger.log("Player moved down");
                }
            }
        });

        // Set up the game timer to update the game state every 20 milliseconds
        gameTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                repaint();
            }
        });
        gameTimer.start();

        // Start the timer for the game to track survival time
        startTime = System.currentTimeMillis();

        // Log the start of the game
        GameLogger.log("Game Started");
    }

    // Method to update the game state
    private void updateGame() {
        // Spawn a new bullet randomly at the top of the screen
        if (Math.random() < 0.02) {
            int randomX = new Random().nextInt(getWidth());
            bullets.add(new Bullet(randomX, 0, 10, 30, 5, Color.RED));
        }

        // Move all bullets down
        for (Bullet bullet : bullets) {
            bullet.move();
        }

        // Check for collisions between bullets and the starship
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet.collidesWith(starship)) {
                // Game Over when collision occurs
                GameLogger.logError("Game Over! Player hit by a bullet.", new RuntimeException());
                gameTimer.stop();
                break;
            }
        }

        // Remove bullets that go off screen
        bullets.removeIf(bullet -> bullet.getY() > getHeight());
    }

    // Method to render the game objects (Starship and Bullets)
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Clear the screen (black background)
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render the starship and bullets
        starship.render(g);
        for (Bullet bullet : bullets) {
            bullet.render(g);
        }

        // Display the score (survival time in seconds)
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        g.setColor(Color.WHITE);
        g.drawString("Survival Time: " + elapsedTime + " seconds", 10, 30);
    }

    // Main method to launch the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Demo1 game = new Demo1();
                game.setVisible(true);
            }
        });
    }
}
