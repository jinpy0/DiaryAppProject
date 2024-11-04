package FirstTest.UI;

import javax.swing.*;
import java.awt.*;

public class FindIdPassWordScreen extends JFrame {
    public FindIdPassWordScreen() {
        // 기본 설정
        setTitle("아이디 / 비밀번호 찾기 화면");
        setSize(350, 600); // 사이즈를 350 x 600으로 변경
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // 수직 박스 레이아웃 설정

//        ID 찾기 ( Email 입력 )
        JPanel findIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputEmail = new JLabel("이메일 : ");
        JTextField inputEmailField = new JTextField(20);
        JButton idBtn = new JButton("아이디 찾기");
        JPanel findIdPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        findPasswordPanel1.add(idBtn);
        findIdPanel.add(inputEmail);
        findIdPanel.add(inputEmailField);

//        비밀번호 찾기 (아이디, 이메일 입력)
//        아이디 입력
        JPanel findPassWordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel findPasswordPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputId = new JLabel("ID : ");
        JTextField inputIdField = new JTextField(20);


//        이메일 입력
        JPanel inputEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel inputEmailPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputEmailLabel = new JLabel("Email : ");
        JTextField inputEmailField1 = new JTextField(20);
        JButton passwordBtn = new JButton("비밀번호 찾기");

        findPassWordPanel.add(inputId);
        findPassWordPanel.add(inputIdField);
        inputEmailPanel.add(inputEmailLabel);
        inputEmailPanel.add(inputEmailField1);
        inputEmailPanel1.add(passwordBtn);


        add(findIdPanel);
        add(findIdPanel1);
        add(findPassWordPanel);

        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FindIdPassWordScreen::new);
    }
}
