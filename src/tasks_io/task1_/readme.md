🧠 Коротка теорія Java IO (мінімум, але по суті)
Основні класи, які нам потрібні:
File — представляє файл або папку

FileWriter / FileReader — запис / читання символів

BufferedWriter / BufferedReader — швидка робота з текстом

try-with-resources — обов’язково, щоб файли закривались автоматично

IOException — базова помилка для IO

📌 Золоте правило:

Працюєш з файлами → завжди try-with-resources.

📁 Завдання 1 — Створити файл
Умова:
Створи файл example.txt у папці data.

💡 Підказка
File file = new File("data/example.txt");

file.createNewFile()

✅ Рішення
import java.io.File;
import java.io.IOException;

public class Task01 {
public static void main(String[] args) {
File file = new File("data/example.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("Файл створено");
            } else {
                System.out.println("Файл вже існує");
            }
        } catch (IOException e) {
            System.out.println("Помилка створення файлу: " + e.getMessage());
        }
    }
}


📎 Нюанс: якщо папки data нема — буде помилка❗

📁 Завдання 2 — Створити файл разом з папкою
Умова:
Якщо папки нема — створи її автоматично.

💡 Підказка
file.getParentFile().mkdirs()

✅ Рішення
public void practice2 () {
File file = new File("data/example.txt");

    try {
        File parent = file.getParentFile();

        if (!parent.exists()) {
            parent.mkdirs();
            System.out.println("folder created");
        }

        if (file.createNewFile()) {
            System.out.println("file created");
        } else {
            System.out.println("file already exists");
        }

    } catch (IOException e) {
        System.out.println("unable to create file: " + e.getMessage());
    }
}

✍️ Завдання 3 — Записати текст у файл
Умова:
Запиши "Hello Java IO" у файл.

💡 Підказка
FileWriter

try-with-resources

✅ Рішення
import java.io.FileWriter;
import java.io.IOException;

public class Task03 {
public static void main(String[] args) {
try (FileWriter writer = new FileWriter("data/example.txt")) {
writer.write("Hello Java IO");
} catch (IOException e) {
e.printStackTrace();
}
}
}
⚠️ Важливо:
FileWriter перезаписує файл, якщо він існує.

## ➕ Завдання 4 — Додати текст у кінець файлу
Умова:
Додай новий рядок "Second line".

💡 Підказка
new FileWriter(path, true)

✅ Рішення
try (FileWriter writer = new FileWriter("data/example.txt", true)) {
writer.write("\nSecond line");
}


## 📖 Завдання 5 — Прочитати файл построчно
Умова:
Виведи всі рядки файлу в консоль.

💡 Підказка
BufferedReader

readLine()

✅ Рішення
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task05 {
public static void main(String[] args) {
try (BufferedReader reader =
new BufferedReader(new FileReader("data/example.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
## 🔢 Завдання 6 — Записати числа у файл
Умова:
Запиши числа від 1 до 5, кожне з нового рядка.

✅ Рішення

    public void practice6 () {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter ("data/numbers.txt"))) {
            for (int i = 1; i>=5; i++) {
                writer.write(String.valueOf(i));
                writer.newLine();
            }} catch (IOException e) {
                e.printStackTrace();
            }
        }

## ➕ Завдання 7 — Прочитати числа і порахувати суму
Умова:
Прочитай numbers.txt і порахуй суму чисел.

💡 Підказка
Integer.parseInt(line)

✅ Рішення
int sum = 0;

try (BufferedReader reader =
new BufferedReader(new FileReader("data/numbers.txt"))) {

    String line;
    while ((line = reader.readLine()) != null) {
        sum += Integer.parseInt(line);
    }

} catch (IOException e) {
e.printStackTrace();
}

System.out.println("Сума: " + sum);
⚠️ Проблема: якщо в файлі буде не число → NumberFormatException.

🛡️ Завдання 8 — Захист від неправильних даних
Умова:
Ігноруй рядки, які не є числами.

✅ Рішення
try (BufferedReader reader =
new BufferedReader(new FileReader("data/numbers.txt"))) {

    String line;
    int sum = 0;

    while ((line = reader.readLine()) != null) {
        try {
            sum += Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Не число: " + line);
        }
    }

    System.out.println("Сума: " + sum);

}
🔄 Завдання 9 — Конвертація даних
Умова:
Прочитай файл, переведи всі рядки у верхній регістр і запиши в інший файл.

✅ Рішення
try (
BufferedReader reader = new BufferedReader(new FileReader("data/example.txt"));
BufferedWriter writer = new BufferedWriter(new FileWriter("data/output.txt"))
) {
String line;
while ((line = reader.readLine()) != null) {
writer.write(line.toUpperCase());
writer.newLine();
}
}
🧹 Завдання 10 — Перевірка існування файлу перед читанням
Умова:
Не читати файл, якщо його не існує.

✅ Рішення
File file = new File("data/example.txt");

if (!file.exists()) {
System.out.println("Файл не знайдено");
return;
}
⚠️ Типові проблеми, про які треба знати
❌ Забув закрити файл → витік ресурсів
✅ try-with-resources

❌ Нема папки → FileNotFoundException
✅ mkdirs()

❌ Перезапис замість дописування
✅ new FileWriter(path, true)

❌ Криві дані в файлі
✅ try/catch NumberFormatException

🎯 Що це реально тренує
роботу з файловою системою

уважність до помилок

підготовку до логування, конфігів, CSV, TXT

базу для NIO, Files, Path