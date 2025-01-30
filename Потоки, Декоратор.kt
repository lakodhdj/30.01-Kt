import java.io.File
import java.io.FileReader
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.channels.FileChannel
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.StandardOpenOption
import java.nio.file.Path
import java.util.concurrent.CountDownLatch

// Задание 1: Декораторы для обработки текста
interface TextProcessor {
    fun process(text: String): String
}

class SimpleTextProcessor : TextProcessor {
    override fun process(text: String): String = text
}

class UpperCaseDecorator(private val processor: TextProcessor) : TextProcessor {
    override fun process(text: String): String = processor.process(text).toUpperCase()
}

class TrimDecorator(private val processor: TextProcessor) : TextProcessor {
    override fun process(text: String): String = processor.process(text).trim()
}

class ReplaceDecorator(private val processor: TextProcessor) : TextProcessor {
    override fun process(text: String): String = processor.process(text).replace(" ", "_")
}

// Задание 2: Сравнение производительности IO и NIO
fun measureIoPerformance() {
    val startTime = System.nanoTime()
    val file = File("input.txt")
    val reader = BufferedReader(FileReader(file))
    val content = StringBuilder()
    reader.use { br ->
        var line: String?
        while (br.readLine().also { line = it } != null) {
            content.append(line).append("\n")
        }
    }
    val endTime = System.nanoTime()
    println("IO чтение: ${endTime - startTime} наносекунд")
}

fun measureNioPerformance() {
    val startTime = System.nanoTime()
    val path = Paths.get("input.txt")
    val channel = FileChannel.open(path)
    val buffer = ByteBuffer.allocate(1024)
    val content = StringBuilder()
    while (channel.read(buffer) > 0) {
        buffer.flip()
        while (buffer.hasRemaining()) {
            content.append(buffer.get().toChar())
        }
        buffer.clear()
    }
    channel.close()
    val endTime = System.nanoTime()
    println("NIO чтение: ${endTime - startTime} наносекунд")
}

// Задание 3: Копирование файла с использованием FileChannel (Java NIO)
fun copyFileUsingNio(source: String, destination: String) {
    val srcChannel = FileInputStream(source).channel
    val destChannel = FileOutputStream(destination).channel
    srcChannel.transferTo(0, srcChannel.size(), destChannel)
    srcChannel.close()
    destChannel.close()
    println("Файл успешно скопирован с использованием NIO.")
}

// Задание 4: Асинхронное чтение файла с использованием NIO.2
fun readFileAsync(filePath: Path) {
    val latch = CountDownLatch(1)
    val asyncChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.READ)

    val buffer = ByteBuffer.allocate(1024)

    asyncChannel.read(buffer, 0, null, object : java.nio.channels.CompletionHandler<Int, Void> {
        override fun completed(result: Int?, attachment: Void?) {
            buffer.flip()
            while (buffer.hasRemaining()) {
                print(buffer.get().toChar())
            }
            latch.countDown()
        }

        override fun failed(exc: Throwable?, attachment: Void?) {
            println("Ошибка при чтении файла: ${exc?.message}")
            latch.countDown()
        }
    })
    latch.await() // Ожидаем завершения асинхронной операции
    asyncChannel.close()
}

// Пример использования всех декораторов
fun main() {
    // Задание 1: Декораторы
    val processor = ReplaceDecorator(
        UpperCaseDecorator(
            TrimDecorator(
                SimpleTextProcessor()
            )
        )
    )
    val result = processor.process(" Hello world ")
    println("Результат после декораторов: $result")  // Вывод: HELLO_WORLD

    // Задание 2: Сравнение производительности IO и NIO
    measureIoPerformance()
    measureNioPerformance()

    // Задание 3: Копирование файла с использованием NIO
    copyFileUsingNio("source.txt", "destination.txt")

    // Задание 4: Асинхронное чтение файла с использованием NIO.2
    val filePath = Paths.get("input.txt")
    println("Чтение файла асинхронно:")
    readFileAsync(filePath)
}