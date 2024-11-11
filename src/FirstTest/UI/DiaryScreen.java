package FirstTest.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 저장된 일기를 볼 수 있는 페이지
public class DiaryScreen extends JFrame {
    public DiaryScreen() {
        setTitle("일기 보기");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(Box.createVerticalStrut(30));

        // 선택된 이미지 파일 , ImageLabel 에 이미지 받아오기 ( 일단 빈 박스 )
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        // 제목 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("a");
        // 텍스트 어떻게 받아올 지
        titlePanel.add(titleLabel);

        // 내용 패널
        JPanel contentPanel = new JPanel();
        JLabel contentLabel = new JLabel("b");
        // 내용 어떻게 받아올지
        contentPanel.add(contentLabel);

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

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DiaryListScreen();
                dispose();
            }
        });

        setBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingDiaryScreen();
                dispose();
            }
        });


        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiaryScreen::new);
    }

}
