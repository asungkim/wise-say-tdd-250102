package app.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CommandTest {
    @Test
    @DisplayName("command Test 최초 테스트")
    void t1() {
        Command cmd=new Command("삭제?id=1");
    }

    @Test
    @DisplayName("actionName 을 얻어올 수 있다")
    void t2() {
        Command cmd=new Command("목록?id=1");

        String actionName=cmd.getActionName();

        assertThat(actionName).isEqualTo("목록");
    }

    @Test
    @DisplayName("입력 형태가 '삭제' ? 가 없으면 잘 나오는지 확인")
    void t3() {
        Command cmd=new Command("목록");

        String actionName=cmd.getActionName();

        assertThat(actionName).isEqualTo("목록");
    }

    @Test
    @DisplayName("paramValue 를 가져올 수 있다")
    void t4() {
        Command cmd=new Command("목록?id=1");

        int paramValue=cmd.getParams();

        assertThat(paramValue).isEqualTo(1);

    }

}
