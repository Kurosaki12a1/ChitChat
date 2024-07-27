package presenter.chat_room.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.models.MessageModel
import domain.models.UserModel
import kotlinx.serialization.json.Json
import ui.theme.BubbleChatOpponentColor
import ui.theme.ForegroundAccentSubtle
import utils.Utils

/**
 * Composable function to display a chat room with messages.
 *
 * @param myUser The user model of the current user.
 * @param oldChats List of old messages in the chat room.
 * @param newChat List of new messages in the chat room.
 * @param paddingValues Padding values for the chat room.
 * @param onActionClick Lambda function to be called when an action item is clicked.
 * @param onEmoteClick Lambda function to be called when an emote item is clicked.
 */
@Composable
fun MessageChatRoom(
    myUser: UserModel,
    oldChats: List<MessageModel>,
    newChat: List<MessageModel>,
    paddingValues: PaddingValues,
    onActionClick: (ActionMessageItem) -> Unit,
    onEmoteClick: (EmoteMessageItem) -> Unit
) {
    // if (oldChats.isEmpty() && newChat.isEmpty()) return
    val test = Json.decodeFromString<List<MessageModel>>(oldJson)
    val test2 = Json.decodeFromString<List<MessageModel>>(newJson)
    val totalList = test + test2
    var timestampStates by remember { mutableStateOf((totalList).associate { it.id to false }) }
    var showMenuForMessage by remember { mutableStateOf<MessageModel?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(totalList, key = { _, item -> item.id }) { index, item ->
            val isSender = item.senderId == myUser.userId
            if (timestampStates[item.id]!!) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = item.timeStamp.toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.caption
                )
            }
            ChatBubble(
                trianglePosition = if (isSender) TrianglePosition.BottomRight else TrianglePosition.TopLeft,
                backgroundColor = if (isSender) ForegroundAccentSubtle else BubbleChatOpponentColor,
                reactionColor = Color.White,
                isContinuous = Utils.isContinuousSenderId(totalList, index),
                messageModel = item,
                shouldShowPopUp = showMenuForMessage != null && showMenuForMessage == item,
                onClick = {
                    timestampStates = timestampStates.mapValues { (id, _) ->
                        if (id == item.id) !timestampStates[id]!! else false
                    }
                },
                onLongClick = { showMenuForMessage = item },
                onDismissMenu = { showMenuForMessage = null },
                onActionClick = onActionClick,
                onEmoteClick = onEmoteClick
            )
        }
    }
}


private const val oldJson = "[\n" +
        "  {\n" +
        "    \"id\": \"1\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Hello, how are you?\",\n" +
        "    \"timeStamp\": \"2024-07-25T08:30:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"2\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"I'm good, thank you!\",\n" +
        "    \"timeStamp\": \"2024-07-25T08:35:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"108250150007484359736\",\n" +
        "        \"emoCode\": \"like\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"3\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"What are you up to?\",\n" +
        "    \"timeStamp\": \"2024-07-25T08:40:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"4\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"Just working on a project.\",\n" +
        "    \"timeStamp\": \"2024-07-25T08:45:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"5\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Sounds interesting!\",\n" +
        "    \"timeStamp\": \"2024-07-25T08:50:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"104772134517320061836\",\n" +
        "        \"emoCode\": \"smile\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"6\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"Yes, it is.\",\n" +
        "    \"timeStamp\": \"2024-07-25T08:55:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"7\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Let's catch up later.\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:00:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"104772134517320061836\",\n" +
        "        \"emoCode\": \"thumbs_up\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"8\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"Sure thing!\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:05:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"9\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Take care.\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:10:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"104772134517320061836\",\n" +
        "        \"emoCode\": \"wave\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"10\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"You too.\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:15:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  }\n" +
        "]"

private const val newJson = "[\n" +
        "  {\n" +
        "    \"id\": \"11\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Hey, are you free this weekend?\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:20:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"12\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"Yes, I am. What do you have in mind?\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:25:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"108250150007484359736\",\n" +
        "        \"emoCode\": \"question\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"13\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Thinking about a short trip.\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:30:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"14\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"That sounds great!\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:35:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"108250150007484359736\",\n" +
        "        \"emoCode\": \"smile\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"15\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Where should we go?\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:40:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"16\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"How about the beach?\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:45:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"17\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Perfect choice!\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:50:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"104772134517320061836\",\n" +
        "        \"emoCode\": \"thumbs_up\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"18\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"When should we leave?\",\n" +
        "    \"timeStamp\": \"2024-07-25T09:55:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"19\",\n" +
        "    \"senderId\": \"108250150007484359736\",\n" +
        "    \"content\": \"Saturday morning? Maybe around 9:00AM. What do you think? Please tell me!\",\n" +
        "    \"timeStamp\": \"2024-07-25T10:00:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"20\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"Sounds good to me! Let me know if you are ready because i am looking forward into it\",\n" +
        "    \"timeStamp\": \"2024-07-25T10:05:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": true,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": [\n" +
        "      {\n" +
        "        \"userId\": \"108250150007484359736\",\n" +
        "        \"emoCode\": \"clap\"\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "]"