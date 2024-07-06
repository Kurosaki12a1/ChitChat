package navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.add_chat
import chitchatmultiplatform.composeapp.generated.resources.add_people
import chitchatmultiplatform.composeapp.generated.resources.bookmark
import chitchatmultiplatform.composeapp.generated.resources.chat_route
import chitchatmultiplatform.composeapp.generated.resources.contacts_route
import chitchatmultiplatform.composeapp.generated.resources.more
import chitchatmultiplatform.composeapp.generated.resources.more_route
import chitchatmultiplatform.composeapp.generated.resources.search
import chitchatmultiplatform.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import utils.extension.noRippleClickAble

@Composable
fun RowScope.ContactsBar(
    onSearchClick: () -> Unit,
    onAddContacts: () -> Unit,
    onStartNewChat: () -> Unit,
    onMoreClick: () -> Unit
) {
    Text(
        text = stringResource(Res.string.contacts_route),
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
        modifier = Modifier.noRippleClickAble { onAddContacts.invoke() },
        painter = painterResource(Res.drawable.add_people),
        contentDescription = "Add People"
    )
    Spacer(modifier = Modifier.width(16.dp))
    Icon(
        modifier = Modifier.noRippleClickAble { onStartNewChat.invoke() },
        painter = painterResource(Res.drawable.add_chat),
        contentDescription = "Add Chat"
    )
    Spacer(modifier = Modifier.width(16.dp))
    Icon(
        modifier = Modifier.noRippleClickAble { onMoreClick.invoke() },
        painter = painterResource(Res.drawable.more),
        contentDescription = "More"
    )
}

@Composable
fun ChatBar(
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

@Composable
fun RowScope.MoreBar(
    onSettingsClick: () -> Unit
) {
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