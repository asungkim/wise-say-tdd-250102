package app.global;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        Assertions.assertThat(actionName).isEqualTo("목록");
    }
}
