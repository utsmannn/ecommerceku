import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.NSUUID
import platform.Foundation.URLByAppendingPathComponent
import platform.UIKit.UIApplication
import kotlin.system.exitProcess

actual fun getPlatformName(): String = "iOS"

actual fun uuid(): String {
    return NSUUID.UUID().UUIDString()
}