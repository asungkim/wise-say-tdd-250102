package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import app.standard.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository {
    private static final String DB_PATH = "db/test/wiseSaying/";
    private int lastId;

    public WiseSayingFileRepository() {
        System.out.println("파일 DB 사용");
        lastIdInit();

    }

    private void lastIdInit() {
        if(!Util.File.exists(DB_PATH + "lastId.txt")) {
            Util.File.createFile(DB_PATH + "lastId.txt");
        }
    }

    public WiseSaying save(WiseSaying wiseSaying) {

        if (wiseSaying.isNew()) wiseSaying.setId(getLastId()+1);

        // 파일 저장
        Util.Json.writeAsMap(getFilePath(wiseSaying.getId()).formatted(wiseSaying.getId()), wiseSaying.toMap());

        setLastId(wiseSaying.getId());

        return wiseSaying;
    }

    private String getFilePath(int id) {
        return DB_PATH + "%d.json".formatted(id);
    }

    public List<WiseSaying> findAll() {
        // 명언들은 파일로 파편화 되어 있다
        // 파일들을 모두 가져와야 한다.
        // 하나씩 읽어서 List로 반환

        List<Path> pathList = Util.File.getPaths(DB_PATH);
        // Path -> String
        return pathList.stream()
                .map(Path::toString)
                .filter(p->p.endsWith(".json"))
                .map(Util.Json::readAsMap)
                .map(WiseSaying::fromMap)
                .toList();
    }

    public boolean deleteById(int id) {
        // id를 통해 파일을 찾아서 삭제하기
        return Util.File.delete(getFilePath(id).formatted(id));
    }


    public Optional<WiseSaying> findById(int id) {

        String filePath=getFilePath(id);

        Map<String, Object> map = Util.Json.readAsMap(filePath);

        if (map.isEmpty()) return Optional.empty();

        return Optional.of(WiseSaying.fromMap(map));
    }

    public int getLastId() {
        String idStr = Util.File.readAsString(DB_PATH + "lastId.txt");
        if (idStr.isEmpty()) return 0;

        try {
            return Integer.parseInt(idStr);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setLastId(int id) {
        Util.File.write(DB_PATH+"lastId.txt",id);
    }
}
