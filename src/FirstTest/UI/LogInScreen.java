package FirstTest.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // 로그인 버튼 클릭 이벤트
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("로그인 시도: " + username + " / 비밀번호: " + password);
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
