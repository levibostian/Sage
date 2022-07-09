package earth.levi.sage.util

actual class LoggerImpl: Logger {
    private val tag = "[Sage]"

    override fun debug(message: String) {
        println(message)
    }

    override fun verbose(message: String) {
        println(message)
    }

    override fun error(message: String) {
        println(message)
    }
}