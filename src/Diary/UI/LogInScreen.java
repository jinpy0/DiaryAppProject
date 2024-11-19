package Diary.UI;

import Diary.DataBase.DataBase;
import Diary.DataBase.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogInScreen extends JFrame {
    public LogInScreen() {
        // 기본 설정
        setTitle("로그인 화면");
        setSize(350, 600); // 사이즈를 350 x 600으로 변경
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // 수직 박스 레이아웃 설정

        // 빈 공간 추가 (상단 여백)
        add(Box.createVerticalStrut(400)); // 높이 400px의 빈 공간 추가

        // 아이디 입력 필드
        JLabel userLabel = new JLabel("  아이디  :");
        JTextField userField = new JTextField(12); // 너비를 12로 설정
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout 사용
        userPanel.add(userLabel);
        userPanel.add(userField);
        add(userPanel); // 아이디 패널 추가

        // 비밀번호 입력 필드와 로그인 버튼을 같은 패널에 배치
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(12); // 너비를 12로 설정
        JButton loginButton = new JButton("로그인");

        // 비밀번호 패널 생성
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout 사용
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createRigidArea(new Dimension(10, 0))); // 비밀번호 필드와 버튼 간격 추가
        passwordPanel.add(loginButton); // 로그인 버튼을 비밀번호 패널에 추가
        add(passwordPanel); // 비밀번호 패널 추가

        // 로그인 버튼
        // 로그인 성공 시 User 객체 생성 후 DiaryListScreen으로 전달
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());

                // 로그인 정보 확인
                try {
                    boolean loginSuccess = DataBase.verifyUserCredentials(username, password);
                    if (loginSuccess) {
                        // User 객체 생성 (실제로는 DB에서 해당 사용자 정보를 받아와야 합니다)
                        User user = new User(username, "사용자 이름", "사용자 이메일", "사용자 이미지 경로");

                        // DiaryListScreen으로 사용자 정보 전달
                        new DiaryListScreen(user);
                        dispose(); // 현재 로그인 화면 닫기
                    } else {
                        JOptionPane.showMessageDialog(LogInScreen.this, "아이디 또는 비밀번호가 일치하지 않습니다.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(LogInScreen.this, "데이터베이스 오류: " + ex.getMessage());
                }
            }
        });


        // 빈 공간 추가 (하단 여백)
        add(Box.createVerticalStrut(20)); // 높이 20px의 빈 공간 추가

        // 회원가입 및 비밀번호 찾기 버튼
        JPanel buttonPanel = new JPanel();
        JButton signupButton = new JButton("회원가입");
        JButton findPasswordButton = new JButton("아이디 / 비밀번호 찾기");
        buttonPanel.add(signupButton);
        buttonPanel.add(findPasswordButton);
        add(buttonPanel); // 버튼 패널 추가

        // 회원가입 페이지로 이동
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpScreen();
                dispose(); // 현재 로그인 화면 닫기
            }
        });

        // 아이디 / 비밀번호 찾기 페이지로 이동
        findPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FindIdPassWordScreen();
                dispose();
            }
        });


        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInScreen::new);
    }
}
