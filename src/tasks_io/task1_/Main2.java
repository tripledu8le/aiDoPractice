package tasks_io.task1_;

import java.io.File;
import java.io.IOException;

public class Main2 {
    public void practice1() {
        File file = new File("data/example.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("file created");
            } else {
                System.out.println("file already exists");
            }
        } catch (IOException e) {
            System.out.println("error creating file, folder doesn't exists");
        }
    }

    public void practice2 () {
        File file = new File("data/example.txt");
        try {
            if (file.getParentFile().mkdirs()) {
                System.out.println("directory created");
            } else {
                System.out.println("directory already exists");
            }

            if (file.createNewFile()) {
                System.out.println("file created");
            } else {
                System.out.println("file already exists");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }



}
