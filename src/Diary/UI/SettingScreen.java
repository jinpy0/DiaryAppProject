package Diary.UI;

import Diary.DataBase.Dao.UserDAO;
import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.UserDTO;
import Diary.DataBase.service.UserSession;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;

public class SettingScreen extends JFrame {

    private String imagePath;

    public SettingScreen(Connection conn) {
        UserDTO user = UserSession.getInstance().getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 로그인 화면으로 이동합니다.");
            new LogInScreen();
            dispose();
            return;
        }

        this.imagePath = user.getImage();

        setTitle("설정 화면");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(120, 120));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                    .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("이미지 없음");
        }
        imagePanel.add(imageLabel);

        JPanel buttonPanel = new JPanel();
        JButton selectImageBtn = new JButton("사진 선택");
        buttonPanel.add(selectImageBtn);

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel setIdLabel = new JLabel("아이디 수정 : ");
        JTextField setIdField = new JTextField(15);
        setIdField.setText(user.getUserId()); // 기존 아이디 설정
        JButton checkIdBtn = new JButton("중복 확인");
        idPanel.add(setIdLabel);
        idPanel.add(setIdField);
        idPanel.add(checkIdBtn);

        JPanel setEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("이메일 수정 : ");
        JTextField setEmailField = new JTextField(15);
        setEmailField.setText(user.getEmail()); // 기존 이메일 설정
        setEmailPanel.add(emailLabel);
        setEmailPanel.add(setEmailField);

        JPanel currentPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel crtPasswordLabel = new JLabel("현재 비밀번호 : ");
        JPasswordField crtPasswordField = new JPasswordField(15);
        currentPasswordPanel.add(crtPasswordLabel);
        currentPasswordPanel.add(crtPasswordField);

        JPanel setPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel setPasswordLabel = new JLabel("변경할 비밀번호 : ");
        JPasswordField setPasswordField = new JPasswordField(15);
        setPasswordPanel.add(setPasswordLabel);
        setPasswordPanel.add(setPasswordField);

        JPanel checkPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel checkPasswordLabel = new JLabel("비밀번호 확인 : ");
        JPasswordField checkPasswordField = new JPasswordField(15);
        checkPasswordPanel.add(checkPasswordLabel);
        checkPasswordPanel.add(checkPasswordField);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("뒤로가기");
        JButton setBtn = new JButton("정보 수정");
        btnPanel.add(backBtn);
        btnPanel.add(setBtn);

        selectImageBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

            int result = chooser.showOpenDialog(SettingScreen.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                imagePath = file.getAbsolutePath();

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
                imageLabel.setText(null);
            }
        });

        checkIdBtn.addActionListener(e -> {
            UserDAO userDAO = new UserDAO(conn);
            String newId = setIdField.getText();
            if (userDAO.isUserIdOverlap(newId)) {
                JOptionPane.showMessageDialog(SettingScreen.this, "아이디가 중복됩니다. 다른 아이디를 입력해주세요.");
            } else {
                JOptionPane.showMessageDialog(SettingScreen.this, "사용 가능한 아이디입니다.");
            }
        });

        backBtn.addActionListener(e -> {
            new DiaryListScreen(conn);
            dispose();
        });

        setBtn.addActionListener(e -> {
            String newId = setIdField.getText();
            String newEmail = setEmailField.getText();
            String currentPassword = new String(crtPasswordField.getPassword());
            String newPassword = new String(setPasswordField.getPassword());
            String confirmPassword = new String(checkPasswordField.getPassword());
            if (!user.getPassword().equals(currentPassword)) {
                JOptionPane.showMessageDialog(SettingScreen.this, "현재 비밀번호가 틀립니다.");
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(SettingScreen.this, "새 비밀번호가 일치하지 않습니다.");
                return;
            }
            user.setUserId(newId);
            user.setEmail(newEmail);
            user.setPassword(newPassword);
            user.setImage(imagePath);
            UserDAO userDAO = new UserDAO(conn);
            boolean isUpdated = userDAO.updateUser(user);
            if (isUpdated) {
                JOptionPane.showMessageDialog(SettingScreen.this, "정보가 수정되었습니다.");
                new DiaryListScreen(conn);
                dispose();
            } else {
                JOptionPane.showMessageDialog(SettingScreen.this, "정보 수정에 실패했습니다. 다시 시도해주세요.");
            }
        });

        // 컴포넌트 추가
        add(imagePanel);
        add(buttonPanel);
        add(idPanel);
        add(setEmailPanel);
        add(currentPasswordPanel);
        add(setPasswordPanel);
        add(checkPasswordPanel);
        add(btnPanel);

        setVisible(true);
    }
}
