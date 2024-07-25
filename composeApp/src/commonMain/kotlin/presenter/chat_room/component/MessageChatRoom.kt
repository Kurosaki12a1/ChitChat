package presenter.chat_room.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.ChatBubble
import component.TrianglePosition
import domain.models.MessageModel
import domain.models.UserModel
import kotlinx.serialization.json.Json
import ui.theme.HighlightDefaultDefault
import utils.Utils

@Composable
fun MessageChatRoom(
    myUser: UserModel,
    oldChats: List<MessageModel>,
    newChat: List<MessageModel>,
    paddingValues: PaddingValues
) {
   /* val test = Json.decodeFromString<List<MessageModel>>(oldJson)
    val test2 = Json.decodeFromString<List<MessageModel>>(newJson)*/
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(HighlightDefaultDefault)
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(oldChats, key = { _, item -> item.id }) { index, item ->
            val isSender = item.senderId == myUser.userId
            ChatBubble(
                trianglePosition = if (isSender) TrianglePosition.BottomRight else TrianglePosition.TopLeft,
                isContinuous = Utils.isContinuousSenderId(oldChats, index),
            ) {
                Text(
                    text = item.content
                )
            }
        }
        itemsIndexed(newChat, key = { _, item -> item.id }) { index, item ->
            val isSender = item.senderId == myUser.userId
            ChatBubble(
                trianglePosition = if (isSender) TrianglePosition.BottomRight else TrianglePosition.TopLeft,
                isContinuous = Utils.isContinuousSenderId(newChat, index)
            ) {
                Text(
                    text = item.content
                )
            }

        }
    }
}

private val oldJson = "[\n" +
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

val newJson = "[\n" +
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
        "    \"content\": \"Saturday morning?\",\n" +
        "    \"timeStamp\": \"2024-07-25T10:00:00.000+00:00\",\n" +
        "    \"chatRoomId\": \"669bd9da542b68456f38cd54\",\n" +
        "    \"isRead\": false,\n" +
        "    \"edited\": false,\n" +
        "    \"reactions\": []\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"20\",\n" +
        "    \"senderId\": \"104772134517320061836\",\n" +
        "    \"content\": \"Sounds good to me!\",\n" +
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