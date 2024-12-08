package Diary.UI;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dao.UserDAO;
import Diary.DataBase.Dto.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ManagerScreen extends JFrame {

    private UserDTO user;
    private List<UserDTO> users;

    public ManagerScreen(UserDTO user, Connection conn) {
        setTitle("사용자 목록");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // UserDAO를 통해 데이터베이스에서 사용자 데이터 가져오기
        UserDAO userDAO = new UserDAO(conn);
        users = userDAO.getAllUsers(); // 데이터베이스에서 사용자 정보 가져오기

        // 테이블 열 제목
        String[] columnNames = {"이름", "사용자 ID", "이메일", "정보 보기"};

        // users 리스트를 Object[][] 배열로 변환
        Object[][] data = new Object[users.size()][4];
        for (int i = 0; i < users.size(); i++) {
            UserDTO u = users.get(i);
            data[i][0] = u.getName();        // 이름
            data[i][1] = u.getUserId();     // 사용자 ID
            data[i][2] = u.getEmail();      // 이메일
            data[i][3] = "정보 보기";       // 버튼 텍스트
        }

        // 테이블 모델 생성
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {
            // '정보 보기' 열만 편집 가능 (버튼 클릭 가능)
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        // 버튼 추가
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(3).setCellRenderer(new ButtonRenderer());
        columnModel.getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), table, users, conn)); // 테이블과 users 전달

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 로그아웃 버튼
        JButton logoutBtn = new JButton("로그아웃");
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen();
                dispose();
            }
        });
        add(logoutBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 버튼 렌더러 클래스
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "정보 보기" : value.toString());
            return this;
        }
    }

    // 버튼 에디터 클래스
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private JTable table; // 테이블 참조 추가
        private List<UserDTO> users; // users 리스트 참조 추가
        private Connection conn; // 데이터베이스 연결 추가

        public ButtonEditor(JCheckBox checkBox, JTable table, List<UserDTO> users, Connection conn) {
            super(checkBox);
            this.table = table; // 전달받은 테이블 저장
            this.users = users; // 전달받은 users 리스트 저장
            this.conn = conn;   // 데이터베이스 연결 저장
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "정보 보기" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                // 버튼 클릭 시 실행할 작업
                int selectedRow = table.getSelectedRow(); // 현재 선택된 행 가져오기
                if (selectedRow >= 0 && selectedRow < users.size()) {
                    UserDTO selectedUser = users.get(selectedRow);
                    new ManagerScreen2(selectedUser, conn); // ManagerScreen2로 이동
                    SwingUtilities.getWindowAncestor(button).dispose(); // 현재 창 닫기
                }
            }
            clicked = false;
            return label;
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}


