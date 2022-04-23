package orgpackages.myapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import orgpackages.myapp.android.ui.ContentView
import orgpackages.myapp.android.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                ContentView()
            }
        }
    }
}