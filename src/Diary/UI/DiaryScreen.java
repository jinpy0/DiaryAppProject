package Diary.UI;

import Diary.DataBase.DataBase;
import Diary.DataBase.Diary; // Diary 클래스를 import
import Diary.DataBase.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiaryScreen extends JFrame {
    private User user; // 사용자 객체
    private Diary diary; // 일기 객체

    // 생성자에서 User 객체와 Diary 객체를 받아오기
    public DiaryScreen(User user, Diary diary) {
        this.user = user; // 전달된 User 객체 설정
        this.diary = diary; // 전달된 Diary 객체 설정

        setTitle("일기 보기");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 이미지 패널 (이미지 표시)
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 실제 이미지 파일을 표시할 수 있도록 수정 (예시로 파일 경로를 넣음)
        // 이미지 경로를 실제 이미지로 수정 필요
        imageLabel.setIcon(new ImageIcon("이미지 파일 경로"));

        imagePanel.add(imageLabel);

        // 제목 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(diary.getTitle()); // Diary 객체에서 제목 가져오기
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // 제목 폰트 설정
        titlePanel.add(titleLabel);

        // 내용 패널 (스크롤 가능한 영역으로 내용 표시)
        JPanel contentPanel = new JPanel();
        JTextArea contentTextArea = new JTextArea(diary.getContent()); // Diary 객체에서 내용 가져오기
        contentTextArea.setEditable(false); // 내용 수정 불가
        contentTextArea.setLineWrap(true); // 줄바꿈 처리
        contentTextArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈
        contentTextArea.setCaretPosition(0); // 처음부터 표시
        JScrollPane scrollPane = new JScrollPane(contentTextArea);
        contentPanel.add(scrollPane);

        // 제목과 내용 패널 합치는 패널
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titlePanel);
        panel.add(contentPanel);

        // 뒤로가기, 수정 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton setBtn = new JButton("수정");
        buttonPanel.add(backBtn);
        buttonPanel.add(setBtn);

        add(imagePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 뒤로가기 버튼 클릭 시
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DiaryListScreen(user); // User 객체 전달
                dispose();
            }
        });

        // 수정 버튼 클릭 시
        setBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingDiaryScreen(user, diary); // User 객체와 Diary 객체 전달
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        // User 객체와 Diary 객체를 예시로 전달하여 DiaryScreen 실행
        SwingUtilities.invokeLater(() -> new DiaryScreen(new User("user123", "홍길동", "hong@domain.com"), new Diary("일기 제목", "일기 내용")));
    }
}
