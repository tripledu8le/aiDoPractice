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

      public void practice3 (int age) throws Exception{
          if (age <1 || age > 120) {
              throw new Exception ("Incorrect age" + age);
          }
          System.out.println("set age " + age);
      }

      public void practice4 (int [] arr, int index) {
          if (index <0 || arr.length > index) {
              throw new ArrayIndexOutOfBoundsException("array is shorted than " + index);
          }
          System.out.println(arr[index]);
      }

      public void practice5 (String password) throws InvalidPasswordException {
          if (password == null || password.length() <6) {
              throw new InvalidPasswordException("Password is short");
          }
          System.out.println("Password is axcepted");
      }


}

class InvalidPasswordException extends Exception {
    public InvalidPasswordException (String msg) {
        super(msg);
    }
}