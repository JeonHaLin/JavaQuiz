import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
    private JButton startButton;
    private JButton scoreBoardButton;
    private JButton ExitButton;
    
    public Main(){
        setTitle("그림 퀴즈");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        BGMManager BGM = new BGMManager("주소입력");
        Thread BGMThread = new Thread(BGM);
        BGMThread.start();

        startButton = new JButton("게임 시작");
        startButton.setBounds(50,20,200,25);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //시작함수
            }
        });
        c.add(startButton);

        // 순위표 보기 버튼 초기화
        scoreBoardButton = new JButton("순위표 보기");
        startButton.setBounds(50,60,200,25);
        scoreBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //순위표 함수
            }
        });
        c.add(scoreBoardButton);

        // 이름 초기화 버튼 초기화
        ExitButton = new JButton("이름 초기화");
        ExitButton.setBounds(50,100,200,25);
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        c.add(ExitButton);

        setVisible(true);
    }
}
