package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class WiseSayingDbRepositoryTest {
    private static WiseSayingDbRepository wiseSayingDbRepository = new WiseSayingDbRepository();

    @BeforeAll
    static void dropTable() {
        wiseSayingDbRepository.createWiseSayingTable();
    }

    @BeforeEach
    void truncateWiseSayingTable() {
        wiseSayingDbRepository.truncateWiseSayingTable();
    }


    @Test
    @DisplayName("save, findById 테스트")
    void t1() {
        WiseSaying wiseSaying = new WiseSaying("현재를 사랑하라", "작자미상");
        wiseSayingDbRepository.save(wiseSaying);

        Optional<WiseSaying> opWiseSaying= wiseSayingDbRepository.findById(wiseSaying.getId());

        WiseSaying found=opWiseSaying.orElse(null);

        assertThat(wiseSaying.getId()).isEqualTo(1);
        assertThat(found).isEqualTo(wiseSaying);
    }

    @Test
    @DisplayName("명언 삭제")
    void t2() {
        WiseSaying wiseSaying = new WiseSaying("현재를 사랑하라", "작자미상");
        wiseSaying = wiseSayingDbRepository.save(wiseSaying);

        boolean delRst = wiseSayingDbRepository.deleteById(wiseSaying.getId());

        // 삭제 완료
        assertThat(delRst).isTrue();

    }

    @Test
    @DisplayName("모든 명언 가져오기")
    void t4() {
        WiseSaying wiseSaying1 = new WiseSaying("content1", "author1");
        WiseSaying wiseSaying2 = new WiseSaying("content2", "author2");
        WiseSaying wiseSaying3 = new WiseSaying("content3", "author3");

        wiseSayingDbRepository.save(wiseSaying1);
        wiseSayingDbRepository.save(wiseSaying2);
        wiseSayingDbRepository.save(wiseSaying3);

        List<WiseSaying> wiseSayings = wiseSayingDbRepository.findAll().getContent();

        assertThat(wiseSayings).hasSize(3);
        assertThat(wiseSayings).contains(wiseSaying1, wiseSaying2, wiseSaying3);

    }
}
