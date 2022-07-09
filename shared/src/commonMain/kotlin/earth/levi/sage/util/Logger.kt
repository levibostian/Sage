package earth.levi.sage.util

interface Logger {
    fun debug(message: String)
    fun verbose(message: String)
    fun error(message: String)
}

expect class LoggerImpl: Logger