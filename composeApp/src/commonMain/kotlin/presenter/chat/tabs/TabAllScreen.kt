package presenter.chat.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.chat_room
import chitchatmultiplatform.composeapp.generated.resources.chatroom_placeholder
import chitchatmultiplatform.composeapp.generated.resources.img_placeholder
import chitchatmultiplatform.composeapp.generated.resources.my_feed
import chitchatmultiplatform.composeapp.generated.resources.view_all
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.ForegroundEmphasisDefaultDefault
import ui.theme.ForegroundLightSubtle
import ui.theme.HighlightDefaultDefault
import ui.theme.HighlightEmphasisDefaultDefault
import utils.extension.noRippleClickAble

@Composable
fun LazyItemScope.TabAllScreen(
    onViewAllClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(HighlightDefaultDefault),
    ) {
        MyFeedScreen(onViewAllClick)
        ChatRoom()
    }
}

@Composable
private fun ColumnScope.MyFeedScreen(
    onViewAllClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.my_feed),
            style = MaterialTheme.typography.button,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.noRippleClickAble { onViewAllClick.invoke() },
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.view_all),
                style = MaterialTheme.typography.button,
                color = ForegroundEmphasisDefaultDefault
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                tint = ForegroundEmphasisDefaultDefault,
                contentDescription = "View All"
            )
        }
    }
}

@Composable
private fun ColumnScope.ChatRoom() {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = stringResource(Res.string.chat_room),
            style = MaterialTheme.typography.button,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

    /*    ItemChatRoom(
            iconRoom = null,
            name = "Samsung Notes",
            membersCount = 20,
            lastMessage = "This is today",
            lastSent = LocalDate(year = 2024, monthNumber = 10, dayOfMonth = 8),
            onChatRoomClick = {

            }
        )
        ItemChatRoom(
            iconRoom = null,
            name = "Samsung Notes Galaxy",
            membersCount = 24,
            lastMessage = "This is todayyyyyyyyyyyyyyyyyyyyy",
            lastSent = LocalDate(year = 2024, monthNumber = 11, dayOfMonth = 8),
            onChatRoomClick = {

            }
        )

        ItemChatRoom(
            iconRoom = "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/08/Hinh-nen-anime-cute-8-1.jpg",
            name = "Samsung Notes Galaxy",
            membersCount = 24,
            lastMessage = "This is todayyyyyyyyyyyyyyyyyyyyy",
            lastSent = LocalDate(year = 2024, monthNumber = 11, dayOfMonth = 8),
            onChatRoomClick = {

            }
        )*/
}

@Composable
private fun ItemChatRoom(
    iconRoom: String?,
    name: String,
    membersCount: Int = 1,
    lastMessage: String? = null,
    lastSent: LocalDate?,
    onChatRoomClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onChatRoomClick(name) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier.background(HighlightEmphasisDefaultDefault, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (iconRoom == null) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(Res.drawable.chatroom_placeholder),
                    contentDescription = "Chat Room"
                )
            } else {
                AsyncImage(
                    modifier = Modifier.size(48.dp).clip(CircleShape),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(iconRoom)
                        .crossfade(durationMillis = 1000)
                        .scale(Scale.FILL)
                        .build(),
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(Res.drawable.img_placeholder),
                    contentDescription = "Chat Room"
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = name,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    maxLines = 1,
                )
                if (membersCount > 1) {
                    Text(
                        text = membersCount.toString(),
                        style = MaterialTheme.typography.button,
                        color = ForegroundLightSubtle
                    )
                }
            }
            if (lastMessage != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = lastMessage,
                        style = MaterialTheme.typography.button,
                        overflow = TextOverflow.Ellipsis,
                        color = ForegroundLightSubtle,
                        maxLines = 1
                    )
                    if (lastSent != null) {
                        val day = lastSent.dayOfMonth.toString().padStart(2, '0')
                        val month = lastSent.monthNumber.toString().padStart(2, '0')
                        val year = lastSent.year
                        Text(
                            text = "$month-$day-$year",
                            style = MaterialTheme.typography.button,
                            color = ForegroundLightSubtle
                        )
                    }
                }
            }
        }
    }
}

