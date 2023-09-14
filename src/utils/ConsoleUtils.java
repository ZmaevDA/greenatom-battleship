package utils;

import java.util.Scanner;

public class ConsoleUtils {
    public static String readLineFromConsole(String string) throws StringIndexOutOfBoundsException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(string);
        return scanner.nextLine();
    }

}
