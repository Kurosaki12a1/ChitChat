package presenter.add_chat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.ic_cancel
import chitchatmultiplatform.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.ForegroundGoodGoodSubtle
import utils.extension.noRippleClickAble

@Composable
fun AddChatTopBar(
    title: String,
    userSelected: Int,
    onSelectClick: () -> Unit,
    onCancel: () -> Unit
) {
    val toolBarTitle = remember { mutableStateOf("") }
    val enableOkButton by derivedStateOf { userSelected > 0 }
    LaunchedEffect(userSelected) {
        if (userSelected == 0) {
            toolBarTitle.value = title
        } else {
            toolBarTitle.value = "$title ($userSelected)"
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.noRippleClickAble { onCancel.invoke() },
            painter = painterResource(Res.drawable.ic_cancel),
            contentDescription = "Cancel"
        )
        Text(
            text = toolBarTitle.value,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        if (enableOkButton) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.noRippleClickAble { onSelectClick.invoke() },
                text = stringResource(Res.string.ok),
                color = ForegroundGoodGoodSubtle
            )
        }
    }
}