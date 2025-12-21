package tasks_io.task1_;

import java.io.*;

public class Main1 {
    public void practice1() {
        File file = new File("data/example.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Error creating file " + e.getMessage());
        }
    }

    public void practice2 () {
        File file = new File("data/example.txt");

        try {
            if(file.getParentFile().mkdirs() &&
            file.createNewFile()) {
                System.out.println("folder and file created");
            } else {
                System.out.println("file is exists");
            }
        } catch (IOException e) {
            System.out.println("unable to create file, " + e.getMessage());
        }
    }

    public void practice3 () {
        try (FileWriter fileWriter = new FileWriter("data/example.txt")) {
            fileWriter.write("Hello JAVA IO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void practice4 () {
        try (FileWriter fileWriter = new FileWriter("data/example.txt", true)) {
            fileWriter.write("\nSecond line");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void practice5 () {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/example.txt"))) {
            String line;
            while ((line = reader.readLine())!= null) {
            System.out.println(line);
        }} catch (IOException e) {
            e.printStackTrace();
        }
    }



}
