import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

fun main() {

    // 1. Основы LocalDate и LocalTime
    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    println("Дата: ${currentDate.format(formatter)} Время: ${currentTime.format(formatter)}")

    // 2. Сравнение дат
    fun compareDates(date1: LocalDate, date2: LocalDate): String {
        return when {
            date1.isBefore(date2) -> "Первая дата меньше второй"
            date1.isAfter(date2) -> "Первая дата больше второй"
            else -> "Даты равны"
        }
    }
    println(compareDates(LocalDate.of(2023, 10, 30), LocalDate.of(2025, 1, 1)))

    // 3. Сколько дней до Нового года?
    fun daysUntilNewYear(): Long {
        val today = LocalDate.now()
        val newYear = LocalDate.of(today.year + 1, 1, 1)
        return ChronoUnit.DAYS.between(today, newYear)
    }
    println("Дней до Нового года: ${daysUntilNewYear()}")

    // 4. Проверка високосного года
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
    println(isLeapYear(2024))

    // 5. Подсчет выходных за месяц
    fun countWeekendsInMonth(year: Int, month: Month): Int {
        var weekendsCount = 0
        val firstDayOfMonth = LocalDate.of(year, month, 1)
        val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())
        var currentDate = firstDayOfMonth
        while (!currentDate.isAfter(lastDayOfMonth)) {
            if (currentDate.dayOfWeek == DayOfWeek.SATURDAY || currentDate.dayOfWeek == DayOfWeek.SUNDAY) {
                weekendsCount++
            }
            currentDate = currentDate.plusDays(1)
        }
        return weekendsCount
    }
    println("Выходных в октябре 2023: ${countWeekendsInMonth(2023, Month.OCTOBER)}")

    // 6. Расчет времени выполнения метода
    fun measureExecutionTime() {
        val start = System.nanoTime()
        for (i in 1..1_000_000) {}  // Псевдо-операции для цикла
        val end = System.nanoTime()
        println("Время выполнения: ${(end - start) / 1_000_000} мс")
    }
    measureExecutionTime()

    // 7. Форматирование и парсинг даты
    fun addDaysToDate(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = LocalDate.parse(dateString, formatter)
        val newDate = date.plusDays(10)
        return newDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    }
    println(addDaysToDate("25-12-2023"))

    // 8. Конвертация между часовыми поясами
    fun convertTimeZone(dateTime: LocalDateTime, fromZone: ZoneId, toZone: ZoneId): ZonedDateTime {
        val zonedDateTime = dateTime.atZone(fromZone)
        return zonedDateTime.withZoneSameInstant(toZone)
    }
    val utcDateTime = LocalDateTime.of(2023, 12, 25, 10, 0)
    println(convertTimeZone(utcDateTime, ZoneId.of("UTC"), ZoneId.of("Europe/Moscow")))

    // 9. Вычисление возраста по дате рождения
    fun calculateAge(birthDate: LocalDate): Int {
        val today = LocalDate.now()
        return today.year - birthDate.year - if (today.dayOfYear < birthDate.dayOfYear) 1 else 0
    }
    println("Возраст: ${calculateAge(LocalDate.of(1990, 12, 25))}")

    // 10. Создание календаря на месяц
    fun generateMonthlyCalendar(year: Int, month: Month) {
        val firstDay = LocalDate.of(year, month, 1)
        val lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth())
        var currentDate = firstDay
        while (!currentDate.isAfter(lastDay)) {
            val dayType = if (currentDate.dayOfWeek == DayOfWeek.SATURDAY || currentDate.dayOfWeek == DayOfWeek.SUNDAY) "Выходной" else "Рабочий"
            println("${currentDate}: $dayType")
            currentDate = currentDate.plusDays(1)
        }
    }
    generateMonthlyCalendar(2023, Month.OCTOBER)

    // 11. Генерация случайной даты в диапазоне
    fun generateRandomDate(startDate: LocalDate, endDate: LocalDate): LocalDate {

        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        val randomDay = (0..daysBetween).random()
        return startDate.plusDays(randomDay)
    }
    println(generateRandomDate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)))

    // 12. Расчет времени до заданной даты
    fun timeUntilEvent(eventDate: LocalDateTime): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(now, eventDate)
        return "${duration.toHours()} часов, ${duration.toMinutes() % 60} минут, ${duration.seconds % 60} секунд"
    }
    println(timeUntilEvent(LocalDateTime.of(2023, 12, 31, 23, 59)))

    // 13. Вычисление количества рабочих часов
    fun workingHours(start: LocalDateTime, end: LocalDateTime): Long {
        var totalHours = Duration.between(start, end).toHours()
        return totalHours.coerceAtLeast(0)  // Учитываем, что время может быть отрицательным
    }
    println("Рабочих часов: ${workingHours(LocalDateTime.of(2023, 10, 1, 9, 0), LocalDateTime.of(2023, 10, 1, 17, 0))}")

    // 14. Конвертация даты в строку с учетом локали
    fun formatDateWithLocale(date: LocalDate, locale: Locale): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale)
        return date.format(formatter)
    }
    println(formatDateWithLocale(LocalDate.of(2023, 10, 25), Locale("ru")))

    // 15. Определение дня недели по дате
    fun dayOfWeekInRussian(date: LocalDate): String {
        val dayOfWeek = date.dayOfWeek
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> "Понедельник"
            DayOfWeek.TUESDAY -> "Вторник"
            DayOfWeek.WEDNESDAY -> "Среда"
            DayOfWeek.THURSDAY -> "Четверг"
            DayOfWeek.FRIDAY -> "Пятница"
            DayOfWeek.SATURDAY -> "Суббота"
            DayOfWeek.SUNDAY -> "Воскресенье"
        }
    }
    println(dayOfWeekInRussian(LocalDate.of(2023, 10, 25)))
}