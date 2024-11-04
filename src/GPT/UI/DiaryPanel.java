package GPT.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DiaryPanel extends JPanel {
    private JTextArea diaryTextArea;    // 일기 내용을 입력할 텍스트 영역
    private JLabel photoLabel;          // 사진 미리보기 라벨
    private JButton uploadPhotoButton;  // 사진 업로드 버튼
    private JTextField dateField;       // 날짜 입력 필드
    private JButton saveButton;         // 저장 버튼

    public DiaryPanel() {
        setLayout(new BorderLayout(10, 10));  // 레이아웃 설정

        // 일기 작성 패널 초기화
        initDiaryComponents();
    }

    private void initDiaryComponents() {
        // 일기 내용을 작성하는 패널
        JPanel diaryContentPanel = new JPanel(new BorderLayout());
        diaryTextArea = new JTextArea(10, 30);  // 텍스트 영역 크기 설정
        diaryContentPanel.add(new JScrollPane(diaryTextArea), BorderLayout.CENTER);

        // 날짜 입력 패널
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Date:"));
        dateField = new JTextField(10);  // 날짜 입력 필드 생성
        datePanel.add(dateField);

        // 사진 업로드와 미리보기를 위한 패널
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadPhotoButton = new JButton("Upload Photo");
        uploadPhotoButton.addActionListener(new UploadPhotoAction());  // 버튼에 액션 리스너 추가
        photoLabel = new JLabel();  // 사진 미리보기 라벨
        photoPanel.add(uploadPhotoButton);
        photoPanel.add(photoLabel);

        // 저장 버튼 추가
        saveButton = new JButton("Save Entry");
        saveButton.addActionListener(e -> saveDiaryEntry());  // 버튼 클릭 시 저장 기능 호출

        // 상단 패널 (날짜, 사진 업로드, 저장 버튼) 추가
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(datePanel, BorderLayout.NORTH);
        topPanel.add(photoPanel, BorderLayout.CENTER);
        topPanel.add(saveButton, BorderLayout.SOUTH);

        // 패널 배치
        add(topPanel, BorderLayout.NORTH);       // 상단에 날짜, 사진 업로드, 저장 버튼 패널 배치
        add(diaryContentPanel, BorderLayout.CENTER);  // 중앙에 일기 작성 텍스트 패널 배치
    }

    // 사진 업로드 기능을 위한 액션 리스너
    private class UploadPhotoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(DiaryPanel.this);  // 파일 선택 창 표시

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                photoLabel.setText("Photo: " + selectedFile.getName());  // 사진 라벨에 파일 이름 표시
            }
        }
    }

    // 일기 내용을 저장하는 메서드 (데이터베이스 연동 가능)
    private void saveDiaryEntry() {
        String date = dateField.getText();
        String content = diaryTextArea.getText();
        String photo = photoLabel.getText();

        // 일기 저장 로직 (데이터베이스 연동 등을 통해 구현 가능)
        JOptionPane.showMessageDialog(this, "Diary Entry Saved:\nDate: " + date + "\nPhoto: " + photo, "Save", JOptionPane.INFORMATION_MESSAGE);
    }
}
