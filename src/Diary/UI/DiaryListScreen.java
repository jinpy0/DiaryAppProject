package Diary.UI;

import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class DiaryListScreen extends JFrame {
    private List<DiaryDTO> diaries;

    public DiaryListScreen(Connection conn) {
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        DiaryDAO diaryDAO = new DiaryDAO(conn);
        this.diaries = diaryDAO.getDiariesByUserId(user.getUserId());

        setTitle("일기 목록");
        setSize(350, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String imagePath = user.getImage();
        ImageIcon imageIcon = null;

        if (imagePath != null && !imagePath.isEmpty()) {
            imageIcon = new ImageIcon(imagePath);
            imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // 이미지 크기 조정
        } else {
            imageIcon = new ImageIcon();
        }

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        JLabel userIdLabel = new JLabel(user.getUserId());
        userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(userIdLabel);

        int columns = 3;
        int rows = (int) Math.ceil((double) diaries.size() / columns);
        JPanel userFeed = new JPanel(new GridLayout(rows, columns, 5, 5));

        if (diaries.isEmpty()) {
            JLabel noDiaryLabel = new JLabel("작성한 일기가 없습니다.");
            noDiaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            userFeed.add(noDiaryLabel);
        } else {
            for (DiaryDTO diary : diaries) {
                imagePath = diary.getDiaryImage();
                imageIcon = null;
                if (imagePath != null && !imagePath.isEmpty()) {
                    imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                            .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                } else {
                    imageIcon = new ImageIcon(new ImageIcon("default_diary_image.png").getImage()
                            .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                }
                JLabel feedLabel = new JLabel(imageIcon);
                feedLabel.setPreferredSize(new Dimension(100, 100));
                feedLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                feedLabel.setHorizontalAlignment(SwingConstants.CENTER);

                feedLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        new DiaryScreen(diary, conn);
                        dispose();
                    }
                });
                userFeed.add(feedLabel);
            }

            int remainingCells = (rows * columns) - diaries.size();
            for (int i = 0; i < remainingCells; i++) {
                userFeed.add(new JLabel());
            }
        }

        // 페이지 네비게이션 버튼 패널
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("이전");
        JButton nextButton = new JButton("다음");
        navPanel.add(prevButton);
        navPanel.add(nextButton);

        // 이전 버튼 클릭 이벤트
        prevButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "첫 번째 페이지 입니다.");
        });

        // 다음 버튼 클릭 이벤트
        nextButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "마지막 페이지 입니다.");
        });

        // 로그아웃, 일기 쓰기, 정보 수정 버튼 추가
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton logoutButton = new JButton("로그아웃");
        JButton newButton = new JButton("일기 작성");
        JButton settingButton = new JButton("설정");
        btnPanel.add(logoutButton);
        btnPanel.add(newButton);
        btnPanel.add(settingButton);

        // 로그아웃 버튼 클릭
        logoutButton.addActionListener(e -> {
            UserSession.getInstance().clearSession(); // 세션 초기화
            new LogInScreen();
            dispose();
        });

        // 일기 작성 버튼 클릭
        newButton.addActionListener(e -> {
            new NewDiaryScreen(conn);
            dispose();
        });

        // 설정 버튼 클릭
        settingButton.addActionListener(e -> {
            new SettingScreen(conn);
            dispose();
        });

        add(imagePanel);
        add(userFeed);
        add(navPanel);
        add(btnPanel);

        setVisible(true);
    }

}
