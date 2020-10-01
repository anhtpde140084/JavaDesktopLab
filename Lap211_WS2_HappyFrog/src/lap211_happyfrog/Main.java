/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lap211_happyfrog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author tranp
 */
public class Main implements ActionListener, MouseListener, KeyListener {

    public static Main frogFly;

    public final int WIDTH = 600, HEIGHT = 800;

    public Display renderer;

    public Rectangle frog;

    public int ticks, yMotion, score;

    public boolean gameOVer, started;

    public Random rand;

    public ArrayList<Rectangle> columns;
    ImageIcon icon = new ImageIcon("/Images/frog.png");

    public Main() {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);

        renderer = new Display();
        rand = new Random();

        jframe.add(renderer);
        jframe.setTitle("Tran_Phi_Anh_Nguyen_Phuong_Nam");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);
        frog = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        //setImageForLabel(frog, icon);
        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();
    }

    public void addColumn(boolean start) {

        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);

        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));

            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));

        }
    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.GREEN);
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void jump() {
        if (gameOVer) {
            frog = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOVer = false;
        }
        if (!started) {
            started = true;
        } else if (!gameOVer) {
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 9;
        }
    }

    private void setImageForLabel(JLabel jl, ImageIcon ic) {
        Image im = ic.getImage().getScaledInstance(jl.getWidth(), jl.getHeight(), java.awt.Image.SCALE_SMOOTH);
        jl.setIcon(new ImageIcon(im));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 5;

        ticks++;

        if (started) {
            for (int i = 0; i < columns.size(); i++) {
                Rectangle col = columns.get(i);
                col.x -= speed;
            }
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                if (column.x + column.width < 0) {
                    columns.remove(i);
                    if (column.y == 0) {
                        addColumn(false);
                    }
                }

            }
            frog.y += yMotion;

            for (Rectangle column : columns) {
                if (column.y == 0 && frog.x + frog.width / 2 > column.x + column.width / 2 - 10 && frog.x + frog.width / 2 < column.x + column.width / 2 + 10) {
                    score++;
                }
                if (column.intersects(frog)) {
                    gameOVer = true;
                    if (frog.x <= column.x) {
                        frog.x = column.x - frog.width;

                    } else {
                        if (column.y != 0) {
                            frog.y = column.y - frog.height;
                        } else if (frog.y < column.height) {
                            frog.y = column.height;
                        }
                    }
                }
                if (frog.y > HEIGHT - 120 || frog.y < 0) {
                    gameOVer = true;
                }
                if (frog.y + yMotion >= HEIGHT - 120) {
                    frog.y = HEIGHT - 120 - frog.height;
                    gameOVer = true;
                }
            }
        }
        renderer.repaint();
    }

    public void repaint(Graphics g) throws IOException {

        
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);
        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 130, WIDTH, 130);

        g.setColor(Color.red);

        g.drawImage(loadImage.image,50,50,null);
        g.fillRect(frog.x, frog.y, frog.width, frog.height);
        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 1, 100));

        if (!started) {
            g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
        }
        if (gameOVer) {
            g.drawString("Game over!", 100, HEIGHT / 2 - 50);
        }

        if (!gameOVer && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
            System.out.println("Nhay");
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        frogFly = new Main();
        System.out.println("Bắt Đầu");
    }
}
