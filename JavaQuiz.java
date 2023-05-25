import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaQuiz extends JFrame {
	private Map<String, String> pictureData; // 그림 데이터를 저장할 맵
	private JLabel pictureLabel; // 그림을 표시할 레이블
	private Timer timer; // 게임 타이머
	private int score; // 점수
	private int correctAnswers; // 정답 개수
	private JButton startButton; // 게임 시작 버튼
	private JButton scoreBoardButton; // 순위표 보기 버튼
	private JButton resetButton; // 이름 초기화 버튼
	private List<String> answerChoices; // 정답 선지 리스트
	private ButtonGroup choiceButtonGroup; // 라디오 버튼 그룹

	// 생성자
	public JavaQuiz() {
		setTitle("Picture Quiz Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 350));
		setLayout(new FlowLayout());

		// 그림 데이터 초기화
		pictureData = new HashMap<>();

		// 그림 폴더 경로 설정
		String pictureFolderPath = "image";

		// 그림 폴더에서 이미지 파일들을 읽어서 그림 데이터에 추가
		File folder = new File(pictureFolderPath);
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (isImageFile(file)) {
						String filePath = file.getAbsolutePath();
						String fileName = file.getName();
						pictureData.put(filePath, fileName);
					}
				}
			}
		}

		// 그림 레이블 초기화
		pictureLabel = new JLabel();
		pictureLabel.setPreferredSize(new Dimension(300, 200));
		add(pictureLabel);

		// 시작 버튼 초기화
		startButton = new JButton("게임 시작");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		add(startButton);

		// 순위표 보기 버튼 초기화
		scoreBoardButton = new JButton("순위표 보기");
		scoreBoardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showScoreBoard();
			}
		});
		add(scoreBoardButton);

		// 이름 초기화 버튼 초기화
		resetButton = new JButton("이름 초기화");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetName();
			}
		});
		add(resetButton);

		pack();
		setVisible(true);
	}

	// 게임 시작 메소드
	private void startGame() {
		score = 0;
		correctAnswers = 0;

		// 그림 데이터의 키(그림 파일명)를 리스트로 변환
		List<String> pictureKeys = new ArrayList<>(pictureData.keySet());

		// 그림 데이터를 랜덤으로 섞음
		Collections.shuffle(pictureKeys);

		// 첫 번째 그림 표시
		displayPicture(pictureKeys.get(0));

		// 선지 초기화
		answerChoices = new ArrayList<>();
		answerChoices.add(pictureData.get(pictureKeys.get(0)));
		// 선지 추가 작업을 수행하시기 바랍니다.

		// 메인 메뉴 버튼 숨기기
		startButton.setVisible(false);
		scoreBoardButton.setVisible(false);
		resetButton.setVisible(false);

		// 타이머 시작 (60초로 설정)
		timer = new Timer(60000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endGame();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	// 그림 표시 메소드
	private void displayPicture(String pictureKey) {
		// 그림 레이블에 이미지 설정
		try {
			BufferedImage image = ImageIO.read(new File(pictureKey));
			Image scaledImage = image.getScaledInstance(pictureLabel.getWidth(), pictureLabel.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon pictureIcon = new ImageIcon(scaledImage);
			pictureLabel.setIcon(pictureIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 선지 패널 초기화
		JPanel choicePanel = new JPanel();
		choicePanel.setLayout(new FlowLayout());

		choiceButtonGroup = new ButtonGroup();
		String correctAnswer = pictureData.get(pictureKey); // 현재 그림의 정답

		// 선지를 포함한 랜덤한 선지 리스트 생성
		answerChoices = new ArrayList<>();
		answerChoices.add(correctAnswer); // 정답 추가

		// 다른 선지를 랜덤하게 가져오기
		List<String> otherChoices = new ArrayList<>(pictureData.values());
		otherChoices.remove(correctAnswer); // 정답 제거
		Collections.shuffle(otherChoices);

		// 4개의 선지를 추가
		for (int i = 0; i < 3; i++) {
			answerChoices.add(otherChoices.get(i));
		}

		// 선지 순서 랜덤화
		Collections.shuffle(answerChoices);

		// 선지 라디오 버튼 생성
		for (String choice : answerChoices) {
			JRadioButton choiceRadioButton = new JRadioButton(choice);
			choiceRadioButton.setActionCommand(choice); // 선택지의 값을 ActionCommand로 설정
			choiceButtonGroup.add(choiceRadioButton);
			choicePanel.add(choiceRadioButton);
		}

		add(choicePanel);

		// 다음 그림으로 넘어가는 버튼 추가
		JButton nextButton = new JButton("다음 그림");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedAnswer = choiceButtonGroup.getSelection().getActionCommand();
				checkAnswer(selectedAnswer, correctAnswer);
			}
		});
		add(nextButton);

		pack();
	}

	// 정답 확인 및 처리 메소드
	private void checkAnswer(String selectedAnswer, String correctAnswer) {
		if (selectedAnswer.equals(correctAnswer)) {
			score += 10; // 정답일 경우 점수 증가
			correctAnswers++; // 정답 개수 증가
		}

		displayNextPicture();
	}

	// 다음 그림 표시 메소드
	private void displayNextPicture() {
		// 그림 데이터가 남아있는지 확인
		if (!answerChoices.isEmpty()) {
			// 이미 선택한 그림의 키(그림 파일명) 가져오기
			String currentPictureKey = answerChoices.get(0);

			// 다음 그림의 키(그림 파일명) 가져오기
			answerChoices.remove(0);
			if (!answerChoices.isEmpty()) {
				String nextPictureKey = answerChoices.get(0);

				// 선지 초기화
				answerChoices.clear();
				answerChoices.add(pictureData.get(nextPictureKey));
				// 선지 추가 작업을 수행하시기 바랍니다.

				// 그림 표시
				displayPicture(nextPictureKey);
			} else {
				endGame();
			}

			// 현재 그림의 버튼들 제거
			getContentPane().removeAll();
			revalidate();
			repaint();
		}
	}


	// 게임 종료 메소드
	private void endGame() {
		timer.stop();

		// 점수 계산
		int timeBonus = 60 - (int) (timer.getDelay() / 1000);
		int totalScore = score + timeBonus + (correctAnswers * 5);

		// 점수 저장 여부 확인
		int option = JOptionPane.showConfirmDialog(this, "게임 종료! 점수: " + totalScore + "점\n이름을 저장하시겠습니까?", "게임 종료", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			String name = JOptionPane.showInputDialog(this, "이름을 입력하세요:");
			saveScore(name, totalScore);
		}

		// 첫 화면으로 돌아가기
		resetGame();
	}

	// 게임 초기화 메소드
	private void resetGame() {
		getContentPane().removeAll();
		startButton.setVisible(true);
		scoreBoardButton.setVisible(true);
		resetButton.setVisible(true);
		revalidate();
		repaint();
	}

	// 순위표 보기 메소드
	private void showScoreBoard() {
		// 순위표 데이터 가져오기
		List<String> scoreBoardData = loadScoreBoard();

		// 순위표 다이얼로그 생성
		StringBuilder scoreBoardMessage = new StringBuilder();
		scoreBoardMessage.append("순위표\n");
		for (int i = 0; i < scoreBoardData.size(); i++) {
			scoreBoardMessage.append(i + 1).append(". ").append(scoreBoardData.get(i)).append("\n");
		}

		JOptionPane.showMessageDialog(this, scoreBoardMessage.toString(), "순위표", JOptionPane.INFORMATION_MESSAGE);
	}

	// 이름 초기화 메소드
	private void resetName() {
		String name = JOptionPane.showInputDialog(this, "초기화할 이름을 입력하세요:");
		deleteScore(name);
	}

	// 점수 저장 메소드
	private void saveScore(String name, int score) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("scores.txt", true))) {
			writer.println(name + ":" + score);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 순위표 불러오기 메소드
	private List<String> loadScoreBoard() {
		List<String> scoreBoardData = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				scoreBoardData.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scoreBoardData;
	}

	// 이름 삭제 메소드
	private void deleteScore(String name) {
		try {
			File inputFile = new File("scores.txt");
			File tempFile = new File("temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith(name + ":")) {
					writer.write(line);
					writer.newLine();
				}
			}

			reader.close();
			writer.close();

			if (!inputFile.delete()) {
				System.out.println("Failed to delete the file.");
				return;
			}

			if (!tempFile.renameTo(inputFile)) {
				System.out.println("Failed to rename the file.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 파일이 이미지 파일인지 확인하는 메소드
	private boolean isImageFile(File file) {
		String extension = getFileExtension(file);
		return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
	}

	// 파일의 확장자 가져오는 메소드
	private String getFileExtension(File file) {
		String extension = "";
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			extension = fileName.substring(dotIndex + 1).toLowerCase();
		}
		return extension;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JavaQuiz();
			}
		});
	}
}
