package revisao.api.crudrevisao.utils;

public class StringUtils {
    public static String convertPath(String string) {
        return string.split("[=;]")[1];
    }
}
