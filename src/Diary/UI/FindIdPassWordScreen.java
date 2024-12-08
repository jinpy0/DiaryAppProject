package Diary.UI;

import Diary.DataBase.Dao.UserDAO;

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

        UserDAO userDAO = new UserDAO(conn);

        // 아이디 찾기 패널
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

        JPanel inputIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputId = new JLabel("아이디 : ");
        JTextField inputIdField = new JTextField(20);
        inputIdPanel.add(inputId);
        inputIdPanel.add(inputIdField);

        JPanel inputEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputEmailLabel = new JLabel("이메일 : ");
        JTextField inputEmailField1 = new JTextField(20);
        inputEmailPanel.add(inputEmailLabel);
        inputEmailPanel.add(inputEmailField1);

        JPanel findPasswordButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton passwordBtn = new JButton("비밀번호 찾기");
        findPasswordButtonPanel.add(passwordBtn);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("뒤로가기");
        backButtonPanel.add(backButton);

        add(findIdPanel);
        add(userNamePanel);
        add(findIdButtonPanel);
        add(inputIdPanel);
        add(inputEmailPanel);
        add(findPasswordButtonPanel);
        add(backButtonPanel);

        // 아이디 찾기 버튼 이벤트
        idBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = inputEmailField.getText().trim();
                String name = inputNameField.getText().trim();
                if (!email.isEmpty() && !name.isEmpty()) {
                    String userId = userDAO.getUserIdByEmailAndName(email, name);
                    if (userId != null) {
                        JOptionPane.showMessageDialog(
                                FindIdPassWordScreen.this,
                                "아이디: " + userId,
                                "아이디 찾기 결과",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                FindIdPassWordScreen.this,
                                "일치하는 아이디를 찾을 수 없습니다.",
                                "아이디 찾기 실패",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            FindIdPassWordScreen.this,
                            "이메일과 이름을 모두 입력해주세요.",
                            "입력 오류",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        // 비밀번호 찾기 버튼 이벤트
        passwordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = inputIdField.getText().trim();
                String email = inputEmailField1.getText().trim();
                if (!userId.isEmpty() && !email.isEmpty()) {
                    String password = userDAO.getPasswordByUserIdAndEmail(userId, email);
                    if (password != null) {
                        JOptionPane.showMessageDialog(
                                FindIdPassWordScreen.this,
                                "비밀번호: " + password,
                                "비밀번호 찾기 결과",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                FindIdPassWordScreen.this,
                                "일치하는 비밀번호를 찾을 수 없습니다.",
                                "비밀번호 찾기 실패",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            FindIdPassWordScreen.this,
                            "아이디와 이메일을 모두 입력해주세요.",
                            "입력 오류",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        // 뒤로가기 버튼 이벤트
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen(); // 이전 화면으로 이동
                dispose();
            }
        });

        setVisible(true);
    }
}
