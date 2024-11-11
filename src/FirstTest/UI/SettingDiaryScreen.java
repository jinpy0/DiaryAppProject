package FirstTest.UI;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// 일기를 보다가 수정할 수 있는 페이지
public class SettingDiaryScreen extends JFrame {
    private String imagePath = "파일경로.jpg";

    public SettingDiaryScreen() {
        setTitle("일기 수정 페이지");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        add(Box.createVerticalStrut(30));

        // 빈 패널
        JPanel emptyPanel = new JPanel();

        // 사진 선택 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setIcon(new ImageIcon(imagePath));
        imagePanel.add(imageLabel);

        add(Box.createVerticalStrut(50));

        // 사진 선택 버튼 패널
        JPanel imgBtnPanel = new JPanel();
        JButton imgBtn = new JButton("사진선택");
        imgBtnPanel.add(imgBtn);

        // 뒤로가기 버튼, 다음 버튼
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton nextBtn = new JButton("다음");
        btnPanel.add(backBtn);
        btnPanel.add(nextBtn);

        add(emptyPanel);
        add(imagePanel);
        add(imgBtnPanel);
        add(btnPanel);
        setVisible(true);


        imgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(SettingDiaryScreen.this);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath(); // 이미지 경로 저장

                    // 이미지 표시
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(null); // 기존 텍스트 제거
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DiaryListScreen();
                dispose();
            }
        });

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingDiaryScreen2();
                dispose();
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(SettingDiaryScreen::new);
    }
}
