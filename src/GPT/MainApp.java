package GPT;

import GPT.DataBase.DatabaseConfig;
import GPT.DataBase.DatabaseInitializer;
import GPT.UI.MainFrame;
import GPT.Utils.DateUtils;
import GPT.Utils.ValidationUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;

public class MainApp {
    public static void main(String[] args) {
        // 데이터베이스 구성 정보 설정
        DatabaseConfig dbConfig = new DatabaseConfig("jdbc:mysql://localhost:3306/diarydb", "username", "password");

        // 데이터베이스 초기화
        DatabaseInitializer dbInitializer = new DatabaseInitializer(dbConfig);
        dbInitializer.initialize();

        // Swing GPT.UI 요소 생성
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });

        // 예시: 다이어리 항목 추가 버튼
        JButton addDiaryButton = new JButton("Add Diary Entry");
        addDiaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dateInput = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
                String contentInput = JOptionPane.showInputDialog("Enter diary content:");

                // 날짜 유효성 검사
                if (ValidationUtils.isValidDate(dateInput) && !ValidationUtils.isEmpty(contentInput)) {
                    try {
                        Date date = DateUtils.parseDate(dateInput);
                        String formattedDate = DateUtils.formatDate(date);
                        // 여기에 다이어리 항목을 데이터베이스에 추가하는 로직을 추가합니다.
                        System.out.println("Diary entry added for date: " + formattedDate + " with content: " + contentInput);
                        // 예시로 콘솔에 출력
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Error parsing date.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please check your date and content.");
                }
            }
        });

        // 메인 프레임에 버튼 추가
        JFrame frame = new JFrame("Diary Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.getContentPane().setLayout(new java.awt.FlowLayout());
        frame.getContentPane().add(addDiaryButton);
        frame.setVisible(true);
    }
}

