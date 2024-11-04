import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Runner extends JPanel implements ActionListener {
    private Timer timer;
    private int score;
    private boolean jumping;
    private int yPos;
    private int ySpeed;
    private int groundLevel = 200;
    private int obstacleX = 400;
    private boolean gameOver;

    public TRexRunner() {
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.WHITE);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !jumping) {
                    jumping = true;
                    ySpeed = -10;
                }
                if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
                    resetGame();
                }
            }
        });
        setFocusable(true);
        timer = new Timer(20, this);
        timer.start();
        resetGame();
    }

    private void resetGame() {
        score = 0;
        jumping = false;
        yPos = groundLevel;
        ySpeed = 0;
        obstacleX = 400;
        gameOver = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            if (jumping) {
                yPos += ySpeed;
                ySpeed += 1;
                if (yPos >= groundLevel) {
                    yPos = groundLevel;
                    jumping = false;
                }
            }

            obstacleX -= 5;
            if (obstacleX < -20) {
                obstacleX = 400;
                score++;
            }

            if (obstacleX < 50 && obstacleX > 30 && yPos >= groundLevel - 20) {
                gameOver = true;
            }

            repaint();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(50, yPos, 20, 20);
        g.fillRect(obstacleX, groundLevel - 20, 20, 20);
        g.drawString("Score: " + score, 10, 10);

        if (gameOver) {
            g.drawString("Game Over! Press R to restart.", 100, 150);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("T-Rex Runner");
        Runner game = new TRexRunner();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}