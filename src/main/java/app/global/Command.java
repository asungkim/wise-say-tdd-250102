package app.global;

public class Command {

    String actionName;
    String paramKey;
    String paramValue;

    public Command(String cmd) {

        // actionName?key=value

        String[] cmdBits = cmd.split("\\?");
        actionName = cmdBits[0];

        if (cmdBits.length < 2) {
            paramValue = "";
            return;
        }

        String params = cmdBits[1];

        // 목록?expr=1=1


        String[] paramBits = params.split("=");
        paramKey = paramBits[0];
        paramValue = paramBits[1];

    }


    public String getActionName() {
        return actionName;
    }

    public int getParams() {
        return Integer.parseInt(paramValue);
    }
}
