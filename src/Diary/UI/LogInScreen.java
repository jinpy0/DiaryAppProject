package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dao.UserDAO;
import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInScreen extends JFrame {
    public LogInScreen() {
        setTitle("로그인 화면");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel userLabel = new JLabel("  아이디  :");
        JTextField userField = new JTextField(12);
        userPanel.add(userLabel);
        userPanel.add(userField);

        // 비밀번호 입력, 로그인 버튼
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(12);
        JButton loginButton = new JButton("로그인");

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        passwordPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    UserDAO userDAO = new UserDAO(DBConnection.getConnection());
                    UserDTO user = userDAO.getUserByIdPassword(userId, password);
                    if (user != null) {
                        UserSession.getInstance().setCurrentUser(user);
                        String role = user.getRole();
                        System.out.println(role);
                        if (role.equals("ADMIN")) {
                            new ManagerScreen(user, DBConnection.getConnection());
                            dispose();
                        }
                        else {
                            new DiaryListScreen(DBConnection.getConnection());
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(LogInScreen.this, "아이디 또는 비밀번호가 일치하지 않습니다.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LogInScreen.this, "로그인 오류: " + ex.getMessage());
                }
            }
        });


        // 빈 공간 추가 (하단 여백)
        add(Box.createVerticalStrut(20));

        // 회원가입, 비밀번호 찾기 버튼
        JPanel buttonPanel = new JPanel();
        JButton signupButton = new JButton("회원가입");
        JButton findPasswordButton = new JButton("아이디 / 비밀번호 찾기");
        buttonPanel.add(signupButton);
        buttonPanel.add(findPasswordButton);

        // 회원가입 버튼
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpScreen(DBConnection.getConnection());
                dispose();
            }
        });

        // 아이디 / 비밀번호 찾기 페이지로 이동
        findPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FindIdPassWordScreen(DBConnection.getConnection());
                dispose();
            }
        });

        // 빈 공간 추가 (상단 여백)
        add(Box.createVerticalStrut(400));
        add(userPanel);
        add(passwordPanel);
        add(buttonPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new LogInScreen();
    }
}
