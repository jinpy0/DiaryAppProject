package Diary.UI;

import Diary.DataBase.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class FindIdPassWordScreen extends JFrame {
    public FindIdPassWordScreen(Connection conn) {
        setTitle("아이디 / 비밀번호 찾기 화면");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel findIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputEmail = new JLabel("이메일 : ");
        JTextField inputEmailField = new JTextField(20);
        findIdPanel.add(inputEmail);
        findIdPanel.add(inputEmailField);

        JPanel userNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputName = new JLabel("이름 : ");
        JTextField inputNameField = new JTextField(20);
        userNamePanel.add(inputName);
        userNamePanel.add(inputNameField);

        JPanel findIdButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton idBtn = new JButton("아이디 찾기");
        findIdButtonPanel.add(idBtn);

//        비밀번호 찾기 (아이디, 이메일 입력)
//        아이디 입력
        JPanel inputIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 아이디 입력 패널
        JLabel inputId = new JLabel("아이디 : ");
        JTextField inputIdField = new JTextField(20);
        inputIdPanel.add(inputId);
        inputIdPanel.add(inputIdField);

        JPanel inputEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 이메일 입력 패널
        JLabel inputEmailLabel = new JLabel("이메일 : ");
        JTextField inputEmailField1 = new JTextField(20);
        inputEmailPanel.add(inputEmailLabel);
        inputEmailPanel.add(inputEmailField1);

        JPanel findPasswordButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 버튼 패널
        JButton passwordBtn = new JButton("비밀번호 찾기");

//        뒤로가기 버튼
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("뒤로가기");
        backButtonPanel.add(backButton);
        findPasswordButtonPanel.add(passwordBtn);

        add(findIdPanel);
        add(userNamePanel);
        add(findIdButtonPanel);
        add(inputIdPanel);
        add(inputEmailPanel);
        add(findPasswordButtonPanel);
        add(backButtonPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen();
                dispose();
            }
        });

        setVisible(true);
    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(FindIdPassWordScreen::new);
//    }
}
