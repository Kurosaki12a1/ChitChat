package presenter.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.announcement
import chitchatmultiplatform.composeapp.generated.resources.chat
import chitchatmultiplatform.composeapp.generated.resources.chat_unfocus
import chitchatmultiplatform.composeapp.generated.resources.ic_lock
import chitchatmultiplatform.composeapp.generated.resources.ic_notice
import chitchatmultiplatform.composeapp.generated.resources.secret_chat
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.BackgroundColorEmphasis
import utils.extension.noRippleClickAble

@Composable
fun BottomSheetStartNewChatScreen(
    onChatClick: () -> Unit,
    onSecretChatClick: () -> Unit,
    onAnnouncementClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Start a New Chat",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BackgroundColorEmphasis)
        )
        listBottomSheet.forEachIndexed { index, itemBottomSheetData ->
            ItemBottomSheet(
                item = itemBottomSheetData,
                onClick = {
                    when (index) {
                        0 -> onChatClick.invoke()
                        1 -> onSecretChatClick.invoke()
                        else -> onAnnouncementClick.invoke()
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ItemBottomSheet(
    item: ItemBottomSheetData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().noRippleClickAble { onClick.invoke() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(item.icon),
            contentDescription = "Icon"
        )
        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Normal
        )
    }
}

private data class ItemBottomSheetData(
    val icon: DrawableResource,
    val title: StringResource
)

private val listBottomSheet = listOf(
    ItemBottomSheetData(
        icon = Res.drawable.chat_unfocus,
        title = Res.string.chat
    ),
    ItemBottomSheetData(
        icon = Res.drawable.ic_lock,
        title = Res.string.secret_chat
    ),
    ItemBottomSheetData(
        icon = Res.drawable.ic_notice,
        title = Res.string.announcement
    )
)

