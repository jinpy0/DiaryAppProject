package FirstTest.UI;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;

public class newDiaryScreen extends JFrame {
    String imagePath = "파일경로.jpg";

    public newDiaryScreen() {
        setTitle("일기 작성 페이지");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add(Box.createVerticalStrut(30));

        // 전 페이지에서 선택한 이미지 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setIcon(new ImageIcon(imagePath));
        imagePanel.add(imageLabel);









        add(imagePanel);
        setVisible(true);




    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(newDiaryScreen::new);
    }
}
