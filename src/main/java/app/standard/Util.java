package app.standard;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
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
            } catch (Exception e) {
                System.out.println("파일 읽기 실패");
                e.printStackTrace();
            }

            return "";
        }

        public static void write(String file, int content) {
            write(file, String.valueOf(content));
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

        public static boolean delete(String file) {
            Path filePath = Paths.get(file);

            if (!Files.exists(filePath)) return false;

            try {
                Files.delete(filePath);
                return true;
            } catch (IOException e) {
                System.out.println("파일 삭제 실패");
                e.printStackTrace();
                return false;
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

        public static List<Path> getPaths(String dirPath) {
            try {
                return Files.walk(Paths.get(dirPath))
                        .filter(Files::isRegularFile)
                        .toList();
            } catch (Exception e) {
                System.out.println("파일 목록 가져오기 실패");
                e.printStackTrace();
            }

            return List.of();
        }

        public static boolean exists(String filePath) {
            return Files.exists(Paths.get(filePath));
        }
    }

    public static class Json {

        public static String mapToJson(Map<String, Object> map) {

            StringBuilder sb = new StringBuilder();
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

        public static String listToJson(List<Map<String, Object>> mapList) {
            StringBuilder jsonBuilder = new StringBuilder();

            jsonBuilder.append("[\n");

            String str = mapList.stream()
                    .map(Json::mapToJson)
                    .map(s -> "\t" + s)
                    .map(s -> s.replaceAll("\n", "\n\t"))
                    .collect(Collectors.joining(",\n"));

            jsonBuilder.append(str);
            jsonBuilder.append("\n]");

            return jsonBuilder.toString();
        }

        public static void writeAsMap(String filePath, Map<String, Object> wiseSayingMap) {
            // map을 통해 json 으로 바꾸고 이를 파일에 write

            String jsonStr = mapToJson(wiseSayingMap);

            File.write(filePath, jsonStr);
        }

        public static Map<String, Object> readAsMap(String filePath) {
            String jsonStr = File.readAsString(filePath);

            if (jsonStr.isEmpty()) return new LinkedHashMap<>();


            return jsonToMap(jsonStr);


        }

        public static Map<String, Object> jsonToMap(String jsonStr) {

            Map<String, Object> map = new LinkedHashMap<>();

            jsonStr = jsonStr.replaceAll("\\{", "")
                    .replaceAll("}", "")
                    .replaceAll("\n", "");

            Arrays.stream(jsonStr.split(","))
                    .map(p -> p.trim().split(":"))
                    .forEach(p -> {
                        String key = p[0].replaceAll("\"", "");
                        String value = p[1].trim();

                        if (value.startsWith("\"")) {
                            map.put(key, value.replaceAll("\"", ""));
                        } else if (value.contains(".")) {
                            map.put(key, Double.parseDouble(value));
                        } else if (value.contains("true") || value.contains("false")) {
                            map.put(key, Boolean.parseBoolean(value));
                        } else {
                            map.put(key, Integer.parseInt(value));
                        }
                    });

            return map;
        }


    }

}
