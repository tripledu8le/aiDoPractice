Я зроблю це як цілісну навчальну серію + міні-проєкт:

Files.walk() і аналіз структури

підрахунок, фільтрація, розміри

власний файловий логер для всіх операцій

Формат:
👉 коротка теорія → завдання → рішення → нюанси з життя.

🧠 Теорія: Files.walk()
Files.walk(Path start)
повертає Stream<Path>

обходить рекурсивно

обов’язково закривати (try-with-resources)

може обмежуватись глибиною:

Files.walk(start, maxDepth)
📌 Files.walk() ≠ Files.list()

list() — тільки 1 рівень

walk() — дерево

📂 Серія завдань: Аналіз структури папок
🟢 Завдання 1 — Обійти дерево папок
Умова:
Виведи всі файли і папки з data/.

✅ Рішення
Path root = Paths.get("data");

try (var stream = Files.walk(root)) {
stream.forEach(System.out::println);
}
🟢 Завдання 2 — Порахувати файли і папки
Умова:
Порахуй окремо файли і директорії.

✅ Рішення
long files;
long dirs;

try (var stream = Files.walk(root)) {
files = stream.filter(Files::isRegularFile).count();
}

try (var stream = Files.walk(root)) {
dirs = stream.filter(Files::isDirectory).count();
}

System.out.println("Files: " + files);
System.out.println("Dirs: " + dirs);
📎 Нюанс: stream одноразовий.

🟡 Завдання 3 — Загальний розмір папки
Умова:
Порахуй сумарний розмір усіх файлів.

✅ Рішення
long totalSize;

try (var stream = Files.walk(root)) {
totalSize = stream
.filter(Files::isRegularFile)
.mapToLong(p -> {
try {
return Files.size(p);
} catch (Exception e) {
return 0L;
}
})
.sum();
}

System.out.println("Total bytes: " + totalSize);
🟡 Завдання 4 — Фільтр за розширенням
Умова:
Знайди всі .txt файли.

✅ Рішення
try (var stream = Files.walk(root)) {
stream
.filter(Files::isRegularFile)
.filter(p -> p.toString().endsWith(".txt"))
.forEach(System.out::println);
}
🟡 Завдання 5 — Найбільший файл
Умова:
Знайди файл з найбільшим розміром.

✅ Рішення
Path biggest;

try (var stream = Files.walk(root)) {
biggest = stream
.filter(Files::isRegularFile)
.max(Comparator.comparingLong(p -> {
try {
return Files.size(p);
} catch (Exception e) {
return 0L;
}
}))
.orElse(null);
}

System.out.println("Biggest: " + biggest);
📝 Частина 2 — Логер файлових операцій
🎯 Ідея
Кожна операція:

COPY

MOVE

DELETE

WALK

👉 лог:

2025-01-12 14:32:11 | COPY | data/a.txt -> backup/a.txt | OK
🧱 Клас FileLogger
import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    private static final Path LOG_FILE = Paths.get("logs/file.log");
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            Files.createDirectories(LOG_FILE.getParent());
            if (Files.notExists(LOG_FILE)) {
                Files.createFile(LOG_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void log(String operation, String message, boolean success) {
        String record = String.format(
                "%s | %s | %s | %s%n",
                LocalDateTime.now().format(FORMATTER),
                operation,
                message,
                success ? "OK" : "FAIL"
        );

        try {
            Files.write(
                    LOG_FILE,
                    record.getBytes(),
                    StandardOpenOption.APPEND
            );
        } catch (IOException ignored) {}
    }
}
🟢 Завдання 6 — Логувати копіювання
try {
Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
FileLogger.log("COPY", src + " -> " + dst, true);
} catch (Exception e) {
FileLogger.log("COPY", src + " -> " + dst, false);
}
🟡 Завдання 7 — Логувати walk
try (var stream = Files.walk(root)) {
stream.forEach(p ->
FileLogger.log("WALK", p.toString(), true)
);
}
🔴 Завдання 8 — Аналіз + лог
Умова:
Залогуй:

кількість файлів

загальний розмір

✅ Рішення
FileLogger.log(
"ANALYZE",
"Files=" + files + ", Size=" + totalSize,
true
);
🔴 Завдання 9 — Обробка помилок доступу
Умова:
Не падати, якщо немає доступу.

✅ Рішення
.filter(p -> {
try {
return Files.isReadable(p);
} catch (Exception e) {
FileLogger.log("READ_CHECK", p.toString(), false);
return false;
}
})
🔴 Завдання 10 — Міні-утиліта Analyzer
Умова:
Зроби метод:

analyze(Path root)
Який:

обходить дерево

рахує файли

розмір

логерує результат

🎯 Що ти реально вивчив
✔ Files.walk()
✔ аналіз файлових систем
✔ Stream + IO
✔ власний логер
✔ основу для системних утиліт

