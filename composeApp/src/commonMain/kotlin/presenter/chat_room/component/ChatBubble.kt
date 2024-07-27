package presenter.chat_room.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.bookmark
import chitchatmultiplatform.composeapp.generated.resources.copy
import chitchatmultiplatform.composeapp.generated.resources.delete
import chitchatmultiplatform.composeapp.generated.resources.edit
import chitchatmultiplatform.composeapp.generated.resources.emote_beg
import chitchatmultiplatform.composeapp.generated.resources.emote_cheer
import chitchatmultiplatform.composeapp.generated.resources.emote_clap
import chitchatmultiplatform.composeapp.generated.resources.emote_cry
import chitchatmultiplatform.composeapp.generated.resources.emote_happy
import chitchatmultiplatform.composeapp.generated.resources.emote_laugh_cry
import chitchatmultiplatform.composeapp.generated.resources.emote_wow
import chitchatmultiplatform.composeapp.generated.resources.forward
import chitchatmultiplatform.composeapp.generated.resources.ic_bookmark
import chitchatmultiplatform.composeapp.generated.resources.ic_copy
import chitchatmultiplatform.composeapp.generated.resources.ic_delete
import chitchatmultiplatform.composeapp.generated.resources.ic_edit
import chitchatmultiplatform.composeapp.generated.resources.ic_forward
import chitchatmultiplatform.composeapp.generated.resources.ic_forward_to_user
import chitchatmultiplatform.composeapp.generated.resources.ic_reply
import chitchatmultiplatform.composeapp.generated.resources.ic_send_message
import chitchatmultiplatform.composeapp.generated.resources.ic_thumb_up
import chitchatmultiplatform.composeapp.generated.resources.ic_translate
import chitchatmultiplatform.composeapp.generated.resources.reply
import chitchatmultiplatform.composeapp.generated.resources.send
import chitchatmultiplatform.composeapp.generated.resources.to_me
import chitchatmultiplatform.composeapp.generated.resources.translate
import domain.models.MessageModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


enum class TrianglePosition {
    BottomRight, TopLeft
}

/**
 * Composable function to render a chat bubble with optional popup menu.
 *
 * @param modifier Modifier to apply to this layout node.
 * @param messageModel Data model representing the chat message.
 * @param backgroundColor Background color of the chat bubble.
 * @param reactionColor Color for the reactions.
 * @param trianglePosition Position of the triangle on the chat bubble.
 * @param isContinuous Flag to determine if the message is continuous.
 * @param shouldShowPopUp Flag to determine if the popup menu should be shown.
 * @param onClick Lambda function to be called on single click.
 * @param onLongClick Lambda function to be called on long click.
 * @param onActionClick Lambda function to handle action clicks.
 * @param onEmoteClick Lambda function to handle emote clicks.
 * @param onDismissMenu Lambda function to dismiss the popup menu.
 */
