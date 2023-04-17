import javax.swing.JFrame;

public class frame extends JFrame {

    frame(){
        this.setTitle("SnakeGame");
        this.add(new panel());
        this.setVisible(true);
        this.pack();


    }
    
}
