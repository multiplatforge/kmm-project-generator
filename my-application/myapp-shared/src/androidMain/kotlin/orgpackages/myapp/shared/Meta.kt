package orgpackages.myapp.shared

import android.os.Build

actual object Meta {
    actual val platform: String = "Android ${Build.VERSION.SDK_INT}"
}