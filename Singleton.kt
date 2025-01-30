// Задача 1. Создание класса базы данных (Singleton)
object DatabaseConnection {
    init {
        println("Подключение к базе данных создано")
    }

    fun connect() {
        println("Подключение успешно выполнено.")
    }
}

// Задача 2. Логирование в системе (Singleton)
object Logger {
    private val logs = mutableListOf<String>()

    fun addLog(message: String) {
        logs.add(message)
    }

    fun showLogs() {
        if (logs.isEmpty()) {
            println("Логи пусты")
        } else {
            logs.forEach { println(it) }
        }
    }
}

// Задача 3. Реализация статусов заказа (Enum)
enum class OrderStatus {
    NEW, IN_PROGRESS, DELIVERED, CANCELLED
}

class Order(var status: OrderStatus) {

    fun changeStatus(newStatus: OrderStatus) {
        if (status == OrderStatus.DELIVERED && newStatus == OrderStatus.CANCELLED) {
            println("Ошибка: Невозможно отменить доставленный заказ!")
        } else {
            status = newStatus
            println("Статус заказа изменен на: $status")
        }
    }

    fun getStatus(): OrderStatus {
        return status
    }
}

// Задача 4. Сезоны года (Enum)
enum class Season {
    WINTER, SPRING, SUMMER, AUTUMN
}

fun getSeasonName(season: Season): String {
    return when(season) {
        Season.WINTER -> "Зима"
        Season.SPRING -> "Весна"
        Season.SUMMER -> "Лето"
        Season.AUTUMN -> "Осень"
    }
}

fun main() {
    // Задача 1. Проверка Singleton для DatabaseConnection
    val db1 = DatabaseConnection
    val db2 = DatabaseConnection

    println("db1 и db2 ссылаются на один и тот же объект: ${db1 === db2}")
    db1.connect()

    // Задача 2. Проверка Logger
    Logger.addLog("Ошибка при подключении к базе данных")
    Logger.addLog("Пользователь вошел в систему")

    println("Все логи:")
    Logger.showLogs()

    // Задача 3. Проверка Order и статусов
    val order = Order(OrderStatus.NEW)
    println("Статус заказа: ${order.getStatus()}")
    order.changeStatus(OrderStatus.IN_PROGRESS)
    order.changeStatus(OrderStatus.DELIVERED)
    order.changeStatus(OrderStatus.CANCELLED)

    // Задача 4. Проверка Seasons
    println("Зима на русском: ${getSeasonName(Season.WINTER)}")
    println("Весна на русском: ${getSeasonName(Season.SPRING)}")
    println("Лето на русском: ${getSeasonName(Season.SUMMER)}")
    println("Осень на русском: ${getSeasonName(Season.AUTUMN)}")
}