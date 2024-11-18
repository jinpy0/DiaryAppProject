package Diary.UI;

import Diary.DataBase.DataBase;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// 왼쪽으로 이동시켜야 함, 간격 추가

public class SignUpScreen extends JFrame {
    private String imagePath; // 이미지 경로 저장 변수
    private final String defaultImagePath = "defaultImage.png"; // 기본 이미지 경로

    public SignUpScreen() {
        // 기본 설정
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
                if(result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath(); // 이미지 경로 저장

                    // 이미지 표시
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(null); // 기존 텍스트 제거
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
        JPanel namePanel =  new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("이름 : ");
        JTextField nameField = new JTextField(15);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // 이메일 입력 패널
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("이메일 : ");
        JTextField emailField = new JTextField(15); // 열 수를 10으로 설정하여 폭 조절
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // 비밀번호 입력 패널
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(15); // 열 수를 10으로 설정하여 폭 조절
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // 비밀번호 확인 입력 패널
        JPanel passwordConfirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
        JPasswordField confirmPasswordField = new JPasswordField(15); // 열 수를 10으로 설정하여 폭 조절
        passwordConfirmPanel.add(confirmPasswordLabel);
        passwordConfirmPanel.add(confirmPasswordField);

        // 뒤로가기, 회원가입 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton signupButton = new JButton("회원가입");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(backButton);
        buttonPanel.add(signupButton);

        // 패널 추가
        add(imagePanel);
        add(idPanel);
        add(namePanel);
        add(emailPanel);
        add(passwordPanel);
        add(passwordConfirmPanel);
        add(buttonPanel);

        // 하단 여백
        add(Box.createVerticalStrut(30));

        // 중복확인 버튼 구현해야 함


        // 뒤로가기 버튼
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen();
                dispose();
            }
        });

        // 회원가입 버튼
//        signupButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                String imagePath = imageLabel.getText();
//                String userId = userIdField.getText();
//                String name = nameField.getText();
//                String email = emailField.getText();
//                String password = String.valueOf(passwordField.getPassword());
//                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
//
//                // 입력해야 하는 값들을 입력하지 않았을 때
//                // id 입력 x
//                if(userId.isEmpty()){
//                    JOptionPane.showMessageDialog(SignUpScreen.this, "아이디를 입력해주세요");
//                    return;
//                }
//                // 이름 입력 x
//                if(name.isEmpty()){
//                    JOptionPane.showMessageDialog(SignUpScreen.this, "이름을 입력해주세요");
//                    return;
//                }
//                // 이메일 입력 x
//                if(email.isEmpty()){
//                    JOptionPane.showMessageDialog(SignUpScreen.this, "이메일을 입력해주세요");
//                    return;
//                }
//                // 비밀번호 입력 x
//                if(password.isEmpty()){
//                    JOptionPane.showMessageDialog(SignUpScreen.this, "비밀번호를 입력해주세요");
//                }
//                // 비밀번호 확인 입력 x
//                if(confirmPassword.isEmpty()){
//                    JOptionPane.showMessageDialog(SignUpScreen.this, "비밀번호 확인란을 입력해주세요");
//                    return;
//                }
//
//                // 비밀번호가 틀렸을 때
//                if(!password.equals(confirmPassword)) {
//                    JOptionPane.showMessageDialog(SignUpScreen.this, "비밀번호가 일치하지 않습니다.");
//                    return;
//                }
//                // 이미지를 넣지 않았을 때 ( 기본 이미지 설정 )
//                if(imagePath == null || imagePath.isEmpty()){
//                    imagePath = defaultImagePath;
//                }
//                // 중복확인 버튼을 눌렀을 때 로직 추가해야 함
//
//                // 중복확인을 안했을 때 로직 추가해야 함
//
//                // 회원가입 정보를 DB에 저장하는 로직을 추가해야 함
//
//                // 로그인 화면으로 이동하는 로직
//                new LogInScreen();
//                dispose();
//            }
//        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

                if (userId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "모든 필드를 입력해주세요.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "비밀번호가 일치하지 않습니다.");
                    return;
                }

                String imagePath = "default/path/to/image"; // 기본 이미지 경로

                boolean success = DataBase.insertUser(userId, name, email, password, imagePath);

                if (success) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "회원가입이 완료되었습니다!");
                } else {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "회원가입 중 오류가 발생했습니다.");
                }
            }
        });


        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpScreen::new);
    }
}
