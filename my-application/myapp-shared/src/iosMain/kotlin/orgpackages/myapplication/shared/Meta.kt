package orgpackages.myapplication.shared

import platform.UIKit.UIDevice

actual object Meta {
    actual val platform: String = UIDevice.currentDevice.run { "$systemName $systemVersion" }
}