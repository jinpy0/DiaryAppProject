package FirstTest.UI;

import javax.swing.*;
import java.awt.*;

// 왼쪽으로 이동시켜야 함, 간격 추가

public class SignUpScreen extends JFrame {
    public SignUpScreen() {
        // 기본 설정
        setTitle("회원가입 화면");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // 상단 여백 추가
        add(Box.createVerticalStrut(30));

        // 아이디 입력 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel signupLabel = new JLabel("아이디:");
        JTextField usernameField = new JTextField(10); // 열 수를 10으로 설정하여 폭 조절
        idPanel.add(signupLabel);
        idPanel.add(usernameField);

        // 중복 확인 버튼
        JButton checkDuplicateButton = new JButton("중복 확인");
        idPanel.add(checkDuplicateButton);

        // 이메일 입력 패널
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("이메일:");
        JTextField emailField = new JTextField(10); // 열 수를 10으로 설정하여 폭 조절
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // 비밀번호 입력 패널
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(10); // 열 수를 10으로 설정하여 폭 조절
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // 비밀번호 확인 입력 패널
        JPanel passwordConfirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
        JPasswordField confirmPasswordField = new JPasswordField(10); // 열 수를 10으로 설정하여 폭 조절
        passwordConfirmPanel.add(confirmPasswordLabel);
        passwordConfirmPanel.add(confirmPasswordField);

        // 회원가입 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton signupButton = new JButton("회원가입");
        buttonPanel.add(signupButton);

        // 패널 추가
        add(idPanel);
        add(emailPanel);
        add(passwordPanel);
        add(passwordConfirmPanel);
        add(buttonPanel);

        // 하단 여백
        add(Box.createVerticalStrut(30));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpScreen::new);
    }
}
