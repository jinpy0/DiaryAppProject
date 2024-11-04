package GPT.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    // 파일에 문자열 쓰기
    public static void writeFile(String filePath, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    // 파일에서 문자열 읽기
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // 파일 삭제
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete(); // 파일 삭제 성공 여부 반환
    }
}
