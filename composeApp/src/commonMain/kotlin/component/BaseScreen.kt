package component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import viewmodel.BaseViewModel

@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    content: @Composable () -> Unit
) {
    LaunchedEffect(Unit) {
        println("Có vào đây init không?")
        viewModel.init()
    }
    content()
}