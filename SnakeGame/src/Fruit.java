import java.awt.Color;
import java.awt.Graphics;

public class Fruit {
    //水果出現的座標
    private int x;
    private int y;

    public Fruit(){
        this.x =(int)(Math.floor(Math.random() * App.col) * App.CELL_SIZE);
        this.y =(int)(Math.floor(Math.random() * App.row) * App.CELL_SIZE);
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public void drawFruit(Graphics g){
        g.setColor(Color.white);
        g.fillOval(this.x, this.y, App.CELL_SIZE, App.CELL_SIZE);
    }

    public void setNewLocation(Snake s){                            //避開蛇身體的地方
        int new_x;
        int new_y;
        boolean overlapping;
        do{
            new_x = (int)(Math.floor(Math.random() * App.col) * App.CELL_SIZE);
            new_y = (int)(Math.floor(Math.random() * App.row) * App.CELL_SIZE);
            overlapping = check_overlap(new_x, new_y, s);
        }while(overlapping);
        this.x = new_x;
        this.y = new_y;
    }

    private boolean check_overlap(int x, int y, Snake s){
        for(int j=0;j<s.getSnakeBody().size();j++){
            if(s.getSnakeBody().get(j).x == x && s.getSnakeBody().get(j).y == y)
                return true;
        }
        return false;
    }
}
