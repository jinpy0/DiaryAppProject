package Diary.DataBase.Dto;

import java.sql.Timestamp;
import java.util.Date;

public class LogDTO {
    private int id;
    private String adminId;
    private String targetUserId;
    private int diaryId; // 일기 ID
    private String action;
    private Date actionDate;

    public LogDTO(int id, String adminId, String targetUserId, int diaryId, String action, Date actionDate) {
        this.id = id;
        this.adminId = adminId;
        this.targetUserId = targetUserId;
        this.diaryId = diaryId;
        this.action = action;
        this.actionDate = actionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Timestamp getTimestamp() {
        return new Timestamp(actionDate.getTime());
    }
}
