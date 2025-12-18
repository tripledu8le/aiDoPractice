package tasks_exception;

public class Main1 {
      public static int practice1 (int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Divide by zero");
        }
          return a/b;
      }

      public void practice2 (String text) {
          if (text == null) {
              throw new IllegalArgumentException("String cannot be null");
          }
          System.out.println(text.length());
      }



}
