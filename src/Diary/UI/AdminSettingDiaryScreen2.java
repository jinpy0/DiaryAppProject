package Diary.UI;

import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dto.DiaryDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;

public class AdminSettingDiaryScreen2 extends JFrame {
    private DatePicker datePicker;
    private DiaryDTO selectedDiary;
    private String imagePath;

    public AdminSettingDiaryScreen2(DiaryDTO diary, Connection conn) {
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        this.selectedDiary = diary;
        this.imagePath = diary.getDiaryImage(); // 기존 이미지 경로

        setTitle("관리자 일기 수정 페이지 2");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 제목 수정 패널
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("제목: ");
        JTextField titleTextField = new JTextField(diary.getDiaryTitle(), 20);
        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);

        // 날짜 선택 패널
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton dateBtn = new JButton("날짜 선택");
        JLabel dateLabel = new JLabel(diary.getCreateDate().toString());
        datePanel.add(dateBtn);
        datePanel.add(dateLabel);

        // 날짜 선택기
        DatePickerSettings dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        datePicker.setVisible(false);

        dateBtn.addActionListener(e -> datePicker.setVisible(true));
        datePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                LocalDate selectedDate = event.getNewDate();
                if (selectedDate != null) {
                    dateLabel.setText(selectedDate.toString());
                } else {
                    dateLabel.setText("날짜를 선택해주세요");
                }
                datePicker.setVisible(false);
            }
        });

        // 이미지 수정 패널
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("이미지 없음");
        }

        JButton imgBtn = new JButton("사진 선택");
        imgBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.length() > 5 * 1024 * 1024) {
                    JOptionPane.showMessageDialog(this, "파일 크기는 5MB 이하로 제한됩니다.");
                    return;
                }

                imagePath = selectedFile.getAbsolutePath();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
                imageLabel.setText(null);
            }
        });

        imagePanel.add(imageLabel);
        imagePanel.add(imgBtn);

        // 내용 수정 패널
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea textArea = new JTextArea(diary.getDiaryContent(), 10, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPanel.add(scrollPane);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("뒤로가기");
        JButton saveBtn = new JButton("수정하기");

        backBtn.addActionListener(e -> {
            new AdminSettingDiaryScreen(diary.getId(), conn);
            dispose();
        });

        saveBtn.addActionListener(e -> {
            String title = titleTextField.getText();
            LocalDate selectedDate = datePicker.getDate();
            String content = textArea.getText();

            // 유효성 검사
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "제목을 입력해주세요.");
                return;
            }
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "날짜를 선택해주세요.");
                return;
            }
            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "내용을 입력해주세요.");
                return;
            }

            // DiaryDTO 업데이트
            selectedDiary.setDiaryTitle(title);
            selectedDiary.setDiaryContent(content);
            selectedDiary.setUpdateDate(selectedDate);
            selectedDiary.setDiaryImage(imagePath);

            DiaryDAO diaryDAO = new DiaryDAO(conn);
            boolean isUpdated = diaryDAO.updateDiary(selectedDiary);

            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "수정 완료!");
                // 수정 완료 후 화면 이동
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    new DiaryListDetailScreen(user, conn); // 관리자 화면으로 이동
                } else {
                    new DiaryListScreen(conn); // 일반 사용자 화면으로 이동
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "수정 실패. 다시 시도해주세요.");
            }
        });

        buttonPanel.add(backBtn);
        buttonPanel.add(saveBtn);

        // 화면 구성 요소 추가
        add(titlePanel);
        add(datePanel);
        add(imagePanel);
        add(contentPanel);
        add(buttonPanel);

        add(datePicker);
        setVisible(true);
    }
}
