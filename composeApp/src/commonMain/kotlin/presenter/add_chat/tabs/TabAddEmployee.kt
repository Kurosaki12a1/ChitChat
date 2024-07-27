package presenter.add_chat.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.img_placeholder
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import component.StatusDot
import domain.models.StatusUser
import domain.models.UserModel
import org.jetbrains.compose.resources.painterResource
import utils.extension.noRippleClickAble
import utils.now

@Composable
fun TabAddEmployee(
    listUser: List<UserModel>,
    onClick: (UserModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "search result") {
            Text(
                text = "Search Result: (${listTest.size})",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
        items(listTest, key = { it.userId }) { user ->
            ItemEmployee(
                user = user,
                onClick = {
                    onClick(user)
                }
            )
        }
    }
}

private val listTest = listOf(
    UserModel(
        userId = "123",
        name = "Test 1",
        "Test1@gmail.com",
        "https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187.jpg?w=1436&h=958",
        now(),
        StatusUser.ONLINE.status
    ),
    UserModel(
        userId = "456",
        name = "Test 2",
        "Test2@gmail.com",
        "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=1350",
        now(),
        StatusUser.OFFLINE.status
    ),
    UserModel(
        userId = "789",
        name = "Test 3",
        "Test3@gmail.com",
        "https://static01.nyt.com/images/2021/09/14/science/07CAT-STRIPES/07CAT-STRIPES-superJumbo.jpg?quality=75&auto=webp",
        now(),
        StatusUser.AWAY.status
    ),
    UserModel(
        userId = "156",
        name = "Test 4",
        "Test4@gmail.com",
        "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png",
        now(),
        StatusUser.BUSY.status
    ),
)


@Composable
private fun ItemEmployee(
    user: UserModel,
    onClick: (UserModel) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp).noRippleClickAble { onClick(user) }
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(48.dp).clip(CircleShape),
            model = ImageRequest.Builder(LocalPlatformContext.current).data(user.profilePhoto)
                .crossfade(durationMillis = 1000).scale(Scale.FILL).build(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.img_placeholder),
            error = painterResource(Res.drawable.img_placeholder),
            contentDescription = "Avatar"
        )
        StatusDot(user.status)
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = user.emailAddress,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}
