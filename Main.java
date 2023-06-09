import GameWindow.GameRun;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends GameRun{
    private JButton startButton;
    private JButton scoreBoardButton;
    private JButton exitButton;

    public Main() {
        JFrame frame = new JFrame("그림 퀴즈");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ImageIcon gifIcon = new ImageIcon("gif/home.jpg");
        Image image = gifIcon.getImage();
        Image resizedImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel gifLabel = new JLabel(resizedIcon);
        gifLabel.setBounds(0,0,600,400);

        JLabel TextLabel = new JLabel("Java Quiz");
        Font font = new Font("Arial", Font.BOLD, 36);
        TextLabel.setFont(font);
        TextLabel.setBounds(215,-25,200,200);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        panel.add(TextLabel, BorderLayout.CENTER);
        panel.add(gifLabel, BorderLayout.CENTER);

        BGMManager BGM = new BGMManager("music/sqidgameBGM.wav");
        Thread BGMThread = new Thread(BGM);
        BGMThread.start();

        startButton = new JButton("게임 시작");
        startButton.setBounds(200, 175, 200, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameRun game = new GameRun();
                game.isStart = true;

                startButton.setVisible(false);
                scoreBoardButton.setVisible(false);
                exitButton.setVisible(false);
                panel.setVisible(false);

                frame.getContentPane().add(game.JP);
                frame.setSize(800,600);
                frame.revalidate();
                frame.repaint();
            }
        });
        panel.add(startButton);

        // 순위표 보기 버튼 초기화
        scoreBoardButton = new JButton("순위표 보기");
        scoreBoardButton.setBounds(200, 225, 200, 30);
        scoreBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //순위표 함수
                ScoreBoardManager bd = new ScoreBoardManager();
            }
        });
        panel.add(scoreBoardButton);

        // 종료 버튼 초기화
        exitButton = new JButton("종료");
        exitButton.setBounds(200, 275, 200, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    public static void main(String args[]){
        new Main();
    }
}