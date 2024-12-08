package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class DiaryScreen extends JFrame {
    private DiaryDTO diary; // 일기 객체

    // 생성자에서 Diary 객체만 받아오기
    public DiaryScreen(DiaryDTO diary, Connection conn) {
        // UserSession에서 사용자 정보 가져오기
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

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

        // 이미지 경로 가져오기
        String imagePath = diary.getDiaryImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                    .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("이미지 없음"); // 이미지가 없을 경우 기본 텍스트 표시
        }
        imagePanel.add(imageLabel);

        // 제목 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(diary.getDiaryTitle()); // Diary 객체에서 제목 가져오기
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // 제목 폰트 설정
        titlePanel.add(titleLabel);

        // 내용 패널 (스크롤 가능한 영역으로 내용 표시)
        JPanel contentPanel = new JPanel();
        JTextArea contentTextArea = new JTextArea(diary.getDiaryContent()); // Diary 객체에서 내용 가져오기
        contentTextArea.setEditable(false); // 내용 수정 불가
        contentTextArea.setLineWrap(true); // 줄바꿈 처리
        contentTextArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈
        contentTextArea.setCaretPosition(0); // 처음부터 표시
        JScrollPane scrollPane = new JScrollPane(contentTextArea);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

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
        backBtn.addActionListener(e -> {
            new DiaryListScreen(DBConnection.getConnection()); // DiaryListScreen으로 이동
            dispose();
        });

        // 수정 버튼 클릭 시
        setBtn.addActionListener(e -> {
            new SettingDiaryScreen(diary, DBConnection.getConnection()); // SettingDiaryScreen으로 이동
            dispose();
        });

        setVisible(true);
    }

//    public static void main(String[] args) {
//    }
}
