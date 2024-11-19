package Diary.UI;

import Diary.DataBase.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewDiaryScreen extends JFrame {
    private String imagePath = "파일경로.jpg";
    private User user; // User 객체를 저장할 변수

    // 생성자에서 User 객체를 받도록 수정
    public NewDiaryScreen(User user) {
        this.user = user; // 전달받은 User 객체를 클래스 필드에 저장

        setTitle("일기 작성 페이지");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // 빈 패널 (상단 여백 역할)
        JPanel emptyPanel = new JPanel();

        // 사진 선택 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(120, 120));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setIcon(new ImageIcon(imagePath));
        imagePanel.add(imageLabel);

        // 사진 선택 버튼 패널
        JPanel imgBtnPanel = new JPanel();
        JButton imgBtn = new JButton("사진선택");
        imgBtnPanel.add(imgBtn);

        // 뒤로가기 버튼, 다음 버튼 패널
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton nextBtn = new JButton("다음");
        btnPanel.add(backBtn);
        btnPanel.add(nextBtn);

        add(emptyPanel);
        add(imagePanel);
        add(imgBtnPanel);
        add(btnPanel);

        imgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(NewDiaryScreen.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();

                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(null); // 기존 텍스트 제거
                }
            }
        });

        backBtn.addActionListener(e -> {
            new DiaryListScreen(user); // 뒤로가기 버튼에서 user 정보를 DiaryListScreen으로 전달
            dispose();
        });

        nextBtn.addActionListener(e -> {
            new NewDiaryScreen2(user); // 다음 버튼에서 user 정보를 NewDiaryScreen2로 전달
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewDiaryScreen(new User("user123", "홍길동", "email@example.com"))); // 임시로 User 객체를 전달
    }
}
