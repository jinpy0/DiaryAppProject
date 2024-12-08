package Diary.UI;

import Diary.DataBase.DBConnection;
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

public class SettingDiaryScreen2 extends JFrame {
    private DatePicker datePicker;
    private DiaryDTO selectedDiary;
    private String imagePath;

    public SettingDiaryScreen2(DiaryDTO diary, Connection conn) {
        // UserSession에서 사용자 정보 가져오기
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        this.selectedDiary = diary;
        this.imagePath = diary.getDiaryImage(); // 기존 이미지 경로

        setTitle("일기 수정 페이지 2");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // 제목 수정 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("제목 : ");
        JTextField titleTextField = new JTextField(diary.getDiaryTitle(), 12); // 기존 제목 표시
        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);

        // 날짜 선택 패널
        JPanel datePanel = new JPanel();
        JButton dateBtn = new JButton("날짜");
        JLabel dateLabel = new JLabel(diary.getCreateDate().toString()); // 기존 날짜 표시
        datePanel.add(dateBtn);
        datePanel.add(dateLabel);

        // 날짜 선택 기능
        DatePickerSettings dateSetting = new DatePickerSettings();
        datePicker = new DatePicker(dateSetting);
        datePicker.setVisible(false); // 처음에는 숨김
        add(datePicker);

        // 이미지 수정 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 기존 이미지 표시
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                    .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("이미지 없음");
        }

        JButton imgBtn = new JButton("사진 선택");
        imgBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(SettingDiaryScreen2.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath(); // 이미지 경로 저장

                // 이미지 갱신
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
                imageLabel.setText(null); // 텍스트 제거
            }
        });

        imagePanel.add(imageLabel);
        imagePanel.add(imgBtn);

        // 일기 내용 수정 패널
        JPanel textPanel = new JPanel();
        JTextArea textArea = new JTextArea(diary.getDiaryContent(), 20, 30); // 기존 내용 표시
        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);

        // 버튼 패널: 뒤로가기, 수정하기
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton saveBtn = new JButton("수정하기");
        btnPanel.add(backBtn);
        btnPanel.add(saveBtn);

        // 날짜 선택 버튼
        dateBtn.addActionListener(e -> datePicker.setVisible(true));

        // 날짜 선택 리스너
        datePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                LocalDate selectedDate = dateChangeEvent.getNewDate();
                if (selectedDate != null) {
                    dateLabel.setText(selectedDate.toString());
                } else {
                    dateLabel.setText("날짜를 선택해주세요");
                }
                datePicker.setVisible(false);
            }
        });

        // 뒤로가기 버튼 이벤트
        backBtn.addActionListener(e -> {
            new DiaryListScreen(conn);
            dispose();
        });

        // 수정하기 버튼 이벤트
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

            // DiaryDTO 객체 업데이트
            selectedDiary.setDiaryTitle(title);
            selectedDiary.setDiaryContent(content);
            selectedDiary.setUpdateDate(selectedDate);
            selectedDiary.setDiaryImage(imagePath);

            // DB 업데이트
            DiaryDAO diaryDAO = new DiaryDAO(conn);
            boolean isUpdated = diaryDAO.updateDiary(selectedDiary);

            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "수정이 완료되었습니다!");
                new DiaryListScreen(conn);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "수정 실패. 다시 시도해주세요.");
            }
        });

        // 컴포넌트 추가
        add(titlePanel);
        add(datePanel);
        add(imagePanel);
        add(textPanel);
        add(btnPanel);

        setVisible(true);
    }
}
