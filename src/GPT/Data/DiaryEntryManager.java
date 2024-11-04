package GPT.Data;

import java.util.Date;
import java.util.List;

public class DiaryEntryManager {
    private DatabaseManager databaseManager; // 데이터베이스 매니저

    public DiaryEntryManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager; // 데이터베이스 매니저 초기화
    }

    // 다이어리 항목 추가
    public void addDiaryEntry(DiaryEntry entry) {
        databaseManager.insertDiaryEntry(entry); // 데이터베이스에 항목 추가
    }

    // 다이어리 항목 수정
    public void updateDiaryEntry(DiaryEntry entry) {
        databaseManager.updateDiaryEntry(entry); // 데이터베이스에서 항목 수정
    }

    // 다이어리 항목 삭제
    public void deleteDiaryEntry(Date date) {
        databaseManager.deleteDiaryEntry(date); // 데이터베이스에서 항목 삭제
    }

    // 특정 날짜의 다이어리 항목 검색
    public DiaryEntry getDiaryEntry(Date date) {
        return databaseManager.getDiaryEntry(date); // 데이터베이스에서 항목 검색
    }

    // 모든 다이어리 항목 검색
    public List<DiaryEntry> getAllDiaryEntries() {
        return databaseManager.getAllDiaryEntries(); // 모든 항목 검색
    }
}
