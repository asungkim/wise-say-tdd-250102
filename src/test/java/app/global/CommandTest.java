package app.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CommandTest {
    @Test
    @DisplayName("command Test 최초 테스트")
    void t1() {
        Command cmd = new Command("삭제?id=1");
    }

    @Test
    @DisplayName("actionName 을 얻어올 수 있다")
    void t2() {
        Command cmd = new Command("목록?id=1");

        String actionName = cmd.getActionName();

        assertThat(actionName).isEqualTo("목록");
    }

    @Test
    @DisplayName("입력 형태가 '삭제' ? 가 없으면 잘 나오는지 확인")
    void t3() {
        Command cmd = new Command("목록");

        String actionName = cmd.getActionName();

        assertThat(actionName).isEqualTo("목록");
    }

    @Test
    @DisplayName("불완전한 입력이 들어왔을 때, 삭제?1, 삭제?id?1")
    void t4() {

        Command cmd = new Command("삭제");
        String actionName = cmd.getActionName();

        assertThat(actionName).isEqualTo("삭제");

    }

    @Test
    @DisplayName("paramValue 를 가져올 수 있다")
    void t5() {
        Command cmd = new Command("목록?id=1");

        int paramValue = cmd.getParams();

        assertThat(paramValue).isEqualTo(1);

    }

    @Test
    @DisplayName("불완전한 입력값이 들어올 때, 입력값1 - 목록?expr=1=1, 입력값2 - 목록?page, 삭제?id=aa ")
    void t6() {
        Command cmd1 = new Command("목록?expr=1=1");
        int param1 = cmd1.getParams();

        Command cmd2 = new Command("목록?page");
        int param2 = cmd1.getParams();

        Command cmd3 = new Command("삭제?id=aa");
        int param3 = cmd1.getParams();


        assertThat(param1).isEqualTo("1=1");
        assertThat(param2).isNull();
        assertThat(param3).isEqualTo("aa"); // 예외처리

    }

}
