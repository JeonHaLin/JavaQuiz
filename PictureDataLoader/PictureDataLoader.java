package PictureDataLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureDataLoader extends JFrame{
	private static final long serialVersionUID = 1L;
	protected static JLabel pictureLabel; // 그림을 표시할 레이블
	private Map<String, String> pictureData; // 그림 데이터를 저장할 맵
    private List<String> answerChoices; // 정답 선지 리스트
	protected List<String> pictureKeys;
	public PictureDataLoader(){
		 // 그림 데이터 초기화
        pictureData = new HashMap<>();
        // 그림 폴더 경로 설정
        String pictureFolderPath = "image";//그림경로 적 
        
        // 그림 폴더에서 이미지 파일들을 읽어서 그림 데이터에 추가
        File folder = new File(pictureFolderPath); 
        if (folder.isDirectory()) { //folder 가 directory
            File[] files = folder.listFiles(); //이미지 경로에 이미지 파일 리스트들을 배열로 설정
            if (files != null) { //아무것도 열
                for (File file : files) { //배열 files배열에 있는 애들 file에 하나씩 넣으면서 반환
                    if (isImageFile(file)) { //file이 이미지 파일이면
                        String filePath = file.getAbsolutePath(); //file의 경로
                        String fileName = file.getName(); //file의 이름
                        pictureData.put(filePath, fileName); //그리고 pictureData에 Hashmap형태로 key는 경로 value는 이름으로 저
                    }
                }
            }
        }
        pictureLabel = new JLabel();
        pictureLabel.setPreferredSize(new Dimension(300, 200));
        add(pictureLabel);
		}
	// 그림 레이블에 이미지를 표시하는 메서드
		protected void displayPicture(String pictureKey) {
			try {
				BufferedImage image = ImageIO.read(new File(pictureKey));
				Image scaledImage = image.getScaledInstance(pictureLabel.getWidth(), pictureLabel.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon pictureIcon = new ImageIcon(scaledImage);
				pictureLabel.setIcon(pictureIcon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 다음 그림을 표시하는 메서드
		private void displayNextPicture() {
        // 그림 데이터가 남아있는지 확인
			if (!answerChoices.isEmpty()) {
			// 이미 선택한 그림의 키(그림 파일명) 가져오기
				String currentPictureKey = answerChoices.get(0);
            // 다음 그림의 키(그림 파일명) 가져오기
				answerChoices.remove(0);
				if (!answerChoices.isEmpty()) {
					String nextPictureKey = answerChoices.get(0);
				}
			}
		}
	//파일이 이미지 파일인지 확인하는 메서드
	private boolean isImageFile(File file) {
		String extension = getFileExtension(file);
		return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
	}
	// 파일의 확장자를 변환하는 메서드
	 private String getFileExtension(File file) {
	        String extension = "";
	        String fileName = file.getName();
	        int dotIndex = fileName.lastIndexOf(".");
	        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
	            extension = fileName.substring(dotIndex + 1).toLowerCase();
	        }
	        return extension;
	    }
	 //게임을 시작하는 메서드
	 protected void startGame() {
		 // 그림 데이터의 키(그림 파일명)를 리스트로 변환
	        pictureKeys = new ArrayList<>(pictureData.keySet()); //이미지 파일들의 경로배열을 pictureKeys배열에 저

	        // 그림 데이터를 랜덤으로 섞음
	        Collections.shuffle(pictureKeys); //이걸로 경로들 섞
	 }
}
       
