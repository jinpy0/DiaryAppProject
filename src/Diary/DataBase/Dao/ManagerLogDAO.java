package Diary.DataBase.Dao;

import Diary.DataBase.DBConnection;
import Diary.DataBase.Dto.ManagerLogDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerLogDAO {
    private Connection conn;

    public ManagerLogDAO(Connection conn) {
        this.conn = DBConnection.getConnection();
    }

    // ManagerLog 추가
    public boolean addManagerLog(ManagerLogDTO log) {
        String sql = "INSERT INTO ManagerLog (admin_id, target_user_id, action, action_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, log.getAdminId());
            stmt.setString(2, log.getTargetUserId());
            stmt.setString(3, log.getAction());
            stmt.setTimestamp(4, new Timestamp(log.getActionDate().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ManagerLog 조회 (관리자별)
    public List<ManagerLogDTO> getLogsByAdminId(String adminId) {
        List<ManagerLogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM ManagerLog WHERE admin_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adminId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new ManagerLogDTO(
                        rs.getInt("id"),
                        rs.getString("admin_id"),
                        rs.getString("target_user_id"),
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
