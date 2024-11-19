package Diary.UI;

import Diary.DataBase.DataBase;
import Diary.DataBase.Diary;
import Diary.DataBase.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DiaryListScreen extends JFrame {
    private User user; // 사용자 객체
    private List<Diary> diaries; // 일기 목록

    // 생성자에서 User 객체를 받아오기
    public DiaryListScreen(User user) {
        this.user = user; // 전달된 User 객체 설정
        this.diaries = DataBase.getDiaries(user.getUser_id()); // 사용자 일기 목록 불러오기

        setTitle("일기 목록");
        setSize(350, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 사용자 정보 패널 설정
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        JLabel userIdLabel = new JLabel(user.getUser_id()); // User 객체에서 ID를 가져옴
        userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(userIdLabel);

        // 사용자 피드 패널 (일기 목록 표시)
        JPanel userFeed = new JPanel(new GridLayout(3, 3, 5, 5));
        if (diaries.isEmpty()) {
            JLabel noDiaryLabel = new JLabel("작성한 일기가 없습니다.");
            noDiaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            userFeed.add(noDiaryLabel);
        } else {
            for (Diary diary : diaries) {
                JLabel feedLabel = new JLabel(diary.getTitle());
                feedLabel.setPreferredSize(new Dimension(100, 100));
                feedLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                feedLabel.setHorizontalAlignment(SwingConstants.CENTER);
                userFeed.add(feedLabel);
            }
        }

        // 이전, 다음 버튼 패널
        JPanel btnPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("이전");
        JButton nextBtn = new JButton("다음");
        btnPanel1.add(backBtn);
        btnPanel1.add(nextBtn);

        // 로그아웃, 일기 쓰기, 정보 수정 버튼 추가
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton logoutButton = new JButton("로그아웃");
        JButton newButton = new JButton("일기 작성");
        JButton settingButton = new JButton("설정");
        btnPanel.add(logoutButton);
        btnPanel.add(newButton);
        btnPanel.add(settingButton);

        // 로그아웃 버튼 클릭
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen(); // 로그인 화면으로 이동
                dispose();
            }
        });

        // 일기 작성 버튼 클릭
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewDiaryScreen(user); // User 객체 전달
                dispose();
            }
        });

        // 설정 버튼 클릭
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingScreen(user); // User 객체 전달
                dispose();
            }
        });

        // 이전 버튼 클릭 (기능 구현 필요)
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이전 페이지로 이동하는 로직 (예: 첫 페이지로 돌아가기)
            }
        });

        // 다음 버튼 클릭 (기능 구현 필요)
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 다음 페이지로 이동하는 로직 (예: 더 많은 일기를 보여주기)
            }
        });

        // 패널을 JFrame에 추가
        add(imagePanel);
        add(userFeed);
        add(btnPanel1);
        add(btnPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // User 객체를 예시로 전달하여 DiaryListScreen 실행
        SwingUtilities.invokeLater(() -> new DiaryListScreen(new User("user123", "홍길동", "hong@domain.com")));
    }
}
