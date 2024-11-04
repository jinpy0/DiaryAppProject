package GPT.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PhotoUploadDialog extends JDialog {
    private JLabel photoPreviewLabel;  // 사진 미리보기를 위한 레이블
    private File selectedFile;          // 선택된 파일

    public PhotoUploadDialog(Frame owner) {
        super(owner, "Upload Photo", true);  // 모달 다이얼로그 생성
        setLayout(new BorderLayout(10, 10)); // 레이아웃 설정

        // 사진 미리보기를 위한 레이블 초기화
        photoPreviewLabel = new JLabel("No photo selected", JLabel.CENTER);
        add(photoPreviewLabel, BorderLayout.CENTER);  // 중앙에 미리보기 레이블 추가

        // 파일 선택 버튼
        JButton selectFileButton = new JButton("Select Photo");
        selectFileButton.addActionListener(new SelectFileAction()); // 버튼에 액션 리스너 추가

        // 하단 패널에 버튼 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectFileButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 다이얼로그 크기 및 종료 설정
        setSize(400, 300);
        setLocationRelativeTo(owner);  // 중앙에 위치
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // 사진 파일을 선택하는 액션 리스너
    private class SelectFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();  // 파일 선택기 생성
            int result = fileChooser.showOpenDialog(PhotoUploadDialog.this); // 파일 선택 다이얼로그 표시

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();  // 선택한 파일 저장
                updatePhotoPreview();  // 미리보기 업데이트
            }
        }
    }

    // 선택한 사진의 미리보기를 업데이트하는 메서드
    private void updatePhotoPreview() {
        if (selectedFile != null) {
            // 선택된 파일의 이름을 레이블에 표시
            photoPreviewLabel.setText("Selected Photo: " + selectedFile.getName());

            // 이미지 미리보기 (선택적으로 작은 이미지 미리보기 추가 가능)
            // 이 부분은 실제 이미지 미리보기를 추가하고 싶다면 수정해야 함.
            // 예: 이미지 아이콘으로 변경하는 로직 구현
            // ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
            // photoPreviewLabel.setIcon(icon);
        }
    }

    // 선택한 파일을 반환하는 메서드
    public File getSelectedFile() {
        return selectedFile;
    }
}
