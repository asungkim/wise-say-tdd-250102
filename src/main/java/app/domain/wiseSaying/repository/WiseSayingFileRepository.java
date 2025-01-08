package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import app.global.AppConfig;
import app.standard.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository {
    private static final String DB_PATH = AppConfig.getDbPath() + "/wiseSaying";
    private static final String ID_FILE_PATH = DB_PATH + "/lastId.txt";
    private static final String BUILD_PATH = DB_PATH + "/build/data.json";


    public WiseSayingFileRepository() {
        System.out.println("파일 DB 사용");
        init();

    }

    public static String getBuildPath() {
        return BUILD_PATH;
    }

    private void init() {
        if (!Util.File.exists(ID_FILE_PATH)) {
            Util.File.createFile(ID_FILE_PATH);
        }

        if (!Util.File.exists(DB_PATH)) {
            Util.File.createDir(DB_PATH);
        }
    }

    public WiseSaying save(WiseSaying wiseSaying) {

        boolean isNew = wiseSaying.isNew();

        if (isNew) wiseSaying.setId(getLastId() + 1);

        // 파일 저장
        Util.Json.writeAsMap(getFilePath(wiseSaying.getId()).formatted(wiseSaying.getId()), wiseSaying.toMap());

        if (isNew) setLastId(wiseSaying.getId());

        return wiseSaying;
    }

    static String getFilePath(int id) {
        return DB_PATH + "/" + id + ".json";
    }

    public List<WiseSaying> findAll() {
        // 명언들은 파일로 파편화 되어 있다
        // 파일들을 모두 가져와야 한다.
        // 하나씩 읽어서 List로 반환

        List<Path> pathList = Util.File.getPaths(DB_PATH);
        // Path -> String
        return pathList.stream()
                .map(Path::toString)
                .filter(p -> p.endsWith(".json"))
                .map(Util.Json::readAsMap)
                .map(WiseSaying::fromMap)
                .toList();
    }

    public boolean deleteById(int id) {
        // id를 통해 파일을 찾아서 삭제하기
        return Util.File.delete(getFilePath(id).formatted(id));
    }


    public Optional<WiseSaying> findById(int id) {

        String filePath = getFilePath(id);

        Map<String, Object> map = Util.Json.readAsMap(filePath);

        if (map.isEmpty()) return Optional.empty();

        return Optional.of(WiseSaying.fromMap(map));
    }

    public int getLastId() {
        String idStr = Util.File.readAsString(ID_FILE_PATH);
        if (idStr.isEmpty()) return 0;

        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setLastId(int id) {
        Util.File.write(ID_FILE_PATH, id);
    }

    public void build() {
        List<Map<String, Object>> mapList = findAll().stream()
                .map(WiseSaying::toMap)
                .toList();

        String jsonStr = Util.Json.listToJson(mapList);

        Util.File.write(BUILD_PATH, jsonStr);
    }

    @Override
    public void makeSampleData(int cnt) {
        for (int i=1;i<=cnt;i++) {
            WiseSaying wiseSaying=new WiseSaying("명언"+i,"작가"+i);
            save(wiseSaying);
        }
    }
}
