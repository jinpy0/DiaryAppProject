package Diary.DataBase.Dao;

import Diary.DataBase.Dto.LogDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {
    private Connection conn;

    public LogDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addLog(LogDTO log) {
        String sql = "INSERT INTO ManagerLog (admin_id, target_user_id, diary_id, action, action_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, log.getAdminId());
            stmt.setString(2, log.getTargetUserId());
            stmt.setInt(3, log.getDiaryId());
            stmt.setString(4, log.getAction());
            stmt.setTimestamp(5, new Timestamp(log.getActionDate().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LogDTO> getLogsByUserId(String userId) {
        List<LogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM ManagerLog WHERE target_user_id = ? ORDER BY action_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new LogDTO(
                        rs.getInt("log_id"),
                        rs.getString("admin_id"),
                        rs.getString("target_user_id"),
                        rs.getInt("diary_id"),
                        rs.getString("action"),
                        rs.getTimestamp("action_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}
