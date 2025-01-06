package app.domain.wiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
        wiseSayingService = new WiseSayingService();
    }

    public void actionWrite() {
        System.out.println("명언 : ");
        String content = sc.nextLine();
        System.out.println("작가 : ");
        String author = sc.nextLine();


        WiseSaying wiseSaying = wiseSayingService.write(content, author);

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    public void actionPrint() {
        System.out.println("""
                번호 / 작가 / 명언
                ----------------------
                """);

        List<WiseSaying> wiseSayingList = wiseSayingService.getAllItems();

        for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
            System.out.printf("%d / %s / %s\n", wiseSayingList.get(i).getId(), wiseSayingList.get(i).getAuthor(), wiseSayingList.get(i).getContent());
        }
    }

    public void actionDelete(String cmd) {
        String data = cmd.split("\\?")[1];
        int id = Integer.parseInt(data.split("=")[1]);

        boolean result = wiseSayingService.delete(id);
        if (result) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }
}
