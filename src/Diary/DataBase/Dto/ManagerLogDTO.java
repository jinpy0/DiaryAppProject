package Diary.DataBase.Dto;

import java.util.Date;

public class ManagerLogDTO {
    private int id;
    private String adminId;
    private String targetUserId;
    private String action;
    private Date actionDate;

    public ManagerLogDTO(int id, String adminId, String targetUserId, String action, Date actionDate) {
        this.id = id;
        this.adminId = adminId;
        this.targetUserId = targetUserId;
        this.action = action;
        this.actionDate = actionDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getTargetUserId() { return targetUserId; }
    public void setTargetUserId(String targetUserId) { this.targetUserId = targetUserId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Date getActionDate() { return actionDate; }
    public void setActionDate(Date actionDate) { this.actionDate = actionDate; }

    @Override
    public String toString() {
        return "ManagerLogDTO{" +
                "id=" + id +
                ", adminId='" + adminId + '\'' +
                ", targetUserId='" + targetUserId + '\'' +
                ", action='" + action + '\'' +
                ", actionDate=" + actionDate +
                '}';
    }
}
