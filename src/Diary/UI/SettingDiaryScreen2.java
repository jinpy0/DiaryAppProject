package Diary.UI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class SettingDiaryScreen2 extends JFrame {
    private DatePicker datePicker;

    public SettingDiaryScreen2() {
        setTitle("일기 수정 페이지2");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // 제목, 날짜, 날짜선택 버튼 // 기존에 작성했던 내용 어떻게 받아올지
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

        // 전에 선택한 이미지 어떻게 받아올지
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        // 일기 내용 작성 textBox
        JPanel textPanel = new JPanel();
        JTextArea textArea = new JTextArea(20, 30);
        textPanel.add(textArea);

        // 뒤로가기, 수정하기 버튼
        JPanel btnPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");
        JButton changeBtn = new JButton("수정하기");
        btnPanel.add(backBtn);
        btnPanel.add(changeBtn);

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
                }else{
                    dateLabel.setText("날짜를 선택해주세요");
                }
                datePicker.setVisible(false);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new NewDiaryScreen();
                dispose();
            }
        });

        changeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 제목 작성 안했을 때 입력하라고 뜨게 하기
                String title = titleTextField.getText();
                if(title.isEmpty()) {
                    JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "제목을 입력해주세요");
                    return;
                }

                // 날짜 선택 안했을 때 입력하라고 뜨게 하기
                LocalDate selectedDate = datePicker.getDate();
                if(selectedDate == null) {
                    JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "날짜를 선택해주세요");
                    return;
                }

                // 내용 작성 안했을 때 입력하라고 뜨게 하기
                String text = textArea.getText();
                if(text.isEmpty()) {
                    JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "내용을 입력해주세요");
                    return;
                }

                // 모두 작성했을 때 작성하기
                JOptionPane.showMessageDialog(SettingDiaryScreen2.this, "수정완료");

                // 업로드 하는 기능 구현?

                // 창 닫기
                new DiaryListScreen();
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
        SwingUtilities.invokeLater(SettingDiaryScreen2::new);
    }
}
