package Diary.DataBase.service;

import Diary.DataBase.Dao.DiaryDAO;
import Diary.DataBase.Dto.DiaryDTO;

import java.sql.Connection;
import java.util.List;

public class DiaryService {
    private DiaryDAO diaryDAO;

    public DiaryService(Connection conn) {
        this.diaryDAO = new DiaryDAO(conn);
    }

    public List<DiaryDTO> getAllDiaries(String userId) {
        return diaryDAO.getDiariesByUserId(userId);
    }
}
