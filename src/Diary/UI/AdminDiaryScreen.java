package Diary.UI;

import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dto.DiaryDTO;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminDiaryScreen extends JFrame {
    private DiaryDTO diary; // 선택된 일기 데이터
    private Connection conn; // 데이터베이스 연결

    public AdminDiaryScreen(int diaryId, Connection conn) {
        try {
            DiaryDAO diaryDAO = new DiaryDAO(conn);
            DiaryDTO diary = diaryDAO.getDiaryWithUserById(diaryId);

            if (diary == null) {
                JOptionPane.showMessageDialog(this, "다이어리를 찾을 수 없습니다.");
                dispose();
                return;
            }

            if (diary.getUser() == null) {
                JOptionPane.showMessageDialog(this, "작성자 정보가 누락되었습니다.");
                dispose();
                return;
            }

            this.diary = diary;

            setTitle("일기 보기 - 관리자 모드");
            setSize(400, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // 이미지 패널
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
            JLabel titleLabel = new JLabel(diary.getDiaryTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titlePanel.add(titleLabel);

            // 내용 패널
            JPanel contentPanel = new JPanel();
            JTextArea contentTextArea = new JTextArea(diary.getDiaryContent());
            contentTextArea.setEditable(false);
            contentTextArea.setLineWrap(true);
            contentTextArea.setWrapStyleWord(true);
            contentTextArea.setCaretPosition(0);
            JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(contentScrollPane, BorderLayout.CENTER);

            // 제목과 내용 패널 합치기
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(titlePanel);
            mainPanel.add(contentPanel);

            // 버튼 패널
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton backButton = new JButton("뒤로가기");
            JButton editButton = new JButton("수정");
            JButton deleteButton = new JButton("삭제");
            buttonPanel.add(backButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            // 뒤로가기 버튼 동작
            backButton.addActionListener(e -> {
                if (diary.getUser() == null) {
                    JOptionPane.showMessageDialog(this, "작성자 정보를 찾을 수 없습니다.");
                    return;
                }
                // 작성자 정보를 DiaryListDetailScreen으로 전달
                new DiaryListDetailScreen(diary.getUser(), conn);
                dispose();
            });

            // 수정 버튼 동작
            editButton.addActionListener(e -> {
                new AdminSettingDiaryScreen(diary.getId(), conn); // 수정 화면으로 이동
                dispose();
            });

            // 삭제 버튼 동작
            deleteButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "정말로 이 일기를 삭제하시겠습니까?",
                        "삭제 확인",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // 기존의 diaryDAO를 재사용
                    boolean isDeleted = diaryDAO.deleteDiaryById(diary.getId());
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(this, "일기가 삭제되었습니다.");
                        new DiaryListDetailScreen(diary.getUser(), conn); // 삭제 후 목록 화면으로 이동
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "삭제에 실패했습니다. 다시 시도해주세요.");
                    }
                }
            });

            // 구성 요소 추가
            add(imagePanel, BorderLayout.NORTH);
            add(mainPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "다이어리 정보를 가져오는 중 오류가 발생했습니다.");
            dispose();
        }
    }
}
