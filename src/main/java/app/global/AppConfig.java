package app.global;

public class AppConfig {
    private static String mode;

    private static String dbMode;

    // static 계열 생성자
    static {
        setDevMode();
        setFileDbMode();
    }

    public static void setProdMode() {
        mode = "prod";
    }

    public static void setDevMode() {
        mode = "dev";
    }

    public static void setTestMode() {
        mode = "test";
    }

    public static boolean isProd() {
        return mode.equals("prod");
    }

    public static boolean isDev() {
        return mode.equals("dev");
    }

    public static boolean isTest() {
        return mode.equals("test");
    }

    public static void setFileDbMode() {
        dbMode = "file";
    }

    public static void setMemDbMode() {
        dbMode = "mode";
    }

    public static boolean isFileDb() {
        return dbMode.equals("file");
    }

    public static boolean isMemDb() {
        return dbMode.equals("mode");
    }


    public static String getDbPath() {
        return "db/" + mode;
    }
}
