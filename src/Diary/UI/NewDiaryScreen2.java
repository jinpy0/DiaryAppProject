package Diary.UI;

import Diary.DataBase.Dto.UserDTO;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;

public class NewDiaryScreen2 extends JFrame {

    private JLabel dateLabel;
    private JButton dateBtn;
    private DatePicker datePicker;
    private UserDTO user;
    private String imagePath; // 이미지를 저장할 변수

    // 생성자에서 User 객체와 이미지 경로를 받도록 수정
    public NewDiaryScreen2(UserDTO user, Connection conn, String imagePath) {
        this.user = user;
        this.imagePath = imagePath; // 전달받은 이미지 경로 저장

        setTitle("일기쓰기 화면 2");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // 제목, 날짜, 날짜선택 버튼
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("제목 : ");
        JTextField titleTextField = new JTextField(12);
        JButton dateBtn = new JButton("날짜");
        JLabel dateLabel = new JLabel();

        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);
        titlePanel.add(dateBtn);
        titlePanel.add(dateLabel);

        DatePickerSettings dateSetting = new DatePickerSettings();
        datePicker = new DatePicker(dateSetting);
        datePicker.setVisible(false);

        add(datePicker);

        // 선택한 이미지 100 * 100 사이즈로 띄우기
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 이미지 경로를 기반으로 이미지를 표시
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));

        imagePanel.add(imageLabel);

        // 일기 내용 작성 textBox
        JPanel textPanel = new JPanel();
        JTextArea textArea = new JTextArea(20, 30);
        textPanel.add(textArea);

        // 뒤로가기, 작성하기 버튼
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton createBtn = new JButton("작성하기");
        btnPanel.add(backBtn);
        btnPanel.add(createBtn);

        dateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                datePicker.setVisible(true);
            }
        });

        datePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                LocalDate selectedDate = dateChangeEvent.getNewDate();
                if(selectedDate != null) {
                    dateLabel.setText(selectedDate.toString());
                } else {
                    dateLabel.setText("날짜를 선택해주세요");
                }
                datePicker.setVisible(false);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new NewDiaryScreen(user, conn);
                dispose();
            }
        });

        createBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleTextField.getText();
                if(title.isEmpty()) {
                    JOptionPane.showMessageDialog(NewDiaryScreen2.this, "제목을 입력해주세요");
                    return;
                }

                LocalDate selectedDate = datePicker.getDate();
                if(selectedDate == null) {
                    JOptionPane.showMessageDialog(NewDiaryScreen2.this, "날짜를 선택해주세요");
                    return;
                }

                String text = textArea.getText();
                if(text.isEmpty()) {
                    JOptionPane.showMessageDialog(NewDiaryScreen2.this, "내용을 입력해주세요");
                    return;
                }

                JOptionPane.showMessageDialog(NewDiaryScreen2.this, "작성완료");

                new DiaryListScreen(user, conn);
                dispose();
            }
        });

        add(titlePanel);
        add(imagePanel);
        add(textPanel);
        add(btnPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // 임시로 User 객체와 Connection 객체를 전달
    }
}
