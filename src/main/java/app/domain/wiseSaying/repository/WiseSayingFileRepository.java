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
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        wiseSaying.setId(++lastId);
        // 파일 저장
        Util.Json.writeAsMap(getFilePath(wiseSaying.getId()).formatted(wiseSaying.getId()), wiseSaying.toMap());

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
        return 0;
    }
}
