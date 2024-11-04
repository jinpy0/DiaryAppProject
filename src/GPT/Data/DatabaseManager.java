package GPT.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseManager {
    private Connection connection; // 데이터베이스 연결

    // 데이터베이스 연결 생성
    public DatabaseManager(String dbUrl, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl, user, password);
    }

    // 다이어리 항목 추가
    public void insertDiaryEntry(DiaryEntry entry) {
        String sql = "INSERT INTO diary_entries (date, content, photo_path, weather) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(entry.getDate().getTime()));
            pstmt.setString(2, entry.getContent());
            pstmt.setString(3, entry.getPhotoPath());
            pstmt.setString(4, entry.getWeather());
            pstmt.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 다이어리 항목 수정
    public void updateDiaryEntry(DiaryEntry entry) {
        String sql = "UPDATE diary_entries SET content = ?, photo_path = ?, weather = ? WHERE date = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entry.getContent());
            pstmt.setString(2, entry.getPhotoPath());
            pstmt.setString(3, entry.getWeather());
            pstmt.setDate(4, new java.sql.Date(entry.getDate().getTime()));
            pstmt.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 다이어리 항목 삭제
    public void deleteDiaryEntry(Date date) {
        String sql = "DELETE FROM diary_entries WHERE date = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            pstmt.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 특정 날짜의 다이어리 항목 검색
    public DiaryEntry getDiaryEntry(Date date) {
        String sql = "SELECT * FROM diary_entries WHERE date = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = pstmt.executeQuery(); // 쿼리 실행
            if (rs.next()) {
                // 결과를 다이어리 항목으로 변환
                return new DiaryEntry(
                        rs.getDate("date"),
                        rs.getString("content"),
                        rs.getString("photo_path"),
                        rs.getString("weather")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
        return null; // 항목이 없으면 null 반환
    }

    // 모든 다이어리 항목 검색
    public List<DiaryEntry> getAllDiaryEntries() {
        List<DiaryEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM diary_entries";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // 결과를 다이어리 항목으로 변환하여 리스트에 추가
                entries.add(new DiaryEntry(
                        rs.getDate("date"),
                        rs.getString("content"),
                        rs.getString("photo_path"),
                        rs.getString("weather")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
        return entries; // 모든 다이어리 항목 반환
    }

    // 데이터베이스 연결 종료
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}

