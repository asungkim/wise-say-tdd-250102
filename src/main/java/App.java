import domain.wiseSaying.WiseSaying;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {

    private final Scanner sc;
    private int lastId;
    private List<WiseSaying> wiseSayingList;

    public App(Scanner sc) {
        this.sc = sc;
        this.lastId = 0;
        this.wiseSayingList = new ArrayList<>();
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.println("명령) ");
            String cmd = sc.nextLine();

            if (cmd.equals("종료")) {
                System.out.println("명언앱을 종료합니다.");
                break;
            } else if (cmd.equals("등록")) {


            } else if (cmd.equals("목록")) {
                System.out.println("""
                        번호 / 작가 / 명언
                        ----------------------
                        """);

                for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
                    System.out.printf("%d / %s / %s\n", wiseSayingList.get(i).getId(), wiseSayingList.get(i).getAuthor(), wiseSayingList.get(i).getContent());
                }
            }
        }


    }
}
