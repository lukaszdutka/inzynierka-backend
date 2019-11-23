package pl.lukaszdutka.helpers;

public class ColorHelper {

    private static final int BOUNDARY = 16777259;

    public static String getColorOf(String string) {
        int x = 13;
        for (int i : string.toCharArray()) {
            x *= i;
            x = x % BOUNDARY;
        }
        String color = "#";
        color += Integer.toHexString(boundInteger(x));

        x /= 256;
        color += Integer.toHexString(boundInteger(x));

        x /= 256;
        color += Integer.toHexString(boundInteger(x));

        return color;
    }

    // 51 - 221 is valid
    private static int boundInteger(int x) {
        x = x % 256;
        // 221-51
        x *= (221 - 51);
        x /= 256;
        x += 51;

        return x;
    }
}
