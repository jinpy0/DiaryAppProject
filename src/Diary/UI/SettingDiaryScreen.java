package Diary.UI;

import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;

public class SettingDiaryScreen extends JFrame {
    private String imagePath; // 이미지 경로
    private DiaryDTO selectedDiary; // 선택된 일기 객체

    public SettingDiaryScreen(DiaryDTO diary, Connection conn) {
        // UserSession에서 사용자 정보 가져오기
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        this.selectedDiary = diary;
        this.imagePath = diary.getDiaryImage(); // 저장된 사진 경로를 초기값으로 설정

        setTitle("일기 수정 페이지");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // 사진 표시 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(120, 120));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 저장된 사진 경로를 사용하여 이미지 설정
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText(null);
        } else {
            imageLabel.setText("이미지 없음");
        }
        imagePanel.add(imageLabel);

        // 사진 선택 버튼 패널
        JPanel imgBtnPanel = new JPanel();
        JButton imgBtn = new JButton("사진선택");
        imgBtnPanel.add(imgBtn);

        // 뒤로가기 버튼과 다음 버튼 패널
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton nextBtn = new JButton("다음");
        btnPanel.add(backBtn);
        btnPanel.add(nextBtn);

        // 추가된 컴포넌트들
        add(imagePanel);
        add(imgBtnPanel);
        add(btnPanel);

        // 사진 선택 버튼 이벤트 처리
        imgBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(SettingDiaryScreen.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath(); // 이미지 경로 업데이트

                // 이미지 표시
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
                imageLabel.setText(null); // 기존 텍스트 제거
            }
        });

        // 뒤로가기 버튼 이벤트 처리
        // 뒤로가기 버튼 이벤트 처리
        backBtn.addActionListener(e -> {
            new DiaryListScreen(conn); // 다이어리 목록 화면으로 이동
            dispose();
        });

        // 다음 버튼 이벤트 처리
        nextBtn.addActionListener(e -> {
            // 선택된 사진 경로 업데이트
            selectedDiary.setDiaryImage(imagePath);

            // SettingDiaryScreen2로 이동
            new SettingDiaryScreen2(selectedDiary, conn); // 제목, 날짜, 내용을 수정할 수 있는 화면으로 이동
            dispose();
        });

        setVisible(true);
    }
}
