package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dto.DiaryDTO;

import javax.swing.*;

import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.sql.Connection;

public class NewDiaryScreen2 extends JFrame {
    private String imagePath; // 이미지 경로
    private DatePicker datePicker;

    public NewDiaryScreen2(Connection conn, String imagePath) {
        // UserSession에서 사용자 정보 가져오기
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        this.imagePath = imagePath; // 전달받은 이미지 경로 저장

        setTitle("일기쓰기 화면 2");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // 제목 입력 패널
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("제목 : ");
        JTextField titleTextField = new JTextField(12);
        JButton dateBtn = new JButton("날짜");
        JLabel dateLabel = new JLabel();

        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);
        titlePanel.add(dateBtn);
        titlePanel.add(dateLabel);

        // 날짜 선택기
        DatePickerSettings dateSetting = new DatePickerSettings();
        datePicker = new DatePicker(dateSetting);
        datePicker.setVisible(false);

        add(datePicker);

        // 이미지 미리보기 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 이미지 경로를 기반으로 이미지 표시
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
        imagePanel.add(imageLabel);

        // 일기 내용 입력 패널
        JPanel textPanel = new JPanel();
        JTextArea textArea = new JTextArea(20, 30);
        textPanel.add(textArea);

        // 뒤로가기 및 작성하기 버튼 패널
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton createBtn = new JButton("작성하기");
        btnPanel.add(backBtn);
        btnPanel.add(createBtn);

        // 날짜 버튼 클릭 이벤트
        dateBtn.addActionListener(e -> datePicker.setVisible(true));

        // 날짜 선택 이벤트
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

        // 뒤로가기 버튼 클릭 이벤트
        backBtn.addActionListener(e -> {
            new NewDiaryScreen(conn);
            dispose();
        });

        // 작성하기 버튼 클릭 이벤트
        createBtn.addActionListener(e -> {
            String title = titleTextField.getText();
            String content = textArea.getText();

            // 제목, 날짜, 내용 유효성 검사
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(NewDiaryScreen2.this, "제목을 입력해주세요");
                return;
            }

            LocalDate selectedDate = datePicker.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(NewDiaryScreen2.this, "날짜를 선택해주세요");
                return;
            }

            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(NewDiaryScreen2.this, "내용을 입력해주세요");
                return;
            }

            // DiaryDTO 객체 생성 및 데이터 설정
            DiaryDTO diary = new DiaryDTO();
            diary.setUserId(user.getUserId());
            diary.setDiaryImage(imagePath);
            diary.setDiaryTitle(title);
            diary.setDiaryContent(content);
            diary.setCreateDate(selectedDate);

            // DiaryDAO를 사용하여 데이터베이스에 저장
            DiaryDAO diaryDAO = new DiaryDAO(conn);
            boolean isSaved = diaryDAO.addDiary(diary);

            // 작성 결과 처리
            if (isSaved) {
                JOptionPane.showMessageDialog(NewDiaryScreen2.this, "작성 완료!");
                new DiaryListScreen(conn); // 작성 후 다이어리 목록 화면으로 이동
                dispose();
            } else {
                JOptionPane.showMessageDialog(NewDiaryScreen2.this, "작성 실패. 다시 시도해주세요.");
            }
        });

        // 구성 요소 추가
        add(titlePanel);
        add(imagePanel);
        add(textPanel);
        add(btnPanel);

        setVisible(true);
    }
}