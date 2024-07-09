package presenter.more.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.more_route
import chitchatmultiplatform.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import utils.extension.noRippleClickAble

@Composable
fun ColumnScope.MoreBar(
    onSettingsClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = stringResource(Res.string.more_route),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.noRippleClickAble { onSettingsClick.invoke() },
            painter = painterResource(Res.drawable.settings),
            contentDescription = "More"
        )
    }
}