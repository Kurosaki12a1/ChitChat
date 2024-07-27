import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*actual class PlatformScreenModule : PlatformScreen {
    @Composable
    actual override fun Content(screen: NavigationItem, viewModel: ViewModel) {

    }
}*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun BottomSheetView(
    visible: Boolean,
    onSheetStateChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(visible) {
        if (visible && !bottomSheetState.isVisible) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        if (bottomSheetState.targetValue == ModalBottomSheetValue.Hidden) {
            onSheetStateChange(false)
        } else {
            onSheetStateChange(true)
        }
    }

    ModalBottomSheetLayout(
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetState = bottomSheetState,
        sheetContent = { content() }) {
    }
}
