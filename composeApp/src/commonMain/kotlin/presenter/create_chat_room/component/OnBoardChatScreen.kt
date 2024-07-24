package presenter.create_chat_room.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.ic_edit
import chitchatmultiplatform.composeapp.generated.resources.ic_room_normal
import chitchatmultiplatform.composeapp.generated.resources.ic_room_secret
import coil3.compose.AsyncImage
import domain.models.ChatRoomModel
import domain.models.RoomType
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnBoardChatScreen(
    room: ChatRoomModel, paddingValues: PaddingValues
) {
    val isSecret = derivedStateOf { room.roomType == RoomType.SECRET.type }
    val defaultImage =
        remember { if (isSecret.value) Res.drawable.ic_room_secret else Res.drawable.ic_room_normal }

    Box(
        modifier = Modifier.fillMaxWidth().padding(paddingValues),
    ) {
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
            Box(modifier = Modifier.background(Color.Gray, CircleShape)) {
                AsyncImage(
                    modifier = Modifier.size(60.dp).clip(CircleShape).align(Alignment.Center),
                    model = null, // TODO room avatar
                    placeholder = painterResource(defaultImage),
                    error = painterResource(defaultImage),
                    contentDescription = "Avatar Room"
                )
                Image(
                    modifier = Modifier.size(24.dp).background(Color.White, CircleShape)
                        .align(Alignment.BottomEnd),
                    painter = painterResource(Res.drawable.ic_edit),
                    contentDescription = "Edit"
                )
            }
        }
    }
}
