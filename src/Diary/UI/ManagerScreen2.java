package Diary.UI;

import javax.swing.*;
import java.awt.*;

public class ManagerScreen2 extends JFrame {
    public ManagerScreen2() {
        setTitle("관리자 화면 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLocationRelativeTo(null);

        // 전체 레이아웃: BoxLayout (수직 배치)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 외부 여백 추가

        // 사용자 정보 패널
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel contentLabel = new JLabel("사용자 정보 ...");
        userPanel.add(imageLabel);
        userPanel.add(contentLabel);

        // 로그 패널
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS)); // 세로 정렬
        JLabel logLabel = new JLabel("활동로그 ...");
        JButton logButton = new JButton("더 보기");
        logLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logPanel.add(logLabel);
        logPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 간격 추가
        logPanel.add(logButton);

        // 리스트 패널
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // 세로 정렬
        JLabel listLabel = new JLabel("일기 목록 ...");
        JButton listBtn = new JButton("더 보기");
        listLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        listBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        listPanel.add(listLabel);
        listPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 간격 추가
        listPanel.add(listBtn);

        // 버튼 패널
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton backbtn = new JButton("뒤로가기");
        btnPanel.add(backbtn);

        // 패널을 메인 패널에 추가
        mainPanel.add(userPanel);
        mainPanel.add(logPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100))); // 간격 추가
        mainPanel.add(listPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100))); // 간격 추가
        mainPanel.add(btnPanel);

        add(mainPanel); // 메인 패널 추가
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManagerScreen2::new);
    }
}
