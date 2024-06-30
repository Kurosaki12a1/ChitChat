import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.initKoin
import navigation.RootComponent
import javax.swing.SwingUtilities

fun main() {
    val windowState = WindowState()
    val lifecycle = LifecycleRegistry()
    val root = runOnMainThreadBlocking { RootComponent(DefaultComponentContext(lifecycle)) }

    val koin = initKoin().koin
    singleWindowApplication(
        state = windowState,
        title = "Chit Chat App",
    ) {
        LifecycleController(lifecycle, windowState)
        App(root)
    }
}

private inline fun <T : Any> runOnMainThreadBlocking(crossinline block: () -> T): T {
    lateinit var result: T
    SwingUtilities.invokeAndWait { result = block() }
    return result
}