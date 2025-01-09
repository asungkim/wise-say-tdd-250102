package app.domain.wiseSaying;

import app.domain.wiseSaying.repository.Page;
import app.global.Command;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingService wiseSayingService;
    private int itemsPerPage;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
        wiseSayingService = new WiseSayingService();
        itemsPerPage = 5;
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();


        WiseSaying wiseSaying = wiseSayingService.write(content, author);

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    public void actionPrint(Command command) {

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        int page = command.getParamAsInt("page", 1);

        Page pageContent = wiseSayingService.getAllItems(itemsPerPage);
        List<WiseSaying> wiseSayingList;


        if (command.isSearchCommand()) {

            String kType = command.getParam("keywordType");
            String kw = command.getParam("keyword");

            wiseSayingList = wiseSayingService.search(kType, kw,itemsPerPage);
        } else {
            wiseSayingList = wiseSayingService.getAllItems(itemsPerPage).getWiseSayings();

        }

        if (wiseSayingList.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
            System.out.printf("%d / %s / %s\n", wiseSayingList.get(i).getId(), wiseSayingList.get(i).getAuthor(), wiseSayingList.get(i).getContent());
        }

        // 페이징
        printPage(page, pageContent.getTotalPages());

    }

    private void printPage(int page, int totalPages) {

        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                System.out.print("[%d]".formatted(i));
            } else {
                System.out.print("%d".formatted(i));
            }
            if (i == totalPages) {
                System.out.println();
                break;
            }
            System.out.print(" / ");
        }
    }

    public void actionDelete(Command cmd) {
        int id = cmd.getParamAsInt("id", -1);

        boolean result = wiseSayingService.delete(id);
        if (result) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    public void actionModify(Command cmd) {
        int id = cmd.getParamAsInt("id", -1);

        Optional<WiseSaying> opWiseSaying = wiseSayingService.getItem(id);

        WiseSaying wiseSaying = opWiseSaying.orElse(null);
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
        System.out.println("명언 : ");
        String newContent = sc.nextLine();

        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.println("작가 : ");
        String newAuthor = sc.nextLine();

        wiseSayingService.modify(wiseSaying, newContent, newAuthor);
        System.out.println("%d번 명언이 수정되었습니다.".formatted(id));


    }

    public void actionBuild() {
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void makeSampleData(int cnt) {
        wiseSayingService.makeSampleData(cnt);
        System.out.println("샘플 데이터가 생성되었습니다.");
    }
}
