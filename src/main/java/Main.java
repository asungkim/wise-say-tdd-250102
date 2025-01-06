import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        // System.out -> 표준 출력 -> 모니터

        PrintStream origin=System.out;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        System.out.println("hello");

        System.setOut(origin);

        String str = out.toString();
        System.out.println(str);
    }
}
