package Diary.UI;

import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dao.LogDAO;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.LogDTO;
import Diary.DataBase.Dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ManagerScreen2 extends JFrame {
    public ManagerScreen2(UserDTO user, Connection conn) {
        setTitle("사용자 상세 정보");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 사용자 정보 패널
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userPanel.setBorder(BorderFactory.createTitledBorder("사용자 정보"));

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (user.getImage() != null && !user.getImage().isEmpty()) {
            try {
                ImageIcon userImage = new ImageIcon(new ImageIcon(user.getImage()).getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(userImage);
            } catch (Exception e) {
                ImageIcon defaultImage = new ImageIcon(new ImageIcon("default_profile.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(defaultImage);
            }
        } else {
            ImageIcon defaultImage = new ImageIcon(new ImageIcon("default_profile.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(defaultImage);
        }

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.add(new JLabel("이름: " + user.getName()));
        userInfoPanel.add(new JLabel("ID: " + user.getUserId()));
        userInfoPanel.add(new JLabel("이메일: " + user.getEmail()));
        userInfoPanel.add(new JLabel("역할: " + user.getRole()));

        userPanel.add(imageLabel);
        userPanel.add(userInfoPanel);

        // 활동 로그 패널
//        JPanel logPanel = new JPanel();
//        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
//        logPanel.setBorder(BorderFactory.createTitledBorder("활동 로그"));

//        JLabel logLabel = new JLabel("최근 활동 로그:");
//        logPanel.add(logLabel);
//        logPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 간격 추가
//
//        // 로그 DAO를 통해 활동 로그 가져오기
//        LogDAO logDAO = new LogDAO(conn);
//        List<LogDTO> logs = logDAO.getLogsByUserId(user.getUserId());
//
//        if (logs.isEmpty()) {
//            logPanel.add(new JLabel("활동 로그가 없습니다."));
//        } else {
//            for (LogDTO log : logs) {
//                logPanel.add(new JLabel("- " + log.getAction() + " (" + log.getActionDate() + ")"));
//            }
//        }

//        JButton logButton = new JButton("더 보기");
//        logButton.addActionListener(e -> {
//            // 활동 로그 상세 페이지로 이동
//            new ActivityLogDetailScreen(user, conn); // 새로운 상세 로그 화면
//            dispose(); // 현재 화면 닫기
//        });
//        logPanel.add(logButton);

        // 일기 목록 패널
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createTitledBorder("일기 목록"));

        JLabel listLabel = new JLabel("최근 작성된 일기:");
        listPanel.add(listLabel);
        listPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 간격 추가

        // 일기 DAO를 통해 일기 목록 가져오기
        DiaryDAO diaryDAO = new DiaryDAO(conn);
        List<DiaryDTO> diaries = diaryDAO.getDiariesByUserId(user.getUserId());

        if (diaries.isEmpty()) {
            listPanel.add(new JLabel("작성된 일기가 없습니다."));
        } else {
            for (DiaryDTO diary : diaries) {
                listPanel.add(new JLabel("- " + diary.getDiaryTitle() + " (" + diary.getCreateDate() + ")"));
            }
        }

        JButton listButton = new JButton("더 보기");
        listButton.addActionListener(e -> {
            // 일기 목록 상세 페이지로 이동
            new DiaryListDetailScreen(user, conn); // 새로운 상세 일기 목록 화면
            dispose(); // 현재 화면 닫기
        });
        listPanel.add(listButton);

        // 뒤로가기 버튼
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> {
            new ManagerScreen(user, conn); // 이전 화면으로 이동
            dispose(); // 현재 창 닫기
        });
        btnPanel.add(backBtn);

        // 패널을 메인 패널에 추가
        mainPanel.add(userPanel);
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 간격 추가
//        mainPanel.add(logPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 간격 추가
        mainPanel.add(listPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 간격 추가
        mainPanel.add(btnPanel);

        // 메인 패널 추가
        add(mainPanel);
        setVisible(true);
    }
}
