package app.standard;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class FIleTest {
    @Test
    @DisplayName("최초의 파일 테스트")
    void t1() {
        Util.File.test();
    }

    @Test
    @DisplayName("빈 파일 생성")
    void t2() {

        String file = "test.txt";
        Util.File.createFile(file); // 파일 생성 ok

        assertThat(Files.exists(Paths.get(file)))
                .isTrue();

    }

    @Test
    @DisplayName("파일 내용 읽어오기")
    void t3() {
        String file = "test.txt";

        String testContent = "Hello World!";

        String content = Util.File.readAsString(file);

        assertThat(content).isEqualTo(testContent);
    }

    @Test
    @DisplayName("파일 내용 수정")
    void t4() {
        String file = "test.txt";

        Util.File.write(file, "modify content!");

        String content = Util.File.readAsString(file);

        assertThat(content).isEqualTo("modify content!");


    }

}