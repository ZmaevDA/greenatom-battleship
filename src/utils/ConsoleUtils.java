package utils;

import exception.InvalidCellSizeException;
import exception.InvalidPointsException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUtils {
    public static String readCellFromConsole(String string) throws InvalidCellSizeException, InvalidPointsException {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("[a-j]10|[a-j][1-9]");
        System.out.println(string);
        String input = scanner.nextLine();
        Matcher matcher = pattern.matcher(input);
        if (input.length() < 2 || input.length() > 3) {
            throw new InvalidCellSizeException();
        }
        if (!matcher.matches()) {
            throw new InvalidPointsException();
        }
        return input;
    }

    public static String readNameFromConsole(String string) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(string);
        return scanner.nextLine();
    }

}
