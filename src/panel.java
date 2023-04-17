import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

import java.util.Arrays;
import java.util.Random;
import javax.swing.Timer;

public class panel extends JPanel implements ActionListener {

    int width=1200;
    int height=600;
    int score=0;
    int unit=50;
    int length = 3;
    int xsnake[] = new int[288];
    int ysnake[] = new int[288];
    char dir = 'R';
    boolean flag=false;
    Timer timer;
    Random random;
    int fx,fy;
    
    panel(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.addKeyListener(new key());
        this.setFocusable(true);
        random = new Random();
        gameStart();

    }

    public void gameStart(){
        spawnFood();
        flag=true;
        timer=new Timer(160, this);
        timer.start();
    }
    public void spawnFood(){
        fx=random.nextInt(width/unit)*unit;
        fy=random.nextInt(height/unit)*unit;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }


    public void draw(Graphics graphic){
        if(flag){
            graphic.setColor(Color.red);
            graphic.fillOval(fx, fy, 50, 50);

            for(int i = 0; i < length; i++){
                if(i!=0) {
                    graphic.setColor(Color.green);
                }
                else graphic.setColor(Color.orange);
                graphic.fillRect(xsnake[i], ysnake[i], 50, 50);
            }

             graphic.setColor(Color.cyan);
             graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
             FontMetrics fme = getFontMetrics(graphic.getFont());
             graphic.drawString("Score:"+score, (width - fme.stringWidth("Score:"+score))/2, graphic.getFont().getSize());
            }
        else{
            gameOver(graphic);
        }
    }


    public void gameOver(Graphics graphic){
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+score, (width - fme.stringWidth("Score:"+score))/2, graphic.getFont().getSize());

        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        fme = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER", (width - fme.stringWidth("GAME OVER"))/2, height/2);

        graphic.setColor(Color.green);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay", (width - fme.stringWidth("Press R to Replay"))/2, 3*height/4);
    }

    

    
    public void eat(){
        if((fx == xsnake[0]) && (fy == ysnake[0])){
            length++;
            score++;
            spawnFood();
        }
    }

    public void hit(){
        for( int i = length; i > 0; i--){
            if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])){
                flag = false;
            }
        }
        if(xsnake[0] < 0){
            flag = false;
        }
        else if(xsnake[0] > width){
            flag = false;
        }
        else if(ysnake[0] < 0){
            flag = false;
        }
        else if(ysnake[0] > height){
            flag = false;
        }
        if(flag == false){
            timer.stop();
        }

    }

    public void move(){
        for(int i = length ; i > 0; i--){
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }

        switch(dir){
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                break;
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;

        }

    }

    public class key extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(dir != 'D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir != 'R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(flag == false){
                        score = 0;
                        length = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        gameStart();
                    }
                    break;
            }
        }
    }






    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            hit();
            eat();

        }
        repaint();
    }



    
    
}
