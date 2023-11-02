import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App extends JPanel implements KeyListener{

    public static final int CELL_SIZE = 20;                                 //每個的大小
    public static int height = 400;                                         //視窗大小:400*400
    public static int width = 400;
    public static int row = height / CELL_SIZE;                             //共有多少個列 , 視窗寬度/格子大小        
    public static int col = width / CELL_SIZE;
    private Snake snake;
    private Fruit fruit;
    private Timer t;
    private int speed = 150;                                                //100ms
    private static String direction;
    private boolean allowKeyPress;
    private int score;
    private int highest_Score;
    String desktop = System.getProperty("user.home") + "/Desktop/";   
    String myFile = desktop + "Score.txt";

    public App(){
        read_Highest_Score();
        reset();
        addKeyListener(this);           
    }

    private void setTimer(){
        t = new Timer();
        //在一個固定時間中執行某些事
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                repaint();
            }
        }, 0, speed);  
    }

    private void reset(){
        score = 0;
        if(snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        setTimer();
    }
    
    @Override
    public void paintComponent(Graphics g){
        //check if the snake bites itself
        ArrayList<Node> snake_body = snake.getSnakeBody();
        Node head = snake_body.get(0);
        for(int i=1;i<snake_body.size();i++){
            if(snake_body.get(i).x == head.x && snake_body.get(i).y == head.y){
                allowKeyPress = false;
                t.cancel();                                     //暫停
                t.purge();
                int response = JOptionPane.showOptionDialog(this, "Game Over!! Your Score is " + score + "\nThe highest Score is: " + highest_Score +"\nWould you like start over?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_OPTION);
                write_a_file(score);
                switch (response) {
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }

        
        //draw a black backdround;
        g.fillRect(0, 0,width, height);
        fruit.drawFruit(g);
        snake.drawSnake(g);


        //remove snake tail and put in head
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;
        if(direction.equals("Left")){
            snakeX -= CELL_SIZE;
        }
        else if(direction.equals("Right")){
            snakeX += CELL_SIZE;
        }
        else if(direction.equals("Up")){
            snakeY -= CELL_SIZE;
        }
        else if(direction.equals("Down")){
            snakeY += CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);

        //Check if the snake eat the fruit
        if(snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()){
            //1. set fruit to a new location
            //2. drawFruit
            //3. score++
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
            score++;
        }else{
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
        }

        
        snake.getSnakeBody().add(0, newHead);;                    //在0的地方加入newHead;

        //right, x+=CELL_SIZE;  left, x-=CELL-SIZE;
        //up, y-=CELL_SIZE;     down, y+=CELL_SIZE;

        allowKeyPress = true;
        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(height, width);
    }

    
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new App());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);                         //不允許更改視窗大小
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(allowKeyPress == true){ 
        if(e.getKeyCode() == 87 && !direction.equals("Down")){
            direction = "Up";
        }
        else if(e.getKeyCode() == 83 && !direction.equals("Up")){
            direction = "Down";
        }
        else if(e.getKeyCode() == 65 && !direction.equals("Right")){
            direction = "Left"; 
        }
        else if(e.getKeyCode() == 68 && !direction.equals("Left")){
            direction = "Right";
        }
        allowKeyPress = false;
    }
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    public void read_Highest_Score(){
        try{
            File myObj = new File(myFile);
            Scanner myReader = new Scanner(myObj);
            highest_Score = myReader.nextInt();
            myReader.close();
        }catch(FileNotFoundException e){                        //沒有檔案表示之前沒有玩過遊戲
            highest_Score = 0;
            try{
                File myObj = new File(myFile);    //新增一個score.txt
                if(myObj.createNewFile()){
                    System.out.println("File created : " + myObj.getName());
                }
                FileWriter myWriter = new FileWriter(myObj.getName());
                myWriter.write(""+0);
            }catch(IOException err){
                System.out.println("An error occurred");
                err.printStackTrace();
            }
        }
    }

    public void write_a_file(int score){
        try{
            FileWriter myWriter = new FileWriter(myFile);
            if(score > highest_Score){
                myWriter.write(""+ score);
                highest_Score = score;
            }else{
                myWriter.write(""+ highest_Score);  
            }
            myWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
