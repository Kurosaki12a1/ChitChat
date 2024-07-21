package component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.models.StatusUser

@Composable
fun StatusDot(
    status: String,
    size : Dp = 20.dp,
    radius : Float = 20f
) {
    when (status) {
        StatusUser.ONLINE.status -> {
            Canvas(
                modifier = Modifier.size(size),
                onDraw = {
                    drawCircle(
                        color = Color.Green,
                        radius = radius
                    )
                }
            )
        }

        StatusUser.BUSY.status -> {
            Canvas(
                modifier = Modifier.size(size),
                onDraw = {
                    drawCircle(
                        color = Color.Red,
                        radius = radius
                    )
                }
            )
        }

        StatusUser.AWAY.status -> {
            Canvas(
                modifier = Modifier.size(size),
                onDraw = {
                    drawCircle(
                        color = Color.Yellow,
                        radius = radius
                    )
                }
            )
        }

        StatusUser.OFFLINE.status -> {
            Canvas(
                modifier = Modifier.size(size),
                onDraw = {
                    drawCircle(
                        color = Color.Gray,
                        radius = radius,
                        style = Stroke(
                            width = radius / 2
                        )
                    )
                }
            )
        }
    }
}