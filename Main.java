package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        image = imageIcon.getImage();
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }
}

class Pug extends Sprite {
    private int dx;
    private int dy;
    private boolean isColliding = false;
    private int direction;

    //DUR: Down - Up - Right
    //REPRESENTS ANGLED MOVEMENT EITHER UP OR DOWN IN THE RIGHT DIRECTION
    private boolean DUR = false;
    //DUL: Down - Up - Left
    //REPRESENTS ANGLED MOVEMENT EITHER UP OR DOWN IN THE LEFT DIRECTION
    private boolean DUL = false;

    public Pug(int x, int y) {
        super(x, y);
        initPug();
    }

    private void initPug() {
        loadImage("C:\\Users\\awosh\\Pictures\\pug.png");
        getImageDimensions();
    }

    public int getDirection() {
        return direction;
    }

    public void move() {

        //DIRECTION CHECKER
        if (dx == 0 && dy == 0) {
            DUR = false;
            DUL = false;
        }
        //Right
        if (!DUR && x + dx > x) {
            direction = 0;
//            System.out.println("right");
        //Left
        } else if (!DUL && x + dx < x) {
            direction = 1;
//            System.out.println("left");
        }
        //Down
        if ((!DUR && !DUL) && y + dy > y) {
            direction = 2;
//            System.out.println("down");
        //Up
        } else if ((!DUR && !DUL) && y + dy < y) {
            direction = 3;
//            System.out.println("up");
        }
        //Down & right
        if (x + dx > x && y + dy > y) {
            DUR = true;
            direction = 4;
//            System.out.println("down & right");
        }
        //Up & right
        if (x + dx > x && y + dy < y) {
            DUR = true;
            direction = 5;
//            System.out.println("up & right");
        }
        //Down & left
        if (x + dx < x && y + dy > y) {
            DUL = true;
            direction = 6;
//            System.out.println("down & left");
        }
        //Up & left
        if (x + dx < x && y + dy < y) {
            DUL = true;
            direction = 7;
//            System.out.println("up & left");
        }

        //BOARD BOUNDARIES
        int pugStopX = 799 - getBounds().width;
        int pugStopY = 599 - getBounds().height;

        if (x < 1) {
            x = 1;
        }
        if (x > pugStopX) {
            x = pugStopX;
        }
        if (y < 1) {
            y = 1;
        }
        if (y > pugStopY) {
            y = pugStopY;
        }

        //PLAYER MOVEMENT
        x += dx;
        y += dy;
    }

    public void stop() {
        if (direction == 0) {
            if (dx > 0) {
                dx = 0;
            }
        }
        if (direction == 1) {
            if (dx < 0) {
                dx = 0;
            }
        }
        if (direction == 2) {
            if (dy > 0) {
                dy = 0;
            }
        }
        if (direction == 3) {
            if (dy < 0) {
                dy = 0;
            }
        }
    }

    public void collide(Rectangle2D rectangle) {
        //left edge
        if (direction == 0) {
            collide(rectangle, "left");
        }
        //right edge
        if (direction == 1) {
            collide(rectangle,"right");
        }
        //top edge
        if (direction == 2) {
            collide(rectangle, "top");
        }
        //bottom edge
        if (direction == 3) {
            collide(rectangle,"bottom");
        }
    }

    public void collide(Rectangle2D rectangle, String side) {
        isColliding = true;
        if (side.equals("top")) {
            y = ((int) rectangle.getY() - height) - 3;
            isColliding = false;
        }
        if (side.equals("bottom")) {
            y = ((int) rectangle.getY() + (int) rectangle.getHeight()) + 3;
            isColliding = false;
        }
        if (side.equals("left")) {
            x = ((int) rectangle.getX() - width) - 3;
            isColliding = false;
        }
        if (side.equals("right")) {
            x = ((int) rectangle.getX() + (int) rectangle.getWidth()) + 3;
            isColliding = false;
        }
    }





    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!isColliding) {
            if (key == KeyEvent.VK_SPACE) {
//                System.out.println("Space bar pressed");
            }
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                //System.out.println("left");
                dx = -3;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
               // System.out.println("right");
                dx = 3;
            }
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                //System.out.println("up");
                dy = -3;
            }
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
               // System.out.println("down");
                dy = 3;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            //System.out.println("left rele");
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            //System.out.println("right rele");
            dx = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            //System.out.println("up rele");
            dy = 0;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            //System.out.println("down rele");
            dy = 0;
        }
    }
}

