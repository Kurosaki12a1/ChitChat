package presenter.chat_room.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBarChatRoom() {
    Row(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
        Text(text = "Test Bottom")
    }
}