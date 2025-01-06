package app.standard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    public static class File {
        public static void test() {
            System.out.println("파일 유틸 테스트");
        }

        public static void createFile(String fileName)  {
            Path filePath= Paths.get(fileName);

            try {
                Files.createFile(filePath);
            }
            catch (Exception e) {
                System.out.println("파일 생성 실패");
                e.printStackTrace();
            }
        }

        public static String readAsString(String file) {
            return null;
        }
    }

}
