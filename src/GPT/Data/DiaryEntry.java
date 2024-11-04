package GPT.Data;
import java.util.Date;

public class DiaryEntry {
    private Date date;            // 다이어리 항목의 날짜
    private String content;       // 다이어리 내용
    private String photoPath;     // 사진 파일 경로
    private String weather;       // 날씨 정보

    // 생성자
    public DiaryEntry(Date date, String content, String photoPath, String weather) {
        this.date = date;
        this.content = content;
        this.photoPath = photoPath;
        this.weather = weather;
    }

    // Getter 및 Setter 메서드
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
