package Diary.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static final String URL = "jdbc:mariadb://localhost:3306/diary_project";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // 아이디 중복 여부 확인
    public static boolean isUserIdDuplicate(String userId) {
        String query = "SELECT COUNT(*) FROM User WHERE user_id = ?";
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

    // 로그인
    public static boolean verifyUserCredentials(String username, String password) throws SQLException {
        String query = "SELECT * FROM User WHERE user_id = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // 결과가 있으면 로그인 성공
        }
    }

    // 유저 추가
    public static boolean insertUser(String user_id, String name, String email, String password, String image) {
        String insertSQL = "INSERT INTO User (user_id, name, email, password, image) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, user_id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, image);
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 사용자 정보 가져오기
    public static User getUser(String userId) {
        String query = "SELECT * FROM User WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("user_id"), rs.getString("name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 사용자 정보가 없으면 null 반환
    }

    // 일기 목록 가져오기
    public static List<Diary> getDiaries(String userId) {
        List<Diary> diaries = new ArrayList<>();
        String query = "SELECT * FROM Diary WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Diary diary = new Diary(rs.getString("title"), rs.getString("content"), rs.getString("image_path"));
                diaries.add(diary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaries;
    }

    // 일기 가져오기 (ID로)
    public static Diary getDiaryById(int diaryId) {
        String query = "SELECT * FROM Diary WHERE diary_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, diaryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Diary(rs.getString("title"), rs.getString("content"), rs.getString("image_path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 일기가 없으면 null 반환
    }

    // 일기 수정
    public static boolean updateDiary(int diaryId, String title, String content, String imagePath) {
        String updateSQL = "UPDATE Diary SET title = ?, content = ?, image_path = ? WHERE diary_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, imagePath);
            pstmt.setInt(4, diaryId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 일기 삭제
    public static boolean deleteDiary(int diaryId) {
        String deleteSQL = "DELETE FROM Diary WHERE diary_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, diaryId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
