package Diary.DataBase;

import java.sql.*;

public class DataBase {
    private static final String URL = "jdbc:mariadb://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // 회원가입 시 사용자 정보 삽입
    public static boolean insertUser(String username, String name, String email, String password, String imagePath) {
        String insertSQL = "INSERT INTO User (username, name, email, password, image_path) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, imagePath);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // 성공 여부 반환
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 아이디 중복 여부 확인
    public static boolean isUserIdDuplicate(String userId) {
        String query = "SELECT COUNT(*) FROM User WHERE username = ?"; // 테이블 이름과 컬럼 수정

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // 중복된 아이디가 있으면 true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
