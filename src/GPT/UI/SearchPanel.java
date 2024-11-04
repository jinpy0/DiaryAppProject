package GPT.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchPanel extends JPanel {
    private JTextField searchField;       // 검색어를 입력할 텍스트 필드
    private JButton searchButton;         // 검색 버튼
    private JList<String> resultList;     // 검색 결과를 표시할 리스트
    private DefaultListModel<String> listModel; // 리스트의 데이터 모델

    public SearchPanel() {
        setLayout(new BorderLayout(10, 10));  // 레이아웃 설정
        initSearchComponents();               // 검색 컴포넌트 초기화
    }

    private void initSearchComponents() {
        // 상단 패널에 검색 필드와 검색 버튼 추가
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);  // 검색어 입력 필드 크기 설정
        searchButton = new JButton("Search");  // 검색 버튼 생성
        searchButton.addActionListener(new SearchAction());  // 검색 버튼에 액션 리스너 추가

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // 검색 결과를 표시할 리스트 및 스크롤 패널 설정
        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);  // 리스트에 데이터 모델 설정
        JScrollPane resultScrollPane = new JScrollPane(resultList);

        // 패널에 검색 패널과 결과 스크롤 패널 배치
        add(searchPanel, BorderLayout.NORTH);       // 상단에 검색 패널 추가
        add(resultScrollPane, BorderLayout.CENTER); // 중앙에 검색 결과 리스트 추가
    }

    // 검색 기능을 위한 액션 리스너
    private class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = searchField.getText();  // 검색어 가져오기
            searchDiaries(keyword);                  // 일기 검색 메서드 호출
        }
    }

    // 일기를 검색하는 메서드 (샘플 데이터 사용)
    private void searchDiaries(String keyword) {
        listModel.clear();  // 이전 검색 결과를 삭제

        // 예시 데이터 - 실제 데이터베이스 연동 시 데이터를 가져와야 함
        ArrayList<String> sampleDiaries = new ArrayList<>();
        sampleDiaries.add("2024-11-01: A beautiful day at the park.");
        sampleDiaries.add("2024-10-15: Went hiking and took some photos.");
        sampleDiaries.add("2024-09-20: Had an amazing coffee today.");
        sampleDiaries.add("2024-08-05: My friend's wedding was wonderful.");

        // 검색어를 포함하는 일기 항목을 필터링하여 결과 리스트에 추가
        for (String diary : sampleDiaries) {
            if (diary.toLowerCase().contains(keyword.toLowerCase())) {
                listModel.addElement(diary);  // 검색 결과를 리스트에 추가
            }
        }

        // 검색 결과가 없을 때 메시지 추가
        if (listModel.isEmpty()) {
            listModel.addElement("No results found.");
        }
    }
}
