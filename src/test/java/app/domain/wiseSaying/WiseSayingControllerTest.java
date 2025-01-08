package app.domain.wiseSaying;

import app.domain.wiseSaying.repository.WiseSayingFileRepository;
import app.global.AppConfig;
import app.standard.TestBot;
import app.standard.Util;
import org.junit.jupiter.api.*;

import java.net.URI;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;

public class WiseSayingControllerTest {

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
    void t1() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    void t2() {
//        app.App app = new app.App();
//        app.run();
    }

    @Test
    @DisplayName("명령에 종료를 입력하면 종료")
    void t3() {
        TestBot testBot = new TestBot();
        String out = testBot.run("");

        assertThat(out)
                .contains("명령)")
                .contains("명언앱을 종료합니다");

    }

    @Test
    @DisplayName("명령을 여러번 입력할 수 있다.")
    void t4() {
        TestBot testBot = new TestBot();
        String out = testBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        // 명령) 횟수를 계산
        int cnt = out.split("명령\\)").length - 1;

        assertThat(cnt).isEqualTo(3);


    }

    @Test
    @DisplayName("앱 시작시 '== 명언 앱 ==' 출력")
    void t5() {
        TestBot testBot = new TestBot();
        String out = testBot.run("");


        assertThat(out)
                .containsSubsequence("== 명언 앱 ==", "명언앱을 종료합니다");


    }

    @Test
    @DisplayName("등록 - 명언 1개 입력")
    void t6() {
        TestBot testBot = new TestBot();
        String out = testBot.run("""
                등록
                현재를 사랑하라.
                작자 미상
                """);

        assertThat(out)
                .containsSubsequence("명언 : ", "작가 : ");


    }

    @Test
    @DisplayName("등록시 명언번호 출력")
    void t7() {
        TestBot testBot = new TestBot();
        String out = testBot.run("""
                등록
                현재를 사랑하라.
                작자 미상
                """);

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.");

    }

    @Test
    @DisplayName("등록할때마다 명언번호 증가 - 4단계")
    void t8() {
        TestBot testBot = new TestBot();
        String out = testBot.run("""
                등록
                현재를 사랑하라.
                작자 미상
                등록
                현재를 사랑하라.
                작자 미상
                """);

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");

    }

    @Test
    @DisplayName("목록 명령어 입력시 목록 출력 - 5단계")
    void t9() {
        TestBot testBot = new TestBot();
        String out = testBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .containsSubsequence("2 / 작자미상 / 과거에 집착하지 마라.",
                        "1 / 작자미상 / 현재를 사랑하라.");


    }

    @Test
    @DisplayName("id를 이용해서 명언을 삭제 - 6단계")
    void t10() {
        String out = TestBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                목록
                """);

        assertThat(out).contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");

    }

    @Test
    @DisplayName("명언 삭제에 대한 예외처리 - 7단계")
    void t11() {
        String out = TestBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                삭제?id=1
                """);

        assertThat(out)
                .contains("1번 명언은 존재하지 않습니다.");

    }

    @Test
    @DisplayName("명언 수정")
    void t12() {
        String out = TestBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=1
                새로운 명언
                새로운 작가
                목록
                """);

        assertThat(out)
                .contains("1 / 새로운 작가 / 새로운 명언")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");

    }

    @Test
    @DisplayName("빌드")
    void t13() {
        String out = TestBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                빌드
                """);

        boolean rst = Util.File.exists(WiseSayingFileRepository.getBuildPath());
        assertThat(rst).isTrue();

    }

    @Test
    @DisplayName("검색 - 검색 타입과 키워드를 입력받아 키워드를 포함하는 명언을 출력한다")
    void t14() {
        String out = TestBot.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                """);

        assertThat(out).contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");

    }

    @Test
    @DisplayName("목록 - 명언이 하나도 등록되지 않았을 때")
    void t15() {
        String out = TestBot.run("""
                목록
                """);

        assertThat(out).contains("등록된 명언이 없습니다.");

    }

    @Test
    @DisplayName("페이징 - 샘플데이터 생성")
    void t16() {

        TestBot.makeSample(10);
        String out = TestBot.run("""
                목록
                """);

        assertThat(out)
                .contains("1 / 작가1 / 명언1")
                .contains("10 / 작가10 / 명언10");
    }

    @Test
    @DisplayName("페이징 - 페이징 UI 생성")
    void t17() {

        TestBot.makeSample(10);
        String out = TestBot.run("""
                목록?page=2
                """);

        assertThat(out)
                .contains("1 / [2]");
    }

    @Test
    @DisplayName("페이징 - 페이징 UI 생성")
    void t18() {

        TestBot.makeSample(30);
        String out = TestBot.run("""
                목록?page=4
                """);

        assertThat(out)
                .contains("1 / 2 / 3 / [4] / 5 / 6");
    }

}
