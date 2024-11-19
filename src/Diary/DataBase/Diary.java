package Diary.DataBase;

import java.time.LocalDate;

public class Diary {
    private String title;
    private String content;
    private String imagePath;
    private LocalDate date; // 날짜 필드 추가

    public Diary() {}

    public Diary(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Diary(String title, String content, String imagePath) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.date = LocalDate.now(); // 기본적으로 오늘 날짜를 설정
    }

    public Diary(String title, String content, String imagePath, LocalDate date) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.date = date; // 전달된 날짜로 설정
    }

    // 제목 getter, setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // 내용 getter, setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 이미지 경로 getter, setter
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // 날짜 getter, setter
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // 일기 내용 출력 (디버깅용)
    @Override
    public String toString() {
        return "제목: " + title + "\n" +
                "내용: " + content + "\n" +
                "이미지 경로: " + imagePath + "\n" +
                "날짜: " + date;
    }
}
