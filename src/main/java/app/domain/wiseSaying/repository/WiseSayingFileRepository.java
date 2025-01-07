package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import app.standard.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository {
    private static final String DB_PATH = "db/test/wiseSaying/";
    private final List<WiseSaying> wiseSayingList;
    private int lastId;

    public WiseSayingFileRepository() {
        wiseSayingList = new ArrayList<>();
        System.out.println("파일 DB 사용");
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        // 파일 저장
        Util.Json.writeAsMap(getFilePath(wiseSaying.getId()).formatted(wiseSaying.getId()), wiseSaying.toMap());

        return wiseSaying;
    }

    private String getFilePath(int id) {
        return DB_PATH + "%d.json".formatted(id);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingList;
    }

    public boolean deleteById(int id) {
        // id를 통해 파일을 찾아서 삭제하기
        return Util.File.delete(getFilePath(id).formatted(id));
    }


    public Optional<WiseSaying> findById(int id) {

        Optional<WiseSaying> opWiseSaying = wiseSayingList.stream()
                .filter(w -> w.getId() == id)
                .findFirst();

        return opWiseSaying;
    }
}
