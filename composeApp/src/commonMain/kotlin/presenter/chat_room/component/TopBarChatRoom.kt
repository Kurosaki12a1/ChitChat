package presenter.chat_room.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.more
import chitchatmultiplatform.composeapp.generated.resources.search
import component.StatusDot
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBarChatRoom(
    title: String,
    status: String? = null,
    onBack: () -> Unit,
    onSearchMessage: (String) -> Unit,
    onMore: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier.clickable { onBack.invoke() },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (status != null) {
            StatusDot(status, size = 24.dp, radius = 24f)
        }
        Image(
            modifier = Modifier.clickable { onSearchMessage("") },
            painter = painterResource(Res.drawable.search),
            contentDescription = "Search"
        )
        Image(
            modifier = Modifier.clickable { onMore.invoke() },
            painter = painterResource(Res.drawable.more),
            contentDescription = "Room Info"
        )
    }
}