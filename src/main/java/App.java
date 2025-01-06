import domain.wiseSaying.SystemController;
import domain.wiseSaying.WiseSaying;
import domain.wiseSaying.WiseSayingController;
import java.util.Scanner;

public class App {
    private final Scanner sc;
    private final WiseSayingController wiseSayingController;
    private final SystemController systemController;

    public App(Scanner sc) {
        this.sc = sc;
        this.wiseSayingController=new WiseSayingController(sc);
        this.systemController=new SystemController();
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        label:
        while (true) {
            System.out.println("명령) ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "종료":
                    systemController.exit();
                    return;
                case "등록":
                    wiseSayingController.actionWrite();
                    break;
                case "목록":
                    wiseSayingController.actionPrint();
                    break;
            }
        }


    }
}
