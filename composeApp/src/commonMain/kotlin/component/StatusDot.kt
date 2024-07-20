package component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import domain.models.StatusUser

@Composable
fun StatusDot(
    status: String
) {
    when (status) {
        StatusUser.ONLINE.status -> {
            Canvas(
                modifier = Modifier.size(20.dp),
                onDraw = {
                    drawCircle(
                        color = Color.Green,
                        radius = 20f
                    )
                }
            )
        }

        StatusUser.BUSY.status -> {
            Canvas(
                modifier = Modifier.size(20.dp),
                onDraw = {
                    drawCircle(
                        color = Color.Red,
                        radius = 20f
                    )
                }
            )
        }

        StatusUser.AWAY.status -> {
            Canvas(
                modifier = Modifier.size(20.dp),
                onDraw = {
                    drawCircle(
                        color = Color.Yellow,
                        radius = 20f
                    )
                }
            )
        }

        StatusUser.OFFLINE.status -> {
            Canvas(
                modifier = Modifier.size(20.dp),
                onDraw = {
                    drawCircle(
                        color = Color.Gray,
                        radius = 20f,
                        style = Stroke(
                            width = 10f
                        )
                    )
                }
            )
        }
    }
}