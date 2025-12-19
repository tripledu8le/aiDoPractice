CSV Analyzer — це вже повноцінний міні-проєкт, який:

виглядає серйозно,

реально трапляється в роботі,

ідеально поєднує Files + Streams + backup + логер.

Я зроблю це як навчальний проєкт рівня strong junior:
структура → завдання → готові рішення → нюанси, які важливі.

🎯 Мета CSV Analyzer
Маємо CSV-файл, наприклад:

id,name,age,salary
1,John,30,1200
2,Alice,25,1500
3,Bob,not_number,900
4,Tom,40,2000
Analyzer повинен:

зробити backup CSV

прочитати файл

пропустити некоректні рядки

проаналізувати дані

зберегти результат у новий файл

залогувати всі дії

🧱 Структура проєкту
csv-analyzer/
├─ data/
│   └─ input.csv
├─ backup/
├─ logs/
│   └─ file.log
└─ src/
├─ CsvAnalyzer.java
├─ CsvParser.java
├─ FileLogger.java
└─ CsvStats.java
🧠 Теорія (мінімум)
CSV = text + delimiter

Files.lines() → стрім рядків

String.split(",") → простий парсер

backup до обробки

логер — не println

🟢 Крок 1 — Backup CSV
Path source = Paths.get("data/input.csv");
Path backupDir = Paths.get("backup");

Files.createDirectories(backupDir);

Path backup = backupDir.resolve(
source.getFileName() + ".bak"
);

Files.copy(
source,
backup,
StandardCopyOption.REPLACE_EXISTING
);

FileLogger.log("BACKUP", source + " -> " + backup, true);
🟢 Крок 2 — CsvParser
public class CsvParser {

    public static String[] parseLine(String line) {
        return line.split(",");
    }
}
📎 Пояснення:
Так, це не ідеальний CSV-парсер, але для навчання — правильно.

🟡 Крок 3 — Модель статистики
public class CsvStats {

    int validRows;
    int invalidRows;
    int totalSalary;

    @Override
    public String toString() {
        return """
               validRows=%d
               invalidRows=%d
               totalSalary=%d
               """.formatted(validRows, invalidRows, totalSalary);
    }
}
🟡 Крок 4 — Аналіз CSV
public class CsvAnalyzer {

    public static CsvStats analyze(Path csv) throws IOException {

        CsvStats stats = new CsvStats();

        try (var lines = Files.lines(csv)) {
            lines
                .skip(1) // header
                .forEach(line -> {
                    String[] parts = CsvParser.parseLine(line);

                    try {
                        Integer.parseInt(parts[0]); // id
                        Integer.parseInt(parts[2]); // age
                        int salary = Integer.parseInt(parts[3]);

                        stats.validRows++;
                        stats.totalSalary += salary;

                    } catch (Exception e) {
                        stats.invalidRows++;
                        FileLogger.log("INVALID_ROW", line, false);
                    }
                });
        }

        return stats;
    }
}
📎 Нюанс:
Ми спеціально ловимо Exception локально, щоб не ламати весь файл.

🟢 Крок 5 — Збереження результату
CsvStats stats = CsvAnalyzer.analyze(source);

Path result = Paths.get("data/result.txt");

Files.write(
result,
stats.toString().getBytes()
);

FileLogger.log("WRITE_RESULT", result.toString(), true);
📝 FileLogger (коротко, як раніше)
public class FileLogger {

    private static final Path LOG = Paths.get("logs/file.log");

    static {
        try {
            Files.createDirectories(LOG.getParent());
            if (Files.notExists(LOG)) Files.createFile(LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void log(String op, String msg, boolean ok) {
        String line = LocalDateTime.now()
                + " | " + op + " | " + msg + " | " + (ok ? "OK" : "FAIL") + "\n";
        try {
            Files.write(LOG, line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ignored) {}
    }
}
📄 Результат result.txt
validRows=3
invalidRows=1
totalSalary=4700
⚠️ Дуже важливі нюанси (це плюс на співбесіді)
✔ backup до аналізу
✔ stream закривається
✔ помилка одного рядка ≠ падіння програми
✔ лог ≠ System.out
✔ CSV — це дані з проблемами, а не ідеальний формат

🚀 Як можна ускладнити (і це вже middle)
1️⃣ середня зарплата
2️⃣ топ-N зарплат
3️⃣ сортування
4️⃣ вивід у CSV
5️⃣ Files.walk() → обробка всіх CSV у папці
6️⃣ parallel() для великих файлів

🔥 Моя рекомендація
Наступним кроком зробити:

👉 CSV Analyzer v2

обробка папки CSV

backup кожного

зведений звіт

один лог

Якщо хочеш — я можу:

дати завдання без рішень (як тренування),

або оформити це як GitHub-проєкт з README,

або ускладнити до рівня middle.