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
        App app = new App();
        app.run();
    }

    @Test
    void t3() {
        TestBot testBot = new TestBot();
        String out = testBot.run("종료");

        assertThat(out).contains("명언앱을 종료합니다");


    }

    @Test
    @DisplayName("앱 시작시 '== 명언 앱 ==' 출력")
    void t4() {
        TestBot testBot = new TestBot();
        String out = testBot.run("종료");


        assertThat(out)
                .containsSubsequence("== 명언 앱 ==", "명언앱을 종료합니다");


    }

    @Test
    @DisplayName("등록 - 명언 1개 입력")
    void t5() {
        TestBot testBot = new TestBot();
        String out = testBot.run("등록\n현재를 사랑하라\n작자 미상\n종료");

        assertThat(out)
                .containsSubsequence("명언 : ","작가 : ");


    }
}
