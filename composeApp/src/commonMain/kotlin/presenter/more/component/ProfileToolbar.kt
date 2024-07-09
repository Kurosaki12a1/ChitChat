package presenter.more.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.ic_sync
import chitchatmultiplatform.composeapp.generated.resources.img_placeholder
import chitchatmultiplatform.composeapp.generated.resources.last_sync
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import domain.model.UserModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.HighlightDefaultDefault
import utils.extension.noRippleClickAble

@Composable
fun ProfileToolbar(
    userModel: UserModel,
    onProfileClick: () -> Unit,
    onSyncClick: () -> Unit
) {
    val day = userModel.lastActive.dayOfMonth.toString().padStart(2, '0')
    val month = userModel.lastActive.monthNumber.toString().padStart(2, '0')
    val year = userModel.lastActive.year
    val hour = userModel.lastActive.hour.toString().padStart(2, '0')
    val minute = userModel.lastActive.minute.toString().padStart(2, '0')

    Row(
        modifier = Modifier.noRippleClickAble { onProfileClick.invoke() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (userModel.profilePhoto.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(1.dp).border(1.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp).clip(CircleShape),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(userModel.profilePhoto)
                        .crossfade(durationMillis = 1000)
                        .scale(Scale.FILL)
                        .build(),
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(Res.drawable.img_placeholder),
                    contentDescription = "Avatar"
                )
            }
        } else {
            Image(
                painter = painterResource(Res.drawable.img_placeholder),
                modifier = Modifier.size(60.dp).clip(CircleShape),
                contentDescription = "Avatar"
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = userModel.name,
                color = Color.Black,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start
            )
            Text(
                text = userModel.emailAddress,
                style = MaterialTheme.typography.button,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .background(HighlightDefaultDefault, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(Res.string.last_sync),
            color = Color.Black,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$month-$day-$year $hour:$minute",
            color = Color.Black,
            style = MaterialTheme.typography.caption
        )
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.White, CircleShape)
                .noRippleClickAble { onSyncClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_sync),
                contentDescription = "Sync"
            )
        }
    }
}