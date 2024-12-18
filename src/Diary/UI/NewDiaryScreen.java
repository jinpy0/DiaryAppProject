package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;

public class NewDiaryScreen extends JFrame {
    private String imagePath = "파일경로.jpg"; // 기본 이미지 경로

    public NewDiaryScreen(Connection conn) {
        // UserSession에서 현재 사용자 정보 가져오기
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        setTitle("일기 작성 페이지");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // 사진 선택 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(120, 120));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setIcon(new ImageIcon(imagePath)); // 기본 이미지 표시
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

        // 화면 구성 요소 추가
        add(imagePanel);
        add(imgBtnPanel);
        add(btnPanel);

        // 사진 선택 버튼 클릭 이벤트
        imgBtn.addActionListener(e -> {
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
                imageLabel.setText(null);
            }
        });

        // 뒤로가기 버튼 클릭 이벤트
        backBtn.addActionListener(e -> {
            new DiaryListScreen(conn); // 다이어리 목록 화면으로 이동
            dispose();
        });

        // 다음 버튼 클릭 이벤트
        nextBtn.addActionListener(e -> {
            new NewDiaryScreen2(conn, imagePath); // 다음 화면으로 이미지 경로 전달
            dispose();
        });

        setVisible(true);
    }
}
