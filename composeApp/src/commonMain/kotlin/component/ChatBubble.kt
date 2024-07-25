package component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp


enum class TrianglePosition {
    BottomRight, TopLeft
}

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    trianglePosition: TrianglePosition = TrianglePosition.BottomRight,
    isContinuous : Boolean = false,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier
                .widthIn(min = 100.dp, max = maxWidth * 0.7f)
                .align(if (trianglePosition == TrianglePosition.BottomRight) Alignment.BottomEnd else Alignment.TopStart)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val cornerRadius = 16.dp.toPx()
                val triangleSize = 20.dp.toPx()  // Kích thước của góc nhọn
                val totalHeight = size.height
                val reducedHeight = totalHeight * 0.9f  // Chiều cao giảm cho góc bo tròn
                val verticalPadding = totalHeight * 0.05f  // Padding trên và dưới

                val path = Path().apply {
                    moveTo(cornerRadius, verticalPadding)
                    lineTo(size.width - cornerRadius, verticalPadding)
                    arcTo(
                        rect = Rect(
                            size.width - 2 * cornerRadius, verticalPadding,
                            size.width, 2 * cornerRadius + verticalPadding
                        ),
                        startAngleDegrees = -90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    lineTo(size.width, reducedHeight - cornerRadius + verticalPadding)
                    arcTo(
                        rect = Rect(
                            size.width - 2 * cornerRadius,
                            reducedHeight - 2 * cornerRadius + verticalPadding,
                            size.width,
                            reducedHeight + verticalPadding
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    lineTo(cornerRadius, reducedHeight + verticalPadding)
                    arcTo(
                        rect = Rect(
                            0f, reducedHeight - 2 * cornerRadius + verticalPadding,
                            2 * cornerRadius, reducedHeight + verticalPadding
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    lineTo(0f, cornerRadius + verticalPadding)
                    arcTo(
                        rect = Rect(
                            0f, verticalPadding,
                            2 * cornerRadius, 2 * cornerRadius + verticalPadding
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    close()
                }

                drawPath(
                    path = path,
                    color = backgroundColor,
                    style = Fill
                )

                if (!isContinuous)  {
                    val trianglePath = Path().apply {
                        when (trianglePosition) {
                            TrianglePosition.BottomRight -> {
                                moveTo(
                                    size.width - cornerRadius - triangleSize,
                                    reducedHeight + verticalPadding
                                )
                                lineTo(
                                    size.width,
                                    reducedHeight + verticalPadding + triangleSize
                                )
                                // Kéo dài xuống dưới
                                lineTo(size.width - cornerRadius, reducedHeight + verticalPadding)
                                close()
                            }

                            TrianglePosition.TopLeft -> {
                                moveTo(0f, verticalPadding - triangleSize)  // Kéo lên trên
                                lineTo(triangleSize + cornerRadius, verticalPadding)
                                lineTo(cornerRadius, verticalPadding)
                                close()
                            }
                        }
                    }

                    drawPath(
                        path = trianglePath,
                        color = backgroundColor,
                        style = Fill
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                content()
            }
        }
    }

}