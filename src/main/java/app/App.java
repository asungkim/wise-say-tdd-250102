package app;

import app.domain.wiseSaying.SystemController;
import app.domain.wiseSaying.WiseSayingController;
import app.global.Command;

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
            System.out.print("명령) ");
            String cmd=sc.nextLine();

            Command command=new Command(cmd);
            String actionName = command.getActionName();

            switch (actionName) {
                case "종료":
                    systemController.exit();
                    break;
                case "등록":
                    wiseSayingController.actionWrite();
                    break;
                case "목록":
                    wiseSayingController.actionPrint(command);
                    break;
                case "삭제":
                    wiseSayingController.actionDelete(command);
                    break;
                case "수정":
                    wiseSayingController.actionModify(command);
                    break;
                case "빌드":
                    wiseSayingController.actionBuild();
                    break;
                default:
                    System.out.println("올바른 명령이 아닙니다.");
            }

            if (cmd.equals("종료")) {
                break;
            }
        }


    }

    public void makeSampleData(int cnt) {
        wiseSayingController.makeSampleData(cnt);
    }
}
