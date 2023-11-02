import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Node> snakeBody;

    public Snake(){
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(80, 0));                 //設定蛇首次的位置
        snakeBody.add(new Node(60, 0));
        snakeBody.add(new Node(40, 0));
        snakeBody.add(new Node(20, 0));
    }

    public ArrayList<Node> getSnakeBody(){
        return snakeBody;
    }

    public void drawSnake(Graphics g){
            for(int i=0;i<snakeBody.size();i++){
                if(i == 0){
                    g.setColor(Color.yellow);
                }
                else{
                    g.setColor(Color.BLACK);
                }
            Node n = snakeBody.get(i);
            if(n.x >= App.width){
                n.x = 0; 
            }
            if(n.x < 0){
                n.x = App.width - App.CELL_SIZE;
            }
            if(n.y >= App.height){
                n.y = 0;
            }
            if(n.y < 0){
                n.y = App.height - App.CELL_SIZE;
            }
            g.fillOval(n.x, n.y, App.CELL_SIZE, App.CELL_SIZE);
            }
        }
    }

