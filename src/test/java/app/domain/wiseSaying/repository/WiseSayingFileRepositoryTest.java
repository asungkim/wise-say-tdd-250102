package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.Page;
import app.domain.wiseSaying.WiseSaying;
import app.global.AppConfig;
import app.standard.Util;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class WiseSayingFileRepositoryTest {

    WiseSayingFileRepository wiseSayingRepository = new WiseSayingFileRepository();

    @BeforeAll
    static void beforeAll() {
        AppConfig.setTestMode();
    }

    @BeforeEach
    void beforeEach() {
        Util.File.deleteForce(AppConfig.getDbPath());
    }

    @AfterEach
    void afterEach() {
        Util.File.deleteForce(AppConfig.getDbPath());
    }

    @Test
    @DisplayName("명언 저장")
    void t1() {
        WiseSaying wiseSaying = new WiseSaying(1, "content", "author");
        wiseSayingRepository.save(wiseSaying);

        String filePath = WiseSayingFileRepository.getFilePath(wiseSaying.getId());

        // 파일 존재 여부
        boolean rst = Files.exists(Paths.get(filePath));
        assertThat(rst).isTrue();

        // 파일을 읽어서 원래 객체와 같은지 점검
        Map<String, Object> map = Util.Json.readAsMap(filePath);
        WiseSaying restoreWiseSaying = WiseSaying.fromMap(map);
        assertThat(restoreWiseSaying).isEqualTo(wiseSaying);

    }

    @Test
    @DisplayName("명언 삭제")
    void t2() {
        WiseSaying wiseSaying = new WiseSaying(1, "content", "author");
        wiseSayingRepository.save(wiseSaying);

        String filePath = WiseSayingFileRepository.getFilePath(wiseSaying.getId());

        boolean delRst = wiseSayingRepository.deleteById(1);
        boolean rst = Files.exists(Paths.get(filePath));

        // 삭제 완료
        assertThat(delRst).isTrue();
        // 파일 존재 x
        assertThat(rst).isFalse();

    }

    @Test
    @DisplayName("아이디로 해당 명언 가져오기")
    void t3() {
        WiseSaying wiseSaying = new WiseSaying(1, "content", "author");
        wiseSayingRepository.save(wiseSaying);

        String filePath = WiseSayingFileRepository.getFilePath(wiseSaying.getId());
        assertThat(Files.exists(Paths.get(filePath))).isTrue();

        Optional<WiseSaying> opWiseSaying = wiseSayingRepository.findById(1);

        WiseSaying findWiseSaying = opWiseSaying.orElse(null);

        // 찾아온 wiseSaying null이 아니고 저장한 wiseSaying 과 같은지 검사
        assertThat(findWiseSaying).isNotNull();
        assertThat(findWiseSaying).isEqualTo(wiseSaying);

    }

    @Test
    @DisplayName("모든 명언 가져오기")
    void t4() {
        WiseSaying wiseSaying1 = new WiseSaying(1, "content1", "author1");
        WiseSaying wiseSaying2 = new WiseSaying(2, "content2", "author2");
        WiseSaying wiseSaying3 = new WiseSaying(3, "content3", "author3");

        wiseSayingRepository.save(wiseSaying1);
        wiseSayingRepository.save(wiseSaying2);
        wiseSayingRepository.save(wiseSaying3);

        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll(5,1).getContent();

        assertThat(wiseSayings).hasSize(3);
        assertThat(wiseSayings).contains(wiseSaying1, wiseSaying2, wiseSaying3);

    }

    @Test
    @DisplayName("lastId 가져오기")
    void t5() {
        WiseSaying wiseSaying1 = new WiseSaying("content1", "author1");
        wiseSayingRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("content2", "author2");
        wiseSayingRepository.save(wiseSaying2);

        int lastId = wiseSayingRepository.getLastId();

        assertThat(lastId).isEqualTo(wiseSaying2.getId());
    }

    @Test
    @DisplayName("build 하면 명언들을 모아 하나의 파일로 저장")
    void t6() {
        WiseSaying wiseSaying1 = new WiseSaying("aaa", "aaa");
        wiseSayingRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("bbb", "bbb");
        wiseSayingRepository.save(wiseSaying2);

        wiseSayingRepository.build();

        String jsonStr = Util.File.readAsString(WiseSayingFileRepository.getBuildPath());

        assertThat(jsonStr)
                .isEqualTo("""
                        [
                        	{
                        		"id": 1,
                        		"content": "aaa",
                        		"author": "aaa"
                        	},
                        	{
                        		"id": 2,
                        		"content": "bbb",
                        		"author": "bbb"
                        	}
                        ]
                        """.stripIndent().trim());
    }


    @Test
    @DisplayName("현재 저장된 명언의 개수를 가져오는 count")
    void t7() {
        WiseSaying wiseSaying1 = new WiseSaying("content1", "author1");
        wiseSayingRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("content2", "author2");
        wiseSayingRepository.save(wiseSaying2);

        int cnt = wiseSayingRepository.count();

        assertThat(cnt).isEqualTo(2);
    }

    @Test
    @DisplayName("페이지 정보와 결과 가져오기")
    void t8() {
        WiseSaying wiseSaying1 = new WiseSaying("content1", "author1");
        wiseSayingRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("content2", "author2");
        wiseSayingRepository.save(wiseSaying2);

        WiseSaying wiseSaying3 = new WiseSaying("content3", "author3");
        wiseSayingRepository.save(wiseSaying3);

//        [List<WiseSaying> wiseSayings, totalItems, totalPages, page]=wiseSayingRepository.findAll();

        Page pageContent = wiseSayingRepository.findAll(5,1);

        List<WiseSaying> wiseSayings = pageContent.getContent();
        int totalItems = pageContent.getTotalItems();
        int totalPages=pageContent.getTotalPages();
//        int page = pageContent.getPage();

        assertThat(totalItems).isEqualTo(3);
        assertThat(totalPages).isEqualTo(1);

    }


}