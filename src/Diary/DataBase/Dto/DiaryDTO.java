package Diary.DataBase.Dto;

import java.time.LocalDate;
import java.util.Date;

public class DiaryDTO {
    private int id;
    private String userId;
    private String diaryImage;
    private String diaryTitle;
    private String diaryContent;
    private LocalDate createDate;
    private LocalDate updateDate;

    public DiaryDTO(int id, String userId, String diaryImage, String diaryTitle, String diaryContent, LocalDate createDate, LocalDate updateDate) {
        this.id = id;
        this.userId = userId;
        this.diaryImage = diaryImage;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

//    public DiaryDTO(int id, String userId ,String diaryTitle, String diaryContent, String diaryImage, String createDate, String updateDate) {
//
//    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDiaryImage() { return diaryImage; }
    public void setDiaryImage(String diaryImage) { this.diaryImage = diaryImage; }

    public String getDiaryTitle() { return diaryTitle; }
    public void setDiaryTitle(String diaryTitle) { this.diaryTitle = diaryTitle; }

    public String getDiaryContent() { return diaryContent; }
    public void setDiaryContent(String diaryContent) { this.diaryContent = diaryContent; }

    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    public LocalDate getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }

    @Override
    public String toString() {
        return "DiaryDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", diaryImage='" + diaryImage + '\'' +
                ", diaryTitle='" + diaryTitle + '\'' +
                ", diaryContent='" + diaryContent + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
