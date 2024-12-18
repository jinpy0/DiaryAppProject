package Diary.DataBase.Dao;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.DiaryDTO;
import Diary.DataBase.Dto.UserDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {
    private Connection conn;

    public DiaryDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addDiary(DiaryDTO diary) {
        String sql = "INSERT INTO Diary (user_id, diary_image, diary_title, diary_content, create_date, update_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, diary.getUserId());
            stmt.setString(2, diary.getDiaryImage());
            stmt.setString(3, diary.getDiaryTitle());
            stmt.setString(4, diary.getDiaryContent());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(6, null);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DiaryDTO> getAllDiaries() {
        List<DiaryDTO> diaries = new ArrayList<>();
        String query = "SELECT * FROM Diary";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate createDate = rs.getTimestamp("create_date").toLocalDateTime().toLocalDate();
                LocalDate updateDate = rs.getTimestamp("update_date") != null ? rs.getTimestamp("update_date").toLocalDateTime().toLocalDate() : null;
                DiaryDTO diary = new DiaryDTO(
                        rs.getInt("diary_id"),
                        rs.getString("user_id"),
                        rs.getString("diary_image"),
                        rs.getString("diary_title"),
                        rs.getString("diary_content"),
                        createDate,
                        updateDate
                );
                diaries.add(diary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaries;
    }

    public List<DiaryDTO> getDiariesByUserId(String userId) {
        List<DiaryDTO> diaries = new ArrayList<>();
        String sql = "SELECT * FROM Diary WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate createDate = rs.getTimestamp("create_date").toLocalDateTime().toLocalDate();
                LocalDate updateDate = rs.getTimestamp("update_date") != null ? rs.getTimestamp("update_date").toLocalDateTime().toLocalDate() : null;
                diaries.add(new DiaryDTO(
                        rs.getInt("diary_id"),
                        rs.getString("user_id"),
                        rs.getString("diary_image"),
                        rs.getString("diary_title"),
                        rs.getString("diary_content"),
                        createDate,
                        updateDate
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaries;
    }

    public DiaryDTO getDiaryWithUserById(int diaryId) throws SQLException {
        String sql = "SELECT d.*, u.name, u.email, u.role FROM Diary d " +
                "JOIN Users u ON d.user_id = u.user_id WHERE d.diary_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, diaryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DiaryDTO diary = new DiaryDTO();
                    diary.setId(rs.getInt("diary_id"));
                    diary.setUserId(rs.getString("user_id"));
                    diary.setDiaryTitle(rs.getString("diary_title"));
                    diary.setDiaryContent(rs.getString("diary_content"));
                    diary.setDiaryImage(rs.getString("diary_image"));
                    diary.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime().toLocalDate());
                    diary.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime().toLocalDate());

                    // 작성자 정보 추가
                    UserDTO user = new UserDTO();
                    user.setUserId(rs.getString("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));

                    diary.setUser(user); // 작성자 정보를 DiaryDTO에 설정
                    return diary;
                }
            }
        }
        return null; // 결과가 없으면 null 반환
    }




//    public DiaryDTO getDiaryWithUserById(int diaryId) throws SQLException {
//        String sql = "SELECT d.*, u.name, u.email, u.role FROM Diary d JOIN Users u ON d.user_id = u.user_id WHERE d.diary_id = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, diaryId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    DiaryDTO diary = new DiaryDTO();
//                    diary.setId(rs.getInt("diary_id"));
//                    diary.setUserId(rs.getString("user_id"));
//                    diary.setDiaryTitle(rs.getString("diary_title"));
//                    diary.setDiaryContent(rs.getString("diary_content"));
//                    diary.setDiaryImage(rs.getString("diary_image"));
//
//                    // LocalDateTime으로 변환
//                    diary.setCreateDate(rs.getTimestamp("create_date") != null
//                            ? rs.getTimestamp("create_date").toLocalDateTime().toLocalDate()
//                            : null);
//                    diary.setUpdateDate(rs.getTimestamp("update_date") != null
//                            ? rs.getTimestamp("update_date").toLocalDateTime().toLocalDate()
//                            : null);
//
//                    // 작성자 정보 추가
//                    UserDTO user = new UserDTO();
//                    user.setUserId(rs.getString("user_id"));
//                    user.setName(rs.getString("name"));
//                    user.setEmail(rs.getString("email"));
//                    user.setRole(rs.getString("role"));
//                    diary.setUser(user);
//
//                    return diary;
//                }
//            }
//        }
//        return null; // 결과가 없으면 null 반환
//    }


    // Diary 수정
    public boolean updateDiary(DiaryDTO diary) {
        String sql = "UPDATE Diary SET diary_image = ?, diary_title = ?, diary_content = ?, update_date = ? WHERE diary_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, diary.getDiaryImage());
            stmt.setString(2, diary.getDiaryTitle());
            stmt.setString(3, diary.getDiaryContent());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, diary.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDiaryById(int diaryId) {
        String sql = "DELETE FROM diary WHERE diary_id = ?"; // 테이블의 실제 컬럼 이름 확인
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, diaryId); // diary_id 값을 바인딩
            int affectedRows = stmt.executeUpdate(); // 삭제된 행 수 반환
            return affectedRows > 0; // 성공적으로 삭제된 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace(); // 에러 출력
            return false; // 실패한 경우 false 반환
        }
    }

}
