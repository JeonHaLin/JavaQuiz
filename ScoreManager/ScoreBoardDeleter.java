package ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardDeleter extends JFrame implements ActionListener {
    private static final String FILE_PATH = "(점수가 저장되어 있는 텍스트 파일의 절대경로)";
    private JTextField scoreTextField;
    private JButton deleteButton;

    public ScoreBoardDeleter() {
        setTitle("Score Board Deleter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel scoreLabel = new JLabel("이름:점수를 입력하세요:");
        scoreTextField = new JTextField(10);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        add(scoreLabel);
        add(scoreTextField);
        add(deleteButton);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            String input = scoreTextField.getText();

            if (input.contains(":")) {
                String[] parts = input.split(":");
                String name = parts[0].trim();
                String score = parts[1].trim();

                deleteScore(name, score);
                JOptionPane.showMessageDialog(this, "점수가 삭제되었습니다.");

                // Clear the input field
                scoreTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "올바른 형식으로 입력하세요. (이름:점수)");
            }
        }
    }

    private void deleteScore(String name, String score) {
        List<String> scoreBoardData = loadScoreBoard();
        List<String> updatedScoreBoardData = new ArrayList<>();

        for (String scoreData : scoreBoardData) {
            if (!scoreData.equals(name + ":" + score)) {
                updatedScoreBoardData.add(scoreData);
            }
        }

        saveScoreBoard(updatedScoreBoardData);
    }

    private List<String> loadScoreBoard() {
        List<String> scoreBoardData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scoreBoardData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scoreBoardData;
    }

    private void saveScoreBoard(List<String> scoreBoardData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String scoreData : scoreBoardData) {
                writer.println(scoreData);
            }
        } catch (IOException e) {
            System.out.println("파일에 점수를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScoreBoardDeleter());
    }
}
