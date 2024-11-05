package FirstTest.UI;

import javax.swing.*;
import java.awt.*;

public class DiaryListScreen extends JFrame {
    public DiaryListScreen() { // User 클래스의 인스턴스를 매개변수로 받아와서 리스트 만들기?
        setTitle("일기 목록");
        setSize(350, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));



        // JPanel imagePanel 이미지 어떻게 받아올 지 (일단 빈 박스로 구현)
        // 이미지, 회원 아이디 출력
        // 이미지 추가
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);
        // 아이디 추가
        JLabel userIdLabel = new JLabel("사용자 ID");
        userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(userIdLabel);


        // 사용자 피드
        JPanel userFeed = new JPanel(new GridLayout(3, 3, 5, 5)); // 5,5는 간격 조절
        for (int i = 0; i < 9; i++) {
            JLabel feedLabel = new JLabel();
            feedLabel.setPreferredSize(new Dimension(100, 100));
            feedLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            feedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            // DB 연동 시 수정해야 함
            feedLabel.setIcon(new ImageIcon("파일 경로.jpg"));
            userFeed.add(feedLabel);
        }

        add(imagePanel);
        add(userFeed);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiaryListScreen::new);
    }
}