class Food extends Sprite {

    private int position;
    private boolean isEaten;
    private String foodType;
    private String foodPath;
    private int foodHP;

    private ArrayList<Integer> foodProb;

    public Food(Food food) {
        super(food.getX(), food.getY());
    }
    public Food(int x, int y) {
        super(x, y);
        initFood();
    }

    private void initFood() {
        setFoodType();
        loadImage(foodPath);
        getImageDimensions();

    }

    private void setFoodType() {
        foodProb = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            foodProb.add(0);
        }
        for (int i = 0; i < 8; i++) {
            foodProb.add(1);
        }
        for (int i = 0; i < 10; i++) {
            foodProb.add(2);
        }
        for (int i = 0; i < 4; i++) {
            foodProb.add(3);
        }
        Random random = new Random();
        int randomInt = random.nextInt(foodProb.size());
        switch (foodProb.get(randomInt)) {
            case 0:
                foodType = "pizza";
                foodPath = "C:\\Users\\awosh\\Pictures\\pizza.png";
                foodHP = 10;
                System.out.println("pizza");
                break;
            case 1:
                foodType = "taco";
                foodPath = "C:\\Users\\awosh\\Pictures\\taco.png";
                foodHP = 5;
                System.out.println("taco");
                break;
            case 2:
                foodType = "burger";
                foodPath = "C:\\Users\\awosh\\Pictures\\burger.png";
                foodHP = 3;
                System.out.println("burger");
                break;
            case 3:
                foodType = "pepper";
                foodPath = "C:\\Users\\awosh\\Pictures\\pepper.png";
                foodHP = -8;
                System.out.println("pepperd");
                break;
        }
    }

    public String getFoodType() {
        return foodType;
    }
    public int getFoodHP() {
        return foodHP;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position > 0 && position <= 6) {
            this.position = position;
        }
        else {
            System.out.println("Invalid Position");
        }
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }
}

class Boundary extends Sprite {
    public Boundary(int x, int y) {
        super(x,y);
        initBoundary();
    }
    private void initBoundary() {
        loadImage("C:\\Users\\awosh\\Pictures\\tableP.png");
        getImageDimensions();
    }
}

class Board extends JPanel implements ActionListener {
    private final int PUG_X = 40;
    private final int PUG_Y = 60;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 10;

    private Timer timer;
    private Pug pug;
    private Boundary table;

    private int countdown = 90;

    private boolean ingame;
    private boolean nom;
    private boolean badFood;
    private int temp;

    private int score;
    private int playerLocation;
    private int counter = 0;
    private List<Food> foodList;
    private ArrayList<Integer> inactivePosition = new ArrayList<>();
    private ArrayList<Integer> activePosition = new ArrayList<>();

