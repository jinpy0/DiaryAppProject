package Diary.UI;

import Diary.DataBase.DataBase;
import Diary.DataBase.Diary;
import Diary.DataBase.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SettingDiaryScreen extends JFrame {
    private String imagePath = "파일경로.jpg"; // 이미지 경로
    private Diary selectedDiary; // 선택된 일기 객체
    private User user; // 사용자 객체

    // 생성자에서 Diary와 User 객체를 받아오기
    public SettingDiaryScreen(User user, Diary diary) {
        this.user = user; // 사용자 객체 설정
        this.selectedDiary = diary; // 수정할 일기 객체 설정

        setTitle("일기 수정 페이지");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        // 제목 수정 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("제목: ");
        JTextField titleField = new JTextField(diary.getTitle(), 20); // 기존 제목을 텍스트 필드로 표시
        titlePanel.add(titleLabel);
        titlePanel.add(titleField);

        // 내용 수정 패널
        JPanel contentPanel = new JPanel();
        JLabel contentLabel = new JLabel("내용: ");
        JTextArea contentArea = new JTextArea(diary.getContent(), 5, 20); // 기존 내용을 텍스트 에어리어로 표시
        JScrollPane scrollPane = new JScrollPane(contentArea);
        contentPanel.add(contentLabel);
        contentPanel.add(scrollPane);

        // 사진 선택 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(120, 120));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setIcon(new ImageIcon(imagePath)); // 초기 이미지 설정
        imagePanel.add(imageLabel);

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

        // 컴포넌트들 추가
        add(titlePanel);
        add(contentPanel);
        add(imagePanel);
        add(imgBtnPanel);
        add(btnPanel);
        setVisible(true);

        // 사진 선택 버튼 이벤트 처리
        imgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(SettingDiaryScreen.this);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath(); // 이미지 경로 저장

                    // 이미지 표시
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(null); // 기존 텍스트 제거
                }
            }
        });

        // 뒤로가기 버튼 이벤트 처리
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DiaryListScreen(user); // User 객체 전달
                dispose();
            }
        });

        // 다음 버튼 이벤트 처리
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 수정된 제목과 내용 저장
                selectedDiary.setTitle(titleField.getText()); // 수정된 제목 저장
                selectedDiary.setContent(contentArea.getText()); // 수정된 내용 저장
                selectedDiary.setImagePath(imagePath); // 수정된 이미지 경로 저장

                new SettingDiaryScreen2(user, selectedDiary); // User 객체와 수정된 Diary 객체 전달
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        // 예시로 User 객체와 Diary 객체 전달
        SwingUtilities.invokeLater(() -> new SettingDiaryScreen(new User("user123", "홍길동", "hong@domain.com"), new Diary("제목", "내용", "이미지 경로")));
    }
}
