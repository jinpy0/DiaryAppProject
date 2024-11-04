package GPT.UI;

import javax.swing.*;
import java.awt.*;

// 로그인 페이지
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // GPT.UI 구성
        add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        add(loginButton);

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(e -> openSignupFrame());
        add(signupButton);

        setVisible(true);
    }

    private void login() {
        // 로그인 로직
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // 유저 인증 및 DiaryFrame으로 전환
    }

    private void openSignupFrame() {
        new SignupFrame();
    }
}

// 회원가입 페이지
class SignupFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignupFrame() {
        setTitle("Sign Up");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        add(passwordField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(e -> signup());
        add(signupButton);

        setVisible(true);
    }

    private void signup() {
        // 회원가입 로직
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // 데이터베이스에 사용자 정보 저장
        dispose(); // 회원가입 후 프레임 닫기
    }
}

// 일기 작성 및 확인 페이지
class DiaryFrame extends JFrame {
    public DiaryFrame() {
        setTitle("Diary");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 일기 작성 및 확인 GPT.UI 구성
        setVisible(true);
    }
}

// 관리자 페이지
class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 사용자 정보 및 일기 정보 표시 GPT.UI 구성
        setVisible(true);
    }
}

