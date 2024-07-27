package presenter.create_chat_room.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.edit
import chitchatmultiplatform.composeapp.generated.resources.ic_room_normal
import chitchatmultiplatform.composeapp.generated.resources.ic_room_secret
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import domain.models.ChatRoomModel
import domain.models.RoomType
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnBoardChatScreen(
    room: ChatRoomModel,
    paddingValues: PaddingValues
) {
    val isSecret = derivedStateOf { room.roomType == RoomType.SECRET.type }
    val isPublic = derivedStateOf { room.participants.size > 2 }
    val defaultImage =
        remember { if (isSecret.value) Res.drawable.ic_room_secret else Res.drawable.ic_room_normal }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        val maxWith = this.maxWidth
        val maxHeight = this.maxHeight
        Column(
            modifier = Modifier.fillMaxWidth().offset(y = maxHeight * 0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.background(Color.Gray, CircleShape)) {
                AsyncImage(
                    modifier = Modifier.size(80.dp).clip(CircleShape).align(Alignment.Center)
                        .padding(10.dp),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(room.roomPhoto)
                        .build(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(defaultImage),
                    error = painterResource(defaultImage),
                    contentDescription = "Room Photo"
                )
                Box(
                    modifier = Modifier.background(Color.White, CircleShape)
                        .align(Alignment.BottomEnd)
                        .border(1.dp, Color.Black, CircleShape)
                        .padding(4.dp)
                ) {
                    Image(
                        modifier = Modifier.size(20.dp).align(Alignment.Center),
                        contentScale = ContentScale.Inside,
                        painter = painterResource(Res.drawable.edit),
                        contentDescription = "Edit"
                    )
                }
            }
        }
    }
}
