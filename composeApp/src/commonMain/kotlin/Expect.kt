import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import navigation.NavigationItem

interface PlatformScreen {
    @Composable
    fun Content(screen: NavigationItem, viewModel: ViewModel)
}

expect class PlatformScreenModule : PlatformScreen {
    @Composable
    override fun Content(screen: NavigationItem, viewModel: ViewModel)
}

