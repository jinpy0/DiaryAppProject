package Diary.UI;

import Diary.DataBase.Dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ManagerScreen2 extends JFrame {
    public ManagerScreen2(UserDTO user, Connection conn) {
        setTitle("사용자 상세 정보");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
        logPanel.setBorder(BorderFactory.createTitledBorder("활동 로그"));

        JLabel logLabel = new JLabel("최근 활동 로그");
        JButton logButton = new JButton("더 보기");
        logButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "활동 로그 상세 페이지로 이동 예정!");
        });

        logPanel.add(logLabel);
        logPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        logPanel.add(logButton);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // 세로 정렬
        listPanel.setBorder(BorderFactory.createTitledBorder("일기 목록"));

        JLabel listLabel = new JLabel("최근 작성된 일기");
        JButton listBtn = new JButton("더 보기");
        listBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "일기 목록 상세 페이지로 이동 예정!");
        });

        listPanel.add(listLabel);
        listPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 간격 추가
        listPanel.add(listBtn);

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
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 간격 추가
        mainPanel.add(logPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 간격 추가
        mainPanel.add(listPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 간격 추가
        mainPanel.add(btnPanel);

        // 메인 패널 추가
        add(mainPanel);
        setVisible(true);
    }
}
