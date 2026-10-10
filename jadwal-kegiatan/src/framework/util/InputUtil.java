package framework.util;

import java.util.Scanner;

/**
 * Utility untuk membaca input string dari keyboard.
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String input(String info) {
        System.out.print(info + " : ");
        if (!scanner.hasNextLine()) {
            return "x";
        }
        return scanner.nextLine();
    }
}
