import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import navigation.RootComponent

fun MainViewController() = ComposeUIViewController {
    val rootComponent = remember {
        RootComponent(DefaultComponentContext(lifecycle = ApplicationLifeCycle()))
    }
    App(root = rootComponent)
}

// Implement KMPAuth https://github.com/mirzemehdi/KMPAuth/tree/main/sampleApp/iosApp/iosApp