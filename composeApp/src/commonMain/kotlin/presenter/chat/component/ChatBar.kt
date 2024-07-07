package presenter.chat.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.add_chat
import chitchatmultiplatform.composeapp.generated.resources.bookmark
import chitchatmultiplatform.composeapp.generated.resources.chat_route
import chitchatmultiplatform.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import utils.extension.noRippleClickAble

@Composable
fun LazyItemScope.ChatBar(
    onSearchClick: () -> Unit,
    onStartNewChat: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = stringResource(Res.string.chat_route),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.noRippleClickAble { onSearchClick.invoke() },
            painter = painterResource(Res.drawable.search),
            contentDescription = "Search"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier.noRippleClickAble { onStartNewChat.invoke() },
            painter = painterResource(Res.drawable.add_chat),
            contentDescription = "Add Chat"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier.noRippleClickAble { onBookmarkClick.invoke() },
            painter = painterResource(Res.drawable.bookmark),
            contentDescription = "More"
        )
    }
}