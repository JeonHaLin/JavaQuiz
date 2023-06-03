import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.*;

public class GameSettings extends JFrame {
    private JComboBox<String> fontComboBox;
    private JComboBox<String> fontSizeComboBox;
    private JComboBox<String> windowSizeComboBox;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private JSlider volumeSlider;
    private JButton saveButton;

    // 파일 경로
    private static final String SETTINGS_FILE = "settings.txt";

    public GameSettings() {
        setTitle("Game Settings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));
        setResizable(false); // 창 크기 조정 비활성화

        // Font ComboBox
        JLabel fontLabel = new JLabel("  Font:"); // 폰트
        fontComboBox = new JComboBox<>(new String[]{"Arial", "Times New Roman", "Consolas"});
        fontComboBox.addActionListener(e -> {
            updateFont();
            saveSettings();
        });
        add(fontLabel);
        add(fontComboBox);

        // Font Size ComboBox
        JLabel fontSizeLabel = new JLabel("  Font Size:"); // 폰트 크기
        fontSizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        fontSizeComboBox.addActionListener(e -> {
            updateFont();
            saveSettings();
        });
        add(fontSizeLabel);
        add(fontSizeComboBox);

        // Window Size ComboBox
        JLabel windowSizeLabel = new JLabel("  Window Size:"); // 창 크기
        windowSizeComboBox = new JComboBox<>(new String[]{"400x400", "600x600", "800x800"});
        windowSizeComboBox.addActionListener(e -> {
            updateWindowSize();
            saveSettings();
        });
        add(windowSizeLabel);
        add(windowSizeComboBox);

        // Red Slider
        JLabel redLabel = new JLabel("  Red:"); // 빨강
        redSlider = new JSlider(0, 255, 255);
        redSlider.addChangeListener(e -> {
            updateColor();
            saveSettings();
        });
        add(redLabel);
        add(redSlider);

        // Green Slider
        JLabel greenLabel = new JLabel("  Green:"); // 초록
        greenSlider = new JSlider(0, 255, 255);
        greenSlider.addChangeListener(e -> {
            updateColor();
            saveSettings();
        });
        add(greenLabel);
        add(greenSlider);

        // Blue Slider
        JLabel blueLabel = new JLabel("  Blue:"); // 파랑
        blueSlider = new JSlider(0, 255, 255);
        blueSlider.addChangeListener(e -> {
            updateColor();
            saveSettings();
        });
        add(blueLabel);
        add(blueSlider);

        // Volume Slider
        JLabel volumeLabel = new JLabel("  Volume:"); // 볼륨
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(50);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(false);
        volumeSlider.addChangeListener(e -> saveSettings());
        add(volumeLabel);
        add(volumeSlider);

        add(new JLabel());

        // Save Button
        saveButton = new JButton("Save"); // 저장
        saveButton.addActionListener(e -> {
            saveSettings();
            dispose();
        });
        add(saveButton);

        loadSettings();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveSettings() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(SETTINGS_FILE));
            String selectedFont = (String) fontComboBox.getSelectedItem();
            String selectedFontSize = (String) fontSizeComboBox.getSelectedItem();
            String selectedWindowSize = (String) windowSizeComboBox.getSelectedItem();
            int red = redSlider.getValue();
            int green = greenSlider.getValue();
            int blue = blueSlider.getValue();
            int selectedVolume = volumeSlider.getValue();

            writer.write(selectedFont);
            writer.newLine();
            writer.write(selectedFontSize);
            writer.newLine();
            writer.write(selectedWindowSize);
            writer.newLine();
            writer.write(String.valueOf(red));
            writer.newLine();
            writer.write(String.valueOf(green));
            writer.newLine();
            writer.write(String.valueOf(blue));
            writer.newLine();
            writer.write(String.valueOf(selectedVolume));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFont() {
        String font = (String) fontComboBox.getSelectedItem();
        String fontSize = (String) fontSizeComboBox.getSelectedItem();
        Font newFont = new Font(font, Font.PLAIN,
                "Small".equals(fontSize) ? 12 :
                        "Medium".equals(fontSize) ? 18 : 24);
        updateComponentFont(this, newFont);
    }

    private void updateComponentFont(Component comp, Font font) {
        comp.setFont(font);
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                updateComponentFont(child, font);
            }
        }
    }

    private void updateWindowSize() {
        String windowSize = (String) windowSizeComboBox.getSelectedItem();
        String[] dimensions = windowSize.split("x");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        setSize(width, height);
    }

    private void updateColor() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();

        Color color = new Color(red, green, blue);
        getContentPane().setBackground(color);

        saveButton.setBackground(Color.lightGray); // 저장 버튼 색상
        saveButton.setForeground(color); // 저장 버튼 전경색
    }

    private void loadSettings() {
        try {
            File file = new File(SETTINGS_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_FILE));
                String font = reader.readLine();
                String fontSize = reader.readLine();
                String windowSizeString = reader.readLine();
                String red = reader.readLine();
                String green = reader.readLine();
                String blue = reader.readLine();
                String volume = reader.readLine();
                reader.close();

                if (font == null || fontSize == null || windowSizeString == null || red == null || green == null || blue == null || volume == null) {
                    throw new IOException("Settings file is corrupted.");
                }

                fontComboBox.setSelectedItem(font);
                fontSizeComboBox.setSelectedItem(fontSize);
                windowSizeComboBox.setSelectedItem(windowSizeString);

                int redValue = red.isEmpty() ? 255 : Integer.parseInt(red);
                int greenValue = green.isEmpty() ? 255 : Integer.parseInt(green);
                int blueValue = blue.isEmpty() ? 255 : Integer.parseInt(blue);
                int volumeValue = volume.isEmpty() ? 50 : Integer.parseInt(volume);

                redSlider.setValue(redValue);
                greenSlider.setValue(greenValue);
                blueSlider.setValue(blueValue);
                volumeSlider.setValue(volumeValue);

                updateFont();
                updateWindowSize();
                updateColor();
            } else {
                loadDefaultSettings();
            }
        } catch (IOException e) {
            e.printStackTrace();
            loadDefaultSettings();
        }
    }

    private void loadDefaultSettings() {
        String defaultFont = "Consolas";
        String defaultFontSize = "Medium";
        String defaultWindowSize = "600x600";
        int defaultRed = 255;
        int defaultGreen = 255;
        int defaultBlue = 255;
        int defaultVolume = 50;

        fontComboBox.setSelectedItem(defaultFont);
        fontSizeComboBox.setSelectedItem(defaultFontSize);
        windowSizeComboBox.setSelectedItem(defaultWindowSize);
        redSlider.setValue(defaultRed);
        greenSlider.setValue(defaultGreen);
        blueSlider.setValue(defaultBlue);
        volumeSlider.setValue(defaultVolume);

        updateFont();
        updateWindowSize();
        updateColor();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameSettings();
            }
        });
    }

}
