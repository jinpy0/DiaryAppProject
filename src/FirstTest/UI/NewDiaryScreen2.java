package FirstTest.UI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class NewDiaryScreen2 extends JFrame {

    private JLabel dateLabel;
    private JButton dateBtn;
    private DatePicker datePicker;

    public NewDiaryScreen2() {
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

        add(titlePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewDiaryScreen2::new);
    }
}
