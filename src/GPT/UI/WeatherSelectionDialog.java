package GPT.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherSelectionDialog extends JDialog {
    private JTextField dateField;      // 날짜 입력 필드
    private JComboBox<String> weatherComboBox; // 날씨 상태 선택 콤보박스
    private String selectedWeather;     // 선택된 날씨 상태
    private String selectedDate;        // 선택된 날짜

    public WeatherSelectionDialog(Frame owner) {
        super(owner, "Select Weather", true); // 모달 다이얼로그 생성
        setLayout(new GridLayout(3, 2, 10, 10)); // 레이아웃 설정

        // 날짜 입력 필드 추가
        add(new JLabel("Date:"));
        dateField = new JTextField();
        add(dateField);

        // 날씨 상태 선택 콤보박스 추가
        add(new JLabel("Weather:"));
        weatherComboBox = new JComboBox<>(new String[]{"Sunny", "Cloudy", "Rainy", "Snowy", "Windy"});
        add(weatherComboBox);

        // 확인 및 취소 버튼 추가
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new OkAction());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose()); // 취소 버튼 클릭 시 다이얼로그 닫기

        // 버튼 패널 생성 및 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        // 다이얼로그 크기 및 종료 설정
        setSize(300, 150);
        setLocationRelativeTo(owner); // 중앙에 위치
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // 확인 버튼 클릭 시 선택된 정보를 저장하는 액션 리스너
    private class OkAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedDate = dateField.getText(); // 선택된 날짜 가져오기
            selectedWeather = (String) weatherComboBox.getSelectedItem(); // 선택된 날씨 상태 가져오기

            // 선택된 날씨 정보가 비어있는지 확인
            if (selectedDate.isEmpty() || selectedWeather == null) {
                JOptionPane.showMessageDialog(WeatherSelectionDialog.this,
                        "Please select a date and weather.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose(); // 모든 정보가 올바르면 다이얼로그 닫기
            }
        }
    }

    // 선택된 날씨와 날짜 정보를 반환하는 메서드
    public String getSelectedWeather() {
        return selectedWeather;
    }

    public String getSelectedDate() {
        return selectedDate;
    }
}