@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    messageModel: MessageModel,
    backgroundColor: Color = MaterialTheme.colors.surface,
    reactionColor: Color = MaterialTheme.colors.secondary,
    trianglePosition: TrianglePosition = TrianglePosition.BottomRight,
    isContinuous: Boolean = false,
    shouldShowPopUp: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onActionClick: (ActionMessageItem) -> Unit,
    onEmoteClick: (EmoteMessageItem) -> Unit,
    onDismissMenu: () -> Unit
) {
    val isMySelf = trianglePosition == TrianglePosition.BottomRight
    // BoxWithConstraints to get maxWidth for setting width constraints of the bubble
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        if (shouldShowPopUp) {
            Popup(
                alignment = if (isMySelf) Alignment.CenterStart else Alignment.CenterEnd,
                properties = PopupProperties(focusable = true),
                onDismissRequest = onDismissMenu
            ) {
                MessageMenu(
                    modifier = Modifier.widthIn(min = 100.dp, max = maxWidth * 0.7f),
                    isSelf = isMySelf,
                    onDismiss = onDismissMenu,
                    onActionClick = onActionClick,
                    onEmoteClick = onEmoteClick
                )
            }
        }
        // Box to contain the chat bubble and handle tap gestures
        Box(
            modifier
                .widthIn(min = 100.dp, max = maxWidth * 0.7f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onClick() },
                        onLongPress = { onLongClick() }
                    )
                }
                .align(if (trianglePosition == TrianglePosition.BottomRight) Alignment.BottomEnd else Alignment.TopStart)
        ) {
            // Canvas to draw the chat bubble with rounded corners and triangle
            Canvas(modifier = Modifier.matchParentSize()) {
                val cornerRadius = 16.dp.toPx()
                val triangleSize = 20.dp.toPx()
                val totalHeight = size.height

                // Path to draw the bubble with rounded corners
                val path = Path().apply {
                    // Move to the start of the top-left rounded corner
                    moveTo(cornerRadius, 0f)
                    // Draw the top edge
                    lineTo(size.width - cornerRadius, 0f)
                    // Draw the top-right rounded corner
                    arcTo(
                        rect = Rect(
                            size.width - 2 * cornerRadius, 0f,
                            size.width, 2 * cornerRadius
                        ),
                        startAngleDegrees = -90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Draw the right edge
                    lineTo(size.width, totalHeight - cornerRadius)
                    // Draw the bottom-right rounded corner
                    arcTo(
                        rect = Rect(
                            size.width - 2 * cornerRadius,
                            totalHeight - 2 * cornerRadius,
                            size.width,
                            totalHeight
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Draw the bottom edge
                    lineTo(cornerRadius, totalHeight)
                    // Draw the bottom-left rounded corner
                    arcTo(
                        rect = Rect(
                            0f, totalHeight - 2 * cornerRadius,
                            2 * cornerRadius, totalHeight
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    // Draw the left edge
                    lineTo(0f, cornerRadius)
                    // Draw the top-left rounded corner
                    arcTo(
                        rect = Rect(
                            0f, 0f,
                            2 * cornerRadius, 2 * cornerRadius
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    close()
                }

                // Draw the bubble path with the background color
                drawPath(
                    path = path,
                    color = backgroundColor,
                    style = Fill
                )

                // If not continuous, draw the triangle at the specified position
                if (!isContinuous) {
                    val trianglePath = Path().apply {
                        // Determine the position of the triangle
                        when (trianglePosition) {
                            TrianglePosition.BottomRight -> {
                                // Move to the starting point of the triangle at the bottom-right position
                                moveTo(
                                    size.width - cornerRadius - triangleSize,
                                    totalHeight
                                )
                                // Draw the line to the bottom point of the triangle
                                lineTo(
                                    size.width,
                                    totalHeight + triangleSize
                                )
                                // Draw the triangle pointing downwards
                                lineTo(size.width - cornerRadius, totalHeight)
                                // Close the path to complete the triangle
                                close()
                            }

                            TrianglePosition.TopLeft -> {
                                // Move to the starting point of the triangle at the top-left position
                                moveTo(0f, -triangleSize)
                                // Draw the line to the top point of the triangle
                                lineTo(triangleSize + cornerRadius, 0f)
                                // Draw the triangle pointing upwards
                                lineTo(cornerRadius, 0f)
                                // Close the path to complete the triangle
                                close()
                            }
                        }
                    }

                    // Draw the triangle path with the background color
                    drawPath(
                        path = trianglePath,
                        color = backgroundColor,
                        style = Fill
                    )
                }
            }

            // Column to contain the message content and reactions
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Text composable for the message content
                Text(
                    text = messageModel.content,
                    color = if (isMySelf) Color.White else Color.Black,
                    style = MaterialTheme.typography.body2
                )
                // If there are reactions, display them
                if (messageModel.reactions.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .background(
                                    reactionColor.copy(alpha = 0.4f),
                                    RoundedCornerShape(8.dp)
                                )
                                .border(2.dp, reactionColor, RoundedCornerShape(8.dp))
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(Res.drawable.ic_thumb_up),
                                contentDescription = "Like"
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = messageModel.reactions.size.toString(),
                                style = MaterialTheme.typography.body2,
                                color = if (isMySelf) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display a message menu with actions and emotes.
 *
 * @param modifier Modifier to apply to this layout node.
 * @param isSelf Boolean flag indicating if the message is from the user themselves.
 * @param onDismiss Lambda function to be called when the menu is dismissed.
 * @param onActionClick Lambda function to be called when an action item is clicked.
 * @param onEmoteClick Lambda function to be called when an emote item is clicked.
 */
@Composable
private fun MessageMenu(
    modifier: Modifier = Modifier,
    isSelf: Boolean,
    onDismiss: () -> Unit,
    onActionClick: (ActionMessageItem) -> Unit,
    onEmoteClick: (EmoteMessageItem) -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        getListAction(isSelf).chunked(4).forEach { list ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                list.forEach { item ->
                    ItemActionMessage(
                        action = item,
                        onClick = {
                            onActionClick(it)
                            onDismiss()
                        }
                    )
                }
            }
        }
        Divider(Modifier.fillMaxWidth().height(1.dp).background(Color.Black))
        // Display emote items in chunks of 4
        listEmoteMessageName.chunked(4).forEach { list ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                list.forEach { item ->
                    ItemEmoteMessage(
                        emote = item,
                        onClick = {
                            onEmoteClick(it)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}


/**
 * Composable function to display an action item in a row.
 *
 * @param action The action message to display.
 * @param onClick Lambda function to be called when the action item is clicked.
 */
@Composable
private fun RowScope.ItemActionMessage(
    action: ActionMessage,
    onClick: (ActionMessageItem) -> Unit = {}
) {
    Column(
        modifier = Modifier.weight(1f).clickable { onClick(action.action) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(action.icon),
            contentDescription = "Icon",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Text(
            text = stringResource(action.name),
            style = MaterialTheme.typography.caption,
            maxLines = 1
        )
    }
}


/**
 * Composable function to display an emote item in a row.
 *
 * @param emote The emote message to display.
 * @param onClick Lambda function to be called when the emote item is clicked.
 */
@Composable
private fun RowScope.ItemEmoteMessage(
    emote: EmoteMessage,
    onClick: (EmoteMessageItem) -> Unit
) {
    Box(
        modifier = Modifier.weight(1f).clickable { onClick(emote.emote) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(emote.icon),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }
}

private data class ActionMessage(
    val name: StringResource,
    val icon: DrawableResource,
    val action: ActionMessageItem
)

private data class EmoteMessage(
    val icon: DrawableResource,
    val emote: EmoteMessageItem
)

private fun getListAction(isSelf: Boolean): List<ActionMessage> {
    return if (isSelf) listActionMessageName
    else listActionMessageName.dropLast(1)
}

private val listEmoteMessageName =
    listOf(
        EmoteMessage(
            icon = Res.drawable.ic_thumb_up,
            emote = EmoteMessageItem.Like
        ),
        EmoteMessage(
            icon = Res.drawable.emote_clap,
            emote = EmoteMessageItem.Clap
        ),
        EmoteMessage(
            icon = Res.drawable.emote_cheer,
            emote = EmoteMessageItem.Cheer
        ),
        EmoteMessage(
            icon = Res.drawable.emote_beg,
            emote = EmoteMessageItem.Beg
        ),
        EmoteMessage(
            icon = Res.drawable.emote_happy,
            emote = EmoteMessageItem.Happy
        ),
        EmoteMessage(
            icon = Res.drawable.emote_cry,
            emote = EmoteMessageItem.Cry
        ),
        EmoteMessage(
            icon = Res.drawable.emote_laugh_cry,
            emote = EmoteMessageItem.Funny
        ),
        EmoteMessage(
            icon = Res.drawable.emote_wow,
            emote = EmoteMessageItem.Wow
        ),
    )

private val listActionMessageName =
    listOf(
        ActionMessage(
            Res.string.reply,
            Res.drawable.ic_reply,
            ActionMessageItem.Reply
        ),
        ActionMessage(
            Res.string.copy,
            Res.drawable.ic_copy,
            ActionMessageItem.Copy
        ),
        ActionMessage(
            Res.string.delete,
            Res.drawable.ic_delete,
            ActionMessageItem.Delete
        ),
        ActionMessage(
            Res.string.forward,
            Res.drawable.ic_forward,
            ActionMessageItem.Forward
        ),
        ActionMessage(
            Res.string.to_me,
            Res.drawable.ic_forward_to_user,
            ActionMessageItem.ToMe
        ),
        ActionMessage(
            Res.string.bookmark,
            Res.drawable.ic_bookmark,
            ActionMessageItem.Bookmark
        ),
        ActionMessage(
            Res.string.send,
            Res.drawable.ic_send_message,
            ActionMessageItem.Send
        ),
        ActionMessage(
            Res.string.translate,
            Res.drawable.ic_translate,
            ActionMessageItem.Translate
        ),
        ActionMessage(
            Res.string.edit,
            Res.drawable.ic_edit,
            ActionMessageItem.Edit
        )
    )

sealed class ActionMessageItem {
    data object Reply : ActionMessageItem()
    data object Copy : ActionMessageItem()
    data object Delete : ActionMessageItem()
    data object Forward : ActionMessageItem()
    data object ToMe : ActionMessageItem()
    data object Bookmark : ActionMessageItem()
    data object Send : ActionMessageItem()
    data object Translate : ActionMessageItem()
    data object Edit : ActionMessageItem()
}

sealed class EmoteMessageItem(val code: String) {
    data object Like : EmoteMessageItem(":emo:like")
    data object Clap : EmoteMessageItem(":emo:clap")
    data object Cheer : EmoteMessageItem(":emo:cheer")
    data object Beg : EmoteMessageItem(":emo:beg")
    data object Happy : EmoteMessageItem(":emo:happy")
    data object Cry : EmoteMessageItem(":emo:cry")
    data object Funny : EmoteMessageItem(":emo:funny")
    data object Wow : EmoteMessageItem(":emo:wow")
}
