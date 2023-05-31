package GameWindow;

import PictureDataLoader.PictureDataLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static java.lang.Thread.sleep;


public class GameRun extends PictureDataLoader {
    public static void main(String[] args) {
        new GameRun();
    }

    JButton[] fourSelect; // 4지선다 그룹
    int totalScore=0;

    GameRun() {
        setTitle("Quiz Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        c.setLayout(new BorderLayout());



        //once
        this.setButtonGroup();
        this.addEvent();
        this.setButtonLocation(c);
        startGame();


        //multi
        this.renameFourSelect();
        this.addPicture(c);

        setVisible(true);
        setSize(500, 600);
        done(c);
        new GameEnd(totalScore);
    }

    void setButtonGroup() { // 4지 선다 버튼 그룹 생성 메서드
        fourSelect=new JButton[4];
        for(int i =0;i<4;i++){
            fourSelect[i]=new JButton();
            fourSelect[i].setSize(40,20);
        }
    }//한번만 실행

    void setButtonLocation(Container c){//버튼을 하단부에 위치시키는 매소드
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        for(int i =0;i<4;i++) {
            buttonPanel.add(fourSelect[i]);
        }
        c.add(buttonPanel, BorderLayout.SOUTH);
    }//한번만 실행

    void addEvent(){//각 버튼마다 이벤트를 추가시키는 메소드
        for(int i =0;i<4;i++){
            fourSelect[i].addActionListener(new AnswerEvent());
        }
    }//한번만 실행

    void renameFourSelect() { // 생성된 버튼에 이름을 다시 할당하는 메서드
        Random ran = new Random();
        for(int i =0;i<4;i++){
            fourSelect[i].setText(String.valueOf(ran.nextInt(30)));
        }
    }//여러번 실행

    void addPicture(Container c){//그림 추가 메소드
        displayNextPicture();
        displayPicture(pictureKeys.get(0));
        c.add(pictureLabel,BorderLayout.CENTER);
    }
    void done(Container c){//게임 종료까지 카운트하는 메소드
        try {
            sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        c.removeAll();

        JFrame temp = new JFrame();
        temp.setVisible(true);
        temp.setSize(100,200);
    }


    class AnswerEvent implements ActionListener {//게임중 정답을 누르는 버튼에 이벤트를 추가하는 메소드
        public void actionPerformed(ActionEvent e){
            JButton temp = (JButton) e.getSource();

            if(temp.getText().equals("Selected")){
                temp.setText("unSelected");
                totalScore+=1;
            }
            else{
                temp.setText("Selected");
            }

        }
    }

}


