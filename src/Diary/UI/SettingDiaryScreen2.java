package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;

public class SettingDiaryScreen2 extends JFrame {
    private DatePicker datePicker;
    private DiaryDTO selectedDiary; // 수정할 일기 객체
    private String imagePath; // 수정된 이미지 경로

    public SettingDiaryScreen2(UserDTO user, DiaryDTO diary) {
        this.selectedDiary = diary; // 수정할 일기 객체
        this.imagePath = diary.getDiaryImage(); // 기존 이미지 경로

        setTitle("일기 수정 페이지2");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // 제목 수정 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("제목 : ");
        JTextField titleTextField = new JTextField(diary.getDiaryTitle(), 12); // 기존 제목을 텍스트 필드로 표시
        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);

        // 날짜 선택 버튼과 레이블
        JPanel datePanel = new JPanel();
        JButton dateBtn = new JButton("날짜");
        JLabel dateLabel = new JLabel(diary.getCreateDate().toString()); // 기존 날짜 표시
        datePanel.add(dateBtn);
        datePanel.add(dateLabel);

        // 날짜 선택 기능
        DatePickerSettings dateSetting = new DatePickerSettings();
        datePicker = new DatePicker(dateSetting);
        datePicker.setVisible(false); // 날짜 선택 창 숨기기
        add(datePicker);

        // 이미지 수정 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setIcon(new ImageIcon(imagePath)); // 기존 이미지 표시
        imagePanel.add(imageLabel);

        // 이미지 선택 버튼
        JButton imgBtn = new JButton("사진선택");
        imgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(SettingDiaryScreen2.this);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath(); // 이미지 경로 저장

                    // 이미지 표시
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                }
            }
        });
        imagePanel.add(imgBtn);

        // 일기 내용 수정 텍스트 에어리어
        JPanel textPanel = new JPanel();
        JTextArea textArea = new JTextArea(diary.getDiaryContent(), 20, 30); // 기존 내용 표시
        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);

        // 버튼 패널: 뒤로가기, 수정하기
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton changeBtn = new JButton("수정하기");
        btnPanel.add(backBtn);
        btnPanel.add(changeBtn);

        dateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                datePicker.setVisible(true); // 날짜 선택창 보이기
            }
        });

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
                datePicker.setVisible(false); // 날짜 선택 후 창 숨기기
            }
        });

        // 뒤로가기 버튼 이벤트
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DiaryListScreen(user, DBConnection.getConnection()); // User 객체 전달
                dispose();
            }
        });

        // 수정하기 버튼 이벤트
        changeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 제목 입력 체크
                String title = titleTextField.getText();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "제목을 입력해주세요");
                    return;
                }

                // 날짜 선택 체크
                LocalDate selectedDate = datePicker.getDate();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "날짜를 선택해주세요");
                    return;
                }

                // 내용 입력 체크
                String text = textArea.getText();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "내용을 입력해주세요");
                    return;
                }

                // 수정된 일기 저장
                selectedDiary.setDiaryTitle(title); // 제목 수정
                selectedDiary.setDiaryContent(text); // 내용 수정
                selectedDiary.setUpdateDate(selectedDate); // 날짜 수정
                selectedDiary.setDiaryImage(imagePath); // 이미지 경로 수정

                // 수정 완료 메시지
                JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "수정완료");

                // 수정된 데이터를 DB에 저장하는 기능 구현 필요 (예: DB 업데이트)

                // 창 닫기
                new DiaryListScreen(user, DBConnection.getConnection()); // 일기 목록 화면으로 이동
                dispose();
            }
        });

        // 화면에 컴포넌트들 추가
        add(titlePanel);
        add(datePanel);
        add(imagePanel);
        add(textPanel);
        add(btnPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new SettingDiaryScreen2(new User("user123", "홍길동", "hong@domain.com"), new Diary("제목", "내용", "이미지 경로", LocalDate.now())));
    }
}
