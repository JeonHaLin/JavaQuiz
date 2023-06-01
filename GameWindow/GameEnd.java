package GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEnd extends JFrame{
    int score;
    public GameEnd(int score){
        this.score = score;
        setTitle("GAME OVER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        addScore(c);//use North
        addRanking(c);//use Center

        setVisible(true);
        setSize(200,300);
    }

    void addScore(Container c){
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel la = new JLabel();
        la.setText("Total Score : "+String.valueOf(score));
        la.setHorizontalAlignment(0);

        temp.add(la);

        c.add(temp,BorderLayout.NORTH);

    }

    void addRanking(Container c){

    }

    class sendRanking implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }

    class retryGame implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
