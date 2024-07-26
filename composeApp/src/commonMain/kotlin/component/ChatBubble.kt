package component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.ic_thumb_up
import domain.models.MessageModel
import org.jetbrains.compose.resources.painterResource


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
                LongPressMenu(
                    onDismiss = onDismissMenu,
                    modifier = Modifier.widthIn(min = 100.dp, max = maxWidth * 0.6f)
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

@Composable
fun LongPressMenu(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Reply", style = MaterialTheme.typography.caption)
            Text(text = "Forward", style = MaterialTheme.typography.caption)
            Text(text = "Delete", style = MaterialTheme.typography.caption)
            Text(text = "Test", style = MaterialTheme.typography.caption)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Reply", style = MaterialTheme.typography.caption)
            Text(text = "Forward", style = MaterialTheme.typography.caption)
            Text(text = "Delete", style = MaterialTheme.typography.caption)
            Text(text = "Test", style = MaterialTheme.typography.caption)
        }
        Divider(Modifier.fillMaxWidth().height(1.dp).background(Color.Black))
    }
}