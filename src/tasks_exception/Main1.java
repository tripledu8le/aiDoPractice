package tasks_exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main1 {
    public static int practice1(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        return a / b;
    }

    public void practice2(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        System.out.println(text.length());
    }

    public void practice3(int age) throws Exception {
        if (age < 1 || age > 120) {
            throw new Exception("Incorrect age" + age);
        }
        System.out.println("set age " + age);
    }

    public void practice4(int[] arr, int index) {
        if (index < 0 || arr.length > index) {
            throw new ArrayIndexOutOfBoundsException("array is shorted than " + index);
        }
        System.out.println(arr[index]);
    }

    public void practice5(String password) throws InvalidPasswordException {
        if (password == null || password.length() < 6) {
            throw new InvalidPasswordException("Password is short");
        }
        System.out.println("Password is axcepted");
    }

    public String practice6(String filePath) {
        try (BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(filePath)))) {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("Error reading file " + e.getMessage());
            return null;
        }
    }

    public int practice7(String a, String b) {
        try {
            int x = Integer.parseInt(a);
            int y = Integer.parseInt(b);
            return x / y;
        } catch (NumberFormatException | ArithmeticException e) {
            System.out.println("Has an exception " + e.getMessage());
            return 0;
        }
    }


    public static String practice8 (String file) throws IOException{
            return new String(Files.readAllBytes(Paths.get(file)));
    } // цей нижчий


    public static void run () {

        try {
            String data = practice8("file.txt");
            System.out.println(data);
        } catch (IOException e) {
            System.out.println("ioexception throws" + e.getMessage());
        }
    } // цей вищий, викликає practice


}
class InvalidPasswordException extends Exception {
    public InvalidPasswordException (String msg) {
        super(msg);
    }
}