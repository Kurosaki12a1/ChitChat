package presenter.more.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.apps
import chitchatmultiplatform.composeapp.generated.resources.default_services
import chitchatmultiplatform.composeapp.generated.resources.ic_apps
import chitchatmultiplatform.composeapp.generated.resources.ic_memo
import chitchatmultiplatform.composeapp.generated.resources.ic_notice
import chitchatmultiplatform.composeapp.generated.resources.ic_support
import chitchatmultiplatform.composeapp.generated.resources.ic_task
import chitchatmultiplatform.composeapp.generated.resources.memo
import chitchatmultiplatform.composeapp.generated.resources.notice
import chitchatmultiplatform.composeapp.generated.resources.support
import chitchatmultiplatform.composeapp.generated.resources.task
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColumnScope.ServicesBar(
    onClick: (Int) -> Unit
) {
    Text(
        modifier = Modifier.offset(x = 16.dp),
        text = stringResource(Res.string.default_services),
        style = MaterialTheme.typography.body2,
        fontWeight = FontWeight.Bold
    )
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        listItemServices.chunked(4).forEach { list ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                list.forEach { item ->
                    ItemServicesBar(
                        modifier = Modifier,
                        itemServices = item,
                        onClick = { onClick(item.index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemServicesBar(
    modifier: Modifier,
    itemServices: ItemServices,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onClick(itemServices.index)
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            painter = painterResource(itemServices.icon),
            contentDescription = stringResource(itemServices.name),
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(itemServices.name),
            style = MaterialTheme.typography.caption
        )
    }
}

data class ItemServices(
    val name: StringResource,
    val icon: DrawableResource,
    val index: Int
)

enum class MoreServices {
    MEMO, TASK, APPS, NOTICE, SUPPORT
}

private val listItemServices = listOf(
    ItemServices(
        name = Res.string.memo,
        icon = Res.drawable.ic_memo,
        index = MoreServices.MEMO.ordinal
    ),
    ItemServices(
        name = Res.string.task,
        icon = Res.drawable.ic_task,
        index = MoreServices.TASK.ordinal
    ),
    ItemServices(
        name = Res.string.apps,
        icon = Res.drawable.ic_apps,
        index = MoreServices.APPS.ordinal
    ),
    ItemServices(
        name = Res.string.notice,
        icon = Res.drawable.ic_notice,
        index = MoreServices.NOTICE.ordinal
    ),
    ItemServices(
        name = Res.string.support,
        icon = Res.drawable.ic_support,
        index = MoreServices.SUPPORT.ordinal
    )
)