package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.MessageBarState
import kotlinx.coroutines.delay
import ui.theme.ErrorRed
import ui.theme.InfoGreen


@Composable
fun MessageBar(messageBarState: MessageBarState) {
    val startAnimation = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(messageBarState) {
        if (messageBarState.error != null) {
            errorMessage.value = "${messageBarState.error?.message}"
        }
        startAnimation.value = true
        delay(3000)
        startAnimation.value = false
    }

    AnimatedVisibility(
        visible = messageBarState.error != null && startAnimation.value
                || messageBarState.message != null && startAnimation.value,
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top
        )
    ) {
        Message(
            messageBarState = messageBarState,
            errorMessage = errorMessage.value
        )
    }
}

@Composable
fun Message(
    messageBarState: MessageBarState,
    errorMessage: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (messageBarState.error != null) ErrorRed else InfoGreen)
            .padding(horizontal = 20.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =
            if (messageBarState.error != null) Icons.Default.Warning
            else Icons.Default.Check,
            contentDescription = "Message Bar Icon",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text =
            if (messageBarState.error != null) errorMessage
            else messageBarState.message.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.button.fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}