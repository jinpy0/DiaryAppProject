package Diary.UI;

import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class DiaryListDetailScreen extends JFrame {
    public DiaryListDetailScreen(UserDTO user, Connection conn) {
        if (user == null) {
            JOptionPane.showMessageDialog(null, "작성자 정보가 없습니다. 다시 시도해주세요.");
            throw new IllegalArgumentException("UserDTO는 null일 수 없습니다.");
        }

        setTitle("일기 목록 상세 페이지");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 일기 DAO를 사용하여 일기 목록 가져오기
        DiaryDAO diaryDAO = new DiaryDAO(conn);
        List<DiaryDTO> diaries = diaryDAO.getDiariesByUserId(user.getUserId());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (diaries.isEmpty()) {
            mainPanel.add(new JLabel("작성된 일기가 없습니다."));
        } else {
            for (DiaryDTO diary : diaries) {
                JPanel diaryPanel = new JPanel();
                diaryPanel.setLayout(new BorderLayout());
                diaryPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                diaryPanel.setPreferredSize(new Dimension(450, 50));

                // 일기 제목 및 날짜
                JLabel diaryInfo = new JLabel(diary.getDiaryTitle() + " (" + diary.getCreateDate() + ")");
                diaryPanel.add(diaryInfo, BorderLayout.CENTER);

                // 버튼 패널
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                // 일기 보기 버튼
                JButton viewButton = new JButton("보기");
                viewButton.addActionListener(e -> {
                    // AdminDiaryScreen으로 이동
                    new AdminDiaryScreen(diary.getId(), conn); // diary와 conn 객체 전달
                    dispose(); // 현재 화면 닫기
                });


                // 수정 버튼
                JButton editButton = new JButton("수정");
                editButton.addActionListener(e -> {
                    new AdminSettingDiaryScreen(diary.getId(), conn);
                    dispose();
                });

                // 삭제 버튼
                JButton deleteButton = new JButton("삭제");
                deleteButton.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean isDeleted = diaryDAO.deleteDiaryById(diary.getId());
                        if (isDeleted) {
                            JOptionPane.showMessageDialog(this, "일기가 삭제되었습니다.");
                            new DiaryListDetailScreen(user, conn); // 화면 새로고침
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "삭제에 실패했습니다. 다시 시도해주세요.");
                        }
                    }
                });

                buttonPanel.add(viewButton);
                buttonPanel.add(editButton);
                buttonPanel.add(deleteButton);

                diaryPanel.add(buttonPanel, BorderLayout.EAST);

                mainPanel.add(diaryPanel);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 간격 추가
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);

        // 뒤로가기 버튼
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> {
            new ManagerScreen(user, conn); // 관리자 화면으로 돌아가기
            dispose();
        });
        btnPanel.add(backBtn);

        // 레이아웃 구성
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
