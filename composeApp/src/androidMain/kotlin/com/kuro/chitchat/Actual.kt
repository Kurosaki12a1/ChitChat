import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import navigation.NavigationItem

actual class PlatformScreenModule : PlatformScreen {
    @Composable
    actual override fun Content(screen: NavigationItem, viewModel: ViewModel) {

    }
}
