package Diary.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerScreen extends JFrame {

    public ManagerScreen() {
        setTitle("사용자 목록");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 테이블 열 제목
        String[] columnNames = {"이름", "사용자 ID", "이메일", "정보 보기"};

        // 테이블 데이터 // 데이터베이스에서 받아오는 방법 ?
        Object[][] data = {
                // 일단
                {"일진표", "1_jinpyo", "1jinpyo@gmail.com", "정보 보기"},
                {"이진표", "2_jinpyo", "2jinpyo@gmail.com", "정보 보기"},
                {"삼진표", "3_jinpyo", "3jinpyo@gmail.com", "정보 보기"},
                {"사진표", "4_jinpyo", "4jinpyo@gmail.com", "정보 보기"},
                {"오진표", "5_jinpyo", "5jinpyo@gmail.com", "정보 보기"},
                {"육진표", "6_jinpyo", "6jinpyo@gmail.com", "정보 보기"},
                {"칠진표", "7_jinpyo", "7jinpyo@gmail.com", "정보 보기"},
                {"팔진표", "8_jinpyo", "8jinpyo@gmail.com", "정보 보기"},
                {"구진표", "9_jinpyo", "9jinpyo@gmail.com", "정보 보기"},
                {"십진표", "10_jinpyo", "10jinpyo@gmail.com", "정보 보기"}
        };

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
        columnModel.getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
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
                JOptionPane.showMessageDialog(button, "사용자 정보를 확인합니다.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManagerScreen::new);
    }
}
