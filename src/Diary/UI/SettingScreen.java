package Diary.UI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SettingScreen extends JFrame {
    public SettingScreen() {
        setTitle("설정 화면");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // 이미지 선택 패널, 버튼
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel(); // 기본 이미지 받아오기 설정해야함
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(120,120));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.add(imageLabel);

        JPanel buttonPanel = new JPanel();
        JButton selectImageBtn = new JButton("사진 선택");
        buttonPanel.add(selectImageBtn);

        // 아이디 입력(수정) 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel setIdLabel = new JLabel("아이디 수정 : ");
        JTextField setIdField = new JTextField(15);
        JButton checkIdBtn = new JButton("중복 확인");
        idPanel.add(setIdLabel);
        idPanel.add(setIdField);
        idPanel.add(checkIdBtn);

        // 이메일 수정 패널
        JPanel setEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("이메일 수정 : ");
        JTextField setEmailField = new JTextField(15);
        setEmailPanel.add(emailLabel);
        setEmailPanel.add(setEmailField);

        // 현재 비밀번호 확인 패널
        JPanel currentPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel crtPasswordLabel = new JLabel("현재 비밀번호 : ");
        JTextField crtPasswordField = new JTextField(15);
        currentPasswordPanel.add(crtPasswordLabel);
        currentPasswordPanel.add(crtPasswordField);

        // 수정할 비밀번호 입력 패널
        JPanel setPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel setPasswordLabel = new JLabel("변경할 비밀번호 : ");
        JTextField setPasswordField = new JTextField(15);
        setPasswordPanel.add(setPasswordLabel);
        setPasswordPanel.add(setPasswordField);
        // 수정할 비밀번호 확인 패널
        JPanel checkPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel checkPasswordLabel = new JLabel("비밀번호 확인 : ");
        JTextField checkPasswordField = new JTextField(15);
        checkPasswordPanel.add(checkPasswordLabel);
        checkPasswordPanel.add(checkPasswordField);

        // 뒤로가기 버튼, 정보 변경 버튼
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("뒤로가기");
        JButton setBtn = new JButton("정보 수정");
        btnPanel.add(backBtn);
        btnPanel.add(setBtn);
        // 버튼 구현

        // 사진 선택 버튼
        selectImageBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

                int result = chooser.showOpenDialog(SettingScreen.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    String path = file.getAbsolutePath();

                    ImageIcon imageIcon = new ImageIcon(path);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(null);
                }
            }
        });

        // 중복 확인 버튼
        checkIdBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 데이터베이스에서 아이디 받아오고 비교하는 로직 추가 필요
            }
        });

        // 뒤로가기 버튼
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new NewDiaryScreen();
                dispose();
            }
        });

        // 정보 수정 버튼
        setBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 수정하는 로직 추가 필요
                new DiaryListScreen();
                dispose();
            }
        });

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SettingScreen::new);
    }
}
