package GPT.UI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // 상단 메뉴바를 위한 변수 선언
    private JMenuBar menuBar;
    // 툴바를 위한 변수 선언
    private JToolBar toolBar;
    // 메인 콘텐츠 패널
    private JPanel contentPanel;
    // 일기 작성/조회 패널
    private DiaryPanel diaryPanel;
    // 검색 패널
    private SearchPanel searchPanel;

    public MainFrame() {
        setTitle("Photo Diary App");  // 프레임 타이틀 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 창 닫기 버튼 클릭 시 애플리케이션 종료
        setSize(800, 600);  // 프레임 크기 설정
        setLocationRelativeTo(null);  // 프레임을 화면 중앙에 배치

        initComponents();  // 컴포넌트 초기화 메서드 호출
        setupLayout();  // 레이아웃 설정 메서드 호출
        setVisible(true);  // 프레임을 표시
    }

    private void initComponents() {
        // 메뉴바 생성 및 초기화
        menuBar = createMenuBar();
        setJMenuBar(menuBar);  // 프레임에 메뉴바 추가

        // 툴바 생성 및 초기화
        toolBar = createToolBar();
        add(toolBar, BorderLayout.NORTH);  // 툴바를 프레임 상단에 배치

        // 카드 레이아웃으로 콘텐츠 패널 생성
        contentPanel = new JPanel(new CardLayout());

        // 일기 패널과 검색 패널 생성
        diaryPanel = new DiaryPanel();
        searchPanel = new SearchPanel();

        // 콘텐츠 패널에 두 패널을 추가하고 각각 이름을 설정
//        contentPanel.add(diaryPanel, "GPT.UI.DiaryPanel");
//        contentPanel.add(searchPanel, "GPT.UI.SearchPanel");

        // 프레임의 센터에 콘텐츠 패널 배치
        add(contentPanel, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();  // 메뉴바 생성

        // 파일 메뉴 생성
        JMenu fileMenu = new JMenu("File");
        JMenuItem newDiaryItem = new JMenuItem("New Diary");  // 새로운 일기 메뉴 항목
        JMenuItem exitItem = new JMenuItem("Exit");  // 종료 메뉴 항목
        exitItem.addActionListener(e -> System.exit(0));  // 종료 메뉴 클릭 시 애플리케이션 종료

        fileMenu.add(newDiaryItem);  // 파일 메뉴에 새로운 일기 항목 추가
        fileMenu.addSeparator();  // 구분선 추가
        fileMenu.add(exitItem);  // 파일 메뉴에 종료 항목 추가
        menuBar.add(fileMenu);  // 메뉴바에 파일 메뉴 추가

        // 보기 메뉴 생성
        JMenu viewMenu = new JMenu("View");
        JMenuItem viewDiaryItem = new JMenuItem("View Diary");  // 일기 보기 항목
        JMenuItem searchDiaryItem = new JMenuItem("Search Diary");  // 일기 검색 항목

        // 일기 보기 메뉴 항목 클릭 시 다이어리 패널 표시
        viewDiaryItem.addActionListener(e -> showPanel("GPT.UI.DiaryPanel"));
        // 검색 메뉴 항목 클릭 시 검색 패널 표시
        searchDiaryItem.addActionListener(e -> showPanel("GPT.UI.SearchPanel"));

        viewMenu.add(viewDiaryItem);  // 보기 메뉴에 일기 보기 항목 추가
        viewMenu.add(searchDiaryItem);  // 보기 메뉴에 검색 항목 추가
        menuBar.add(viewMenu);  // 메뉴바에 보기 메뉴 추가

        return menuBar;  // 초기화된 메뉴바 반환
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();  // 툴바 생성

        // 새로운 일기 버튼 생성 및 액션 추가
        JButton newDiaryButton = new JButton("New Diary");
        newDiaryButton.addActionListener(e -> showPanel("GPT.UI.DiaryPanel"));

        // 검색 버튼 생성 및 액션 추가
        JButton searchDiaryButton = new JButton("Search Diary");
        searchDiaryButton.addActionListener(e -> showPanel("GPT.UI.SearchPanel"));

        // 툴바에 버튼 추가
        toolBar.add(newDiaryButton);
        toolBar.add(searchDiaryButton);

        return toolBar;  // 초기화된 툴바 반환
    }

    private void setupLayout() {
        setLayout(new BorderLayout());  // 프레임의 기본 레이아웃을 BorderLayout으로 설정
    }

    // 카드 레이아웃을 전환하는 메서드
    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, panelName);  // 지정된 패널로 전환
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);  // 메인 스레드에서 프레임을 실행
    }
}
