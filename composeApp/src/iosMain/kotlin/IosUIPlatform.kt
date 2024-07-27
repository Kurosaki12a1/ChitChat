import androidx.compose.runtime.Composable

/*
actual class PlatformScreenModule : PlatformScreen {
    @Composable
    actual override fun Content(screen: NavigationItem, viewModel: ViewModel) {

    }
}

*/

@Composable
actual fun BottomSheetView(
    visible: Boolean,
    onSheetStateChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {

}
