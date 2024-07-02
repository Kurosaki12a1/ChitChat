package presenter.auth.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.sign_in
import org.jetbrains.compose.resources.stringResource
import ui.theme.topAppBarBackgroundColor
import ui.theme.topAppBarContentColor

@Composable
fun AuthTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.sign_in),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}
