import androidx.compose.runtime.Composable


/*
interface PlatformScreen {
    @Composable
    fun Content(screen: NavigationItem, viewModel: ViewModel)
}

expect class PlatformScreenModule : PlatformScreen {
    @Composable
    override fun Content(screen: NavigationItem, viewModel: ViewModel)
}
*/


@Composable
expect fun BottomSheetView(
    visible: Boolean,
    onSheetStateChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
)
