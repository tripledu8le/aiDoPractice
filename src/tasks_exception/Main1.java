package tasks_exception;

public class Main1 {
      public static int practice1 (int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Divide by zero");
        }
          return a/b;
      }


}
