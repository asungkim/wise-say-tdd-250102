import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FirstTest {


    @Test
    void t1() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    void t2() {
//        App app = new App();
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
}