    int testX;
    int testY;



    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));

        pug = new Pug(PUG_X,PUG_Y);
        table = new Boundary(((B_WIDTH / 2) - 125), ((B_HEIGHT / 2) - 75));
        initFood();

        timer = new Timer(DELAY,this);
        timer.start();
    }

    private void initFood() {
        foodList = new ArrayList<>();
        if (inactivePosition.size() <= 0) {
            for (int i = 0; i <= 5; i++) {
                inactivePosition.add(i);
            }
        }
        for (int i = 1; i <= 6; i++) {
            foodList.add(randomFood(i));
        }
        for (Food food : foodList) {
            food.setVisible(false);
        }
    }


    private Food randomFood(int position) {
        Food randomFood;
        int foodX;
        int foodY;
        Random random = new Random();
        switch (position) {
            case 1:
                foodX = (int) (B_WIDTH * 0.00625) + random.nextInt((int) (B_WIDTH * 0.23));
                foodY = (int) (B_HEIGHT * 0.00833) + random.nextInt((int) (B_HEIGHT * 0.3783));
                randomFood = new Food(foodX, foodY);
                randomFood.setEaten(false);
                randomFood.setPosition(1);
                System.out.println("Food in sector 1");
                return randomFood;
            case 2:
                foodX = (int) (B_WIDTH * 0.344) + random.nextInt((int) (B_WIDTH * 0.1988));
                foodY = (int) (B_HEIGHT * 0.00833) + random.nextInt((int) (B_HEIGHT * 0.078333));
                randomFood = new Food(foodX, foodY);
                randomFood.setEaten(false);
                randomFood.setPosition(2);
                return randomFood;
            case 3:
                foodX = (int) (B_WIDTH * 0.6563) + random.nextInt( (int) (B_WIDTH * 0.22375));
                foodY = (int) (B_HEIGHT * 0.00833) + random.nextInt((int) (B_HEIGHT * 0.3783));
                randomFood = new Food(foodX, foodY);
                randomFood.setEaten(false);
                randomFood.setPosition(3);
                return randomFood;
            case 4:
                foodX = (int) (B_WIDTH * 0.00625) + random.nextInt((int) (B_WIDTH * 0.23));
                foodY = (int) (B_HEIGHT * 0.5) + random.nextInt((int) (B_HEIGHT * 0.37));
                randomFood = new Food(foodX, foodY);
                randomFood.setEaten(false);
                randomFood.setPosition(4);
                return randomFood;
            case 5:
                foodX = (int) (B_WIDTH * 0.344) + random.nextInt((int) (B_WIDTH * 0.1988));
                foodY = (int) (B_HEIGHT * 0.63) + random.nextInt((int) (B_HEIGHT * 0.23667));
                randomFood = new Food(foodX, foodY);
                randomFood.setEaten(false);
                randomFood.setPosition(5);
                return randomFood;
            case 6:
                foodX = (int) (B_WIDTH * 0.6563)  + random.nextInt((int) (B_WIDTH * 0.22375));
                foodY = (int) (B_HEIGHT * 0.5) + random.nextInt((int) (B_HEIGHT * 0.37));
                randomFood = new Food(foodX, foodY);
                randomFood.setEaten(false);
                randomFood.setPosition(6);
                return randomFood;
            default:
                randomFood = new Food(5,5);
                return randomFood;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ingame) {
            drawObject(g);
            if (nom) {
                System.out.println("Nom received");
                nom(g);
            }
        }
        else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObject(Graphics g) {

        if (table.isVisible()) {
            g.drawImage(table.getImage(), (B_WIDTH / 2) - 125, (B_HEIGHT / 2) - 75, this);
        }
        for (Food food : foodList) {
            if (food.isVisible()) {
                g.drawImage(food.getImage(), food.getX(),food.getY(), this);
            }
        }
        if (pug.isVisible()) {
            g.drawImage(pug.getImage(), pug.getX(), pug.getY(), this);
        }
        g.setColor(Color.white);
        //HEALTH/SCORE BAR
        g.drawRect(50,30,150,15);

        g.setColor(Color.GREEN);
        g.fillRect(50,30,score,16);

        if (badFood) {
            System.out.println("BadFOOD");
            g.setColor(Color.RED);
            g.fillRect(50 + score,30, 8,16);
        }

        g.setColor(Color.WHITE);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        String timeMsg = "Time: " + String.format("%02d", countdown / 60) + ":" + String.format("%02d", countdown % 60);
        g.drawString(timeMsg, (B_WIDTH - 50) - fm.stringWidth(timeMsg),40);
    }

    private void nom(Graphics g) {
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Nom", testX, testY);
    }

    private void drawGameOver(Graphics g) {
        String msg = "You Win!";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updatePug();
        updateFood();
        playerLocation();
        checkCollisions();
        repaint();
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
        if (score >= 150) {
            ingame = false;
        }
        if (countdown <= 0) {
            ingame = false;
        }
    }

    private void updatePug() {
        if (pug.isVisible()) {
            pug.move();
        }
    }

    private void playerLocation() {
        Rectangle2D sector1 = new Rectangle2D.Double(5,5, 270, 295);
        Rectangle2D sector2 = new Rectangle2D.Double(275,5, 250, 215);
        Rectangle2D sector3 = new Rectangle2D.Double(525,5, 270, 295);

        Rectangle2D sector4 = new Rectangle2D.Double(5,300, 270, 295);
        Rectangle2D sector5 = new Rectangle2D.Double(275,380, 250, 215);
        Rectangle2D sector6 = new Rectangle2D.Double(525,300, 270, 295);

        Rectangle2D player = pug.getBounds();

        if(player.intersects(sector1)) {
            playerLocation = 1;
        }
        if(player.intersects(sector2)) {
            playerLocation = 2;
        }
        if(player.intersects(sector3)) {
            playerLocation = 3;
        }
        if(player.intersects(sector4)) {
            playerLocation = 4;
        }
        if(player.intersects(sector5)) {
            playerLocation = 5;
        }
        if(player.intersects(sector6)) {
            playerLocation = 6;
        }
    }

    private void updateFood() {
        counter++;
        Random random = new Random();
        int randomInt;

       if (counter % 100 == 0) {
           countdown -= 1;
           randomInt = random.nextInt(inactivePosition.size());
           if (inactivePosition.get(randomInt) == (playerLocation - 1)) {
               if (randomInt > 0) {
                   randomInt--;
               } else randomInt++;
           }
           foodList.get(inactivePosition.get(randomInt)).setVisible(true);

           activePosition.add(inactivePosition.get(randomInt));
           inactivePosition.remove(randomInt);

           if (activePosition.size() > 4 || counter % 400 == 0) {
               foodList.get(activePosition.get(0)).setVisible(false);
               foodList.set(activePosition.get(0), randomFood(foodList.get(activePosition.get(0)).getPosition()));
               foodList.get(activePosition.get(0)).setVisible(false);

               inactivePosition.add(activePosition.get(0));

               activePosition.remove(0);
           }
       }
       if (nom) {
           System.out.println("Nom");
           if (counter > temp) {
               nom = false;
               System.out.println("Nom down");
               badFood = false;
           }
       }

    }

    private void checkCollisions()  {
        //THE PLAYERS BOUNDS - ADDITIONAL RECTANGLES ARE FOR CHECKING COLLISION AT ANGLES, THEY REPRESENT THE FOUR COURNERS OF THE PLAYER RECTANGLE
        Rectangle2D pugBounds =  pug.getBounds();

        //top left corner
        Rectangle2D pugA = new Rectangle2D.Double(pugBounds.getX(), pugBounds.getY(), 3.0, 3.0);
        //top right corner
        Rectangle2D pugB = new Rectangle2D.Double(pugBounds.getX() + pugBounds.getWidth(), pugBounds.getY(), 3.0, 3.0);
        //bottom left corner
        Rectangle2D pugC = new Rectangle2D.Double(pugBounds.getX(), pugBounds.getY() + pugBounds.getHeight(), 3.0, 3.0);
        //bottom right corner
        Rectangle2D pugD = new Rectangle2D.Double(pugBounds.getX() + pugBounds.getWidth(), pugBounds.getY() + pugBounds.getHeight(), 3.0, 3.0);

        //TABLE BOUNDS - LINES FOR TABLE COLLISION
        Rectangle2D tableBounds = new Rectangle((B_WIDTH /2) - 125 ,(B_HEIGHT/2) - 75, 250,150);

        Line2D leftEdge = new Line2D.Double(tableBounds.getX(),tableBounds.getY(),tableBounds.getX(),tableBounds.getY() + tableBounds.getHeight());
        Line2D rightEdge = new Line2D.Double(tableBounds.getX() + tableBounds.getWidth(),tableBounds.getY(),tableBounds.getX() + tableBounds.getWidth(),tableBounds.getY() + tableBounds.getHeight());
        Line2D topEdge = new Line2D.Double(tableBounds.getX(), tableBounds.getY(), tableBounds.getX() + tableBounds.getWidth(),tableBounds.getY());
        Line2D bottomEdge = new Line2D.Double(tableBounds.getX(), tableBounds.getY() + tableBounds.getHeight(), tableBounds.getX() + tableBounds.getWidth(), tableBounds.getY() + tableBounds.getHeight());

        //FOOD COLLISION
        for (Food food : foodList) {
            Rectangle2D foodRec = food.getBounds();
            if (pugBounds.intersects(foodRec)) {
                if (food.isVisible()) {
                    score += food.getFoodHP();
                    food.setVisible(false);
                    food.setEaten(true);

                    nom = true;
                    temp = counter + 65;
                    testX = (int) foodRec.getX();
                    testY = (int) foodRec.getY();

                    int foodPos = food.getPosition();
                    int actIndex = activePosition.indexOf(foodPos - 1);
                    if (food.getFoodType().equals("pepper")) {
                        badFood = true;
                    }

                    inactivePosition.add(activePosition.get(actIndex));
                    activePosition.remove(actIndex);

                    int indexObj = foodList.indexOf(food);
                    foodList.set(indexObj, randomFood(foodPos));
                    foodList.get(indexObj).setVisible(false);
                }

            }
        }

        //TABLE COLLISION
        //
        //CLEAN COLLISION - LEFT, RIGHT, TOP, BOTTOM
        if (pug.getDirection() == 0 || pug.getDirection() == 1 || pug.getDirection() == 2 || pug.getDirection() == 3) {
            if ((pug.getDirection() == 0) && pugBounds.intersectsLine(leftEdge)) {
                pug.collide(tableBounds);
            }
            if ((pug.getDirection() == 1) && pugBounds.intersectsLine(rightEdge)) {
                pug.collide(tableBounds);
            }
            if ((pug.getDirection() == 2) && pugBounds.intersectsLine(topEdge)) {
                pug.collide(tableBounds);
            }
            if ((pug.getDirection() == 3) && pugBounds.intersectsLine(bottomEdge)) {
                pug.collide(tableBounds);
            }
        }
        //COLLISION AT AN ANGLE
        else if (pug.getDirection() == 4 || pug.getDirection() == 5 || pug.getDirection() == 6 || pug.getDirection() == 7) {
            if (pug.getDirection() == 4 || pug.getDirection() == 6) {
                if ((pugC.intersectsLine(topEdge) || pugD.intersectsLine(topEdge)) || (pugC.intersectsLine(topEdge) && pugD.intersectsLine(topEdge))) {
                    pug.collide(tableBounds, "top");
                }
            }
            if (pug.getDirection() == 5 || pug.getDirection() == 7) {
                if ((pugA.intersectsLine(bottomEdge) || pugB.intersectsLine(bottomEdge)) || (pugA.intersectsLine(bottomEdge) && pugB.intersectsLine(bottomEdge))) {
                    pug.collide(tableBounds, "bottom");
                }
            }
            if (pug.getDirection() == 4 || pug.getDirection() == 5) {
                if ((pugB.intersectsLine(leftEdge) || pugD.intersectsLine(leftEdge)) || (pugB.intersectsLine(leftEdge) && pugD.intersectsLine(leftEdge))) {
                    pug.collide(tableBounds, "left");
                }
            }
            if (pug.getDirection() == 6 || pug.getDirection() == 7) {
                if ((pugA.intersectsLine(rightEdge) || pugC.intersectsLine(rightEdge)) || (pugA.intersectsLine(rightEdge) && pugC.intersectsLine(rightEdge))) {
                    pug.collide(tableBounds, "right");
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            pug.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pug.keyReleased(e);
        }
    }
}

public class Main extends JFrame {

    private Board board = new Board();
    public Main() {
        initUI();
    }

    private void initUI() {
        add(board);
        setResizable(false);
        pack();

        setTitle("Pug Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main collision = new Main();
            collision.setVisible(true);
        });
    }
}