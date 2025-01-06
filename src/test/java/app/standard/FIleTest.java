package app.standard;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        String fileName="test.txt";
        Path filePath= Paths.get(fileName);

        Util.File.createFile(); // 파일 생성 ok

        Assertions.assertThat(Files.exists(filePath)).isTrue();


    }

}