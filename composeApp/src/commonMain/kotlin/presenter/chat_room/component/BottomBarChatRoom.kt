package presenter.chat_room.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.ic_emotion
import chitchatmultiplatform.composeapp.generated.resources.ic_thumb_up
import chitchatmultiplatform.composeapp.generated.resources.pick_image_black
import chitchatmultiplatform.composeapp.generated.resources.place_holder_message
import domain.models.MessageModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.BackgroundColorEmphasis
import utils.now

@Composable
fun BottomBarChatRoom(
    senderId: String? = "", chatRoomId: String? = "", onSend: (MessageModel) -> Unit
) {
    val messageValue = remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth().background(Color.White)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Add"
        )
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(Res.drawable.pick_image_black),
            contentDescription = "Pick Image"
        )
        TextField(modifier = Modifier.weight(1f),
            value = messageValue.value,
            onValueChange = { messageValue.value = it },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BackgroundColorEmphasis,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                onSend(
                    MessageModel(
                        senderId = senderId ?: "",
                        chatRoomId = chatRoomId ?: "",
                        content = messageValue.value,
                        timeStamp = now()
                    )
                )
                messageValue.value = ""
            }),
            placeholder = { Text(text = stringResource(Res.string.place_holder_message)) },
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_emotion),
                    contentDescription = "Emotion"
                )
            })

        if (messageValue.value.isNotEmpty()) {
            Icon(
                modifier = Modifier.clickable {
                    onSend(
                        MessageModel(
                            senderId = senderId ?: "",
                            chatRoomId = chatRoomId ?: "",
                            content = messageValue.value,
                            timeStamp = now()
                        )
                    )
                    messageValue.value = ""
                }, imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send"
            )
        } else {
            Icon(
                modifier = Modifier.clickable {
                    onSend(
                        MessageModel(
                            senderId = senderId ?: "",
                            chatRoomId = chatRoomId ?: "",
                            content = ":emo:like",
                            timeStamp = now()
                        )
                    )
                },
                painter = painterResource(Res.drawable.ic_thumb_up),
                contentDescription = "Like"
            )
        }
    }
}