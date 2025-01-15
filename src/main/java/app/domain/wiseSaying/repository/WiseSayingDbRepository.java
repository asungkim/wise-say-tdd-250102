package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import app.standard.simpleDb.SimpleDb;
import app.standard.simpleDb.Sql;

import java.util.Optional;

public class WiseSayingDbRepository {
    private final SimpleDb simpleDb;

    public WiseSayingDbRepository() {
        this.simpleDb = new SimpleDb("localhost", "root", "lldj123414", "wiseSaying__test");
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        return null;
    }

    public void createWiseSayingTable() {
        simpleDb.run("DROP TABLE IF EXISTS wise_saying");

        simpleDb.run("""
                CREATE TABLE wise_saying (
                    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    content VARCHAR(100) NOT NULL,
                    author VARCHAR(100) NOT NULL
                )
                """);
    }

    public void truncateWiseSayingTable() {
        simpleDb.run("TRUNCATE TABLE wise_saying");
    }

    public Optional<WiseSaying> findById(int id) {
        return null;
    }
}
