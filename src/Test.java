import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("[a-j][1-9]|10");

        String userInput;
        Matcher matcher;

        do {
            System.out.println("Введите строку вида a1 (Где a - буква от a до j, а 1 - цифра от 1 до 10):");
            userInput = scanner.nextLine();
            matcher = pattern.matcher(userInput);
        } while (!matcher.matches());

        System.out.println("Строка введена верно!");
    }
}
