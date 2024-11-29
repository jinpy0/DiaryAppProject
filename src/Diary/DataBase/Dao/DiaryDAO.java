package Diary.DataBase.Dao;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.DiaryDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {
    private Connection conn;

    public DiaryDAO(Connection conn) {
        this.conn = DBConnection.getConnection();
    }

    // Diary 추가
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


    // 모든 다이어리 조회 // 관리자용
    public List<DiaryDTO> getAllDiaries() {
        List<DiaryDTO> diaries = new ArrayList<>();
        String query = "SELECT * FROM Diary";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // LocalDate 로 바꾸기
                LocalDate createDate = rs.getTimestamp("create_date").toLocalDateTime().toLocalDate();
                LocalDate updateDate = rs.getTimestamp("update_date") != null ? rs.getTimestamp("update_date").toLocalDateTime().toLocalDate() : null;
                // 리스트에 추가하기
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

    // Diary 조회 (사용자별)
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

    // Diary 삭제
    public boolean deleteDiary(int id) {
        String sql = "DELETE FROM Diary WHERE diary_id = ?";  // 컬럼 이름 확인
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
