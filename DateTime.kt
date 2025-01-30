import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun main() {
    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()

    // Formatter for date
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    // Formatter for time
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    println("Дата: ${currentDate.format(dateFormatter)}")
    println("Время: ${currentTime.format(timeFormatter)}")
}
