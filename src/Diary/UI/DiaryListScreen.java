package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dao.UserDAO;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class DiaryListScreen extends JFrame {
    private UserDTO user;
    private List<DiaryDTO> diaries;

    public DiaryListScreen(UserDTO user, Connection conn) {
        DiaryDAO diaryDAO = new DiaryDAO(conn);
        this.user = user;
        this.diaries = diaryDAO.getDiariesByUserId(user.getUserId());

        setTitle("일기 목록");
        setSize(350, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 사용자 정보 패널
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // 이미지 경로가 존재하면 사용자 이미지 로드
        String imagePath = user.getImage();
        ImageIcon imageIcon = null;

        if (imagePath != null && !imagePath.isEmpty()) {
            imageIcon = new ImageIcon(imagePath); // 로컬 파일 경로 또는 URL 경로
            imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // 이미지 크기 조정
        } else {
            imageIcon = new ImageIcon(); // 기본 이미지 (없으면)
        }

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        JLabel userIdLabel = new JLabel(user.getUserId());
        userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(userIdLabel);

        // 사용자 피드 패널
        JPanel userFeed = new JPanel(new GridLayout(3, 3, 5, 5));
        if (diaries.isEmpty()) {
            JLabel noDiaryLabel = new JLabel("작성한 일기가 없습니다.");
            noDiaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            userFeed.add(noDiaryLabel);
        } else {
            for (DiaryDTO diary : diaries) {
                JLabel feedLabel = new JLabel(diary.getDiaryTitle());
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
                new LogInScreen();
                dispose();
            }
        });

        // 일기 작성 버튼 클릭
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewDiaryScreen(user, DBConnection.getConnection()); // User 객체 전달
                dispose();
            }
        });

        // 설정 버튼 클릭
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingScreen(user, DBConnection.getConnection());
                dispose();
            }
        });

        // 이전 버튼 클릭 (기능 구현 필요)
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이전 페이지로 이동하는 로직 구현해야 함
            }
        });

        // 다음 버튼 클릭 (기능 구현 필요)
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 다음 페이지로 이동하는 로직 구현해야 함
            }
        });

        add(imagePanel);
        add(userFeed);
        add(btnPanel1);
        add(btnPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // SwingUtilities.invokeLater(() -> new DiaryListScreen(UserDAO.getUserById("123"), DBConnection.getConnection()));
    }
}
