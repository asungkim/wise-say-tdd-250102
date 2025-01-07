package app.standard;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.stream.Collectors;


public class Util {

    public static class File {
        public static void test() {
            System.out.println("파일 유틸 테스트");
        }

        public static void createFile(String fileName) {
            write(fileName, "");
        }

        public static String readAsString(String file) {
            Path filePath = Paths.get(file);

            try {
                return Files.readString(filePath);
            } catch (IOException e) {
                System.out.println("파일 읽기 실패");
                e.printStackTrace();
            }

            return "";
        }

        public static void write(String file, String content) {
            Path filePath = Paths.get(file);

            if (filePath.getParent() != null) {
                createDir(filePath.getParent().toString());
            }

            try {
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                System.out.println("파일 쓰기 실패");
                e.printStackTrace();
            }
        }

        public static void delete(String file) {
            Path filePath = Paths.get(file);

            if (!Files.exists(filePath)) return;

            try {
                Files.delete(filePath);
            } catch (IOException e) {
                System.out.println("파일 삭제 실패");
                e.printStackTrace();
            }

        }

        public static void deleteForce(String path) {
            Path folderPath = Paths.get(path);

            if (!Files.exists(folderPath)) return;

            try {
                Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void createDir(String dirPath) {
            try {
                Files.createDirectories(Paths.get(dirPath));
            } catch (IOException e) {
                System.out.println("폴더 생성 실패");
                e.printStackTrace();
            }
        }
    }

    public static class Json {

        public static String MapToJson(Map<String, Object> map) {

            StringBuilder sb=new StringBuilder();
            sb.append("{\n");

            String str = map.keySet().stream()
                    .map(k -> map.get(k) instanceof String
                            ? "\t\"%s\": \"%s\"".formatted(k, map.get(k))
                            : "\t\"%s\": %s".formatted(k, map.get(k))
                    ).collect(Collectors.joining(",\n"));

            sb.append(str);
            sb.append("\n}");

            return sb.toString();

//            for (String key : map.keySet()) {
//                sb.append("\t\"").append(key).append("\": ");
//
//                Object value=map.get(key);
//                if (value instanceof String) {
//                    sb.append("\"").append(value).append("\"");
//                }
//                else if (value instanceof Number) {
//                    sb.append(value);
//                }
//
//
//                sb.append("\n");
//            }
//
//            sb.append("}");
//
//            return sb.toString();
        }

        public static void writeAsMap(String filePath, Map<String, Object> wiseSayingMap) {
            // map을 통해 json 으로 바꾸고 이를 파일에 write

            String jsonStr = MapToJson(wiseSayingMap);

            File.write(filePath,jsonStr);
        }
    }

}
