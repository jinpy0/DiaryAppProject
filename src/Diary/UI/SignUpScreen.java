package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dao.UserDAO;
import Diary.DataBase.Dto.UserDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

public class SignUpScreen extends JFrame {
    private String imagePath = null;

    // 기본 이미지 경로
    private final String defaultImagePath = "C:/JavaSource/DiaryProject/src/Diary/default_image.png";

    private boolean isUserIdAvailable = false;

    public SignUpScreen(Connection conn) {
        setTitle("회원가입 화면");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // 상단 여백 추가
        add(Box.createVerticalStrut(30));

        // 이미지 선택 패널
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel("이미지 선택");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        // 이미지 선택 버튼
        JButton selectImageBtn = new JButton("사진 선택");
        selectImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(SignUpScreen.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();

                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(null);
                }
            }
        });
        imagePanel.add(selectImageBtn);

        // 아이디 입력 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel signupLabel = new JLabel("아이디:");
        JTextField userIdField = new JTextField(15);
        idPanel.add(signupLabel);
        idPanel.add(userIdField);

        // 중복 확인 버튼
        JButton checkDuplicateButton = new JButton("중복 확인");
        idPanel.add(checkDuplicateButton);

        // 이름 입력 패널
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("이름 : ");
        JTextField nameField = new JTextField(15);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // 이메일 입력 패널
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("이메일 : ");
        JTextField emailField = new JTextField(15);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // 비밀번호 입력 패널
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // 비밀번호 확인 입력 패널
        JPanel passwordConfirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
        JPasswordField confirmPasswordField = new JPasswordField(15);
        passwordConfirmPanel.add(confirmPasswordLabel);
        passwordConfirmPanel.add(confirmPasswordField);

        // 뒤로가기, 회원가입 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton signupButton = new JButton("회원가입");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(backButton);
        buttonPanel.add(signupButton);

        add(imagePanel);
        add(idPanel);
        add(namePanel);
        add(emailPanel);
        add(passwordPanel);
        add(passwordConfirmPanel);
        add(buttonPanel);

        add(Box.createVerticalStrut(30));

        UserDAO userDAO = new UserDAO(conn);

        // 중복확인 버튼
        checkDuplicateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "아이디를 입력해주세요.");
                    isUserIdAvailable = false;
                    return;
                }
                if (userDAO.isUserIdOverlap(userId)) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "이미 존재하는 아이디입니다.");
                    isUserIdAvailable = false;
                } else {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "사용 가능한 아이디입니다.");
                    isUserIdAvailable = true;
                }
            }
        });

        // 뒤로가기 버튼
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen();
                dispose();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
                String role = "USER";

                if (userId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "모든 필드를 입력해주세요.");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "비밀번호가 일치하지 않습니다.");
                    return;
                }

                // 사진이 선택되지 않았으면 기본 이미지 경로 사용
                String finalImagePath = (imagePath != null) ? imagePath : defaultImagePath;

                // Connection 객체 생성
                Connection conn = DBConnection.getConnection();

                // UserDAO 객체를 생성하고 addUser 메서드를 호출
                UserDAO userDAO = new UserDAO(conn);
                UserDTO user = new UserDTO(userId, name, email, password, finalImagePath, role);
                boolean success = userDAO.addUser(user);

                if (success) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "회원가입이 완료되었습니다!");
                    new LogInScreen();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "회원가입 중 오류가 발생했습니다.");
                }
            }
        });

        setVisible(true);
    }
}
