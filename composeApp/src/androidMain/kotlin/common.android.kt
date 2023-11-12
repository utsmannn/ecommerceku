import java.util.UUID

actual fun getPlatformName(): String = "Android"

actual fun uuid(): String {
    return UUID.randomUUID().toString()
}