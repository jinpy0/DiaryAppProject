package Diary.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class MariaDBConnectionTest {
    static String driver = "org.mariadb.jdbc.Driver";
    static String url = "jdbc:mariadb://localhost:3306/test";
    static String username = "root";
    static String password = "1234";

    public static void main(String[] args) {
        Connection conn = null;

        try {
            Class.forName(driver);
            System.out.println("JDBC 드라이버 로드 성공");

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("DB 접속 성공");
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버 로드 실패: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("DB 연결 실패: " + e.getMessage());
            e.printStackTrace(); // 예외 세부정보 출력
        }
        finally {
            // 연결 종료
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("DB 연결 종료");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
