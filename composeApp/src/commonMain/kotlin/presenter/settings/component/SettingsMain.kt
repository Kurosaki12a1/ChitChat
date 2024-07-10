package presenter.settings.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.chat
import chitchatmultiplatform.composeapp.generated.resources.chat_unfocus
import chitchatmultiplatform.composeapp.generated.resources.contacts
import chitchatmultiplatform.composeapp.generated.resources.contacts_unfocus
import chitchatmultiplatform.composeapp.generated.resources.display
import chitchatmultiplatform.composeapp.generated.resources.ic_display
import chitchatmultiplatform.composeapp.generated.resources.ic_horizontal_more
import chitchatmultiplatform.composeapp.generated.resources.ic_info
import chitchatmultiplatform.composeapp.generated.resources.ic_lock
import chitchatmultiplatform.composeapp.generated.resources.ic_notifications
import chitchatmultiplatform.composeapp.generated.resources.ic_release_note
import chitchatmultiplatform.composeapp.generated.resources.ic_sync
import chitchatmultiplatform.composeapp.generated.resources.notifications
import chitchatmultiplatform.composeapp.generated.resources.others
import chitchatmultiplatform.composeapp.generated.resources.password_and_lock
import chitchatmultiplatform.composeapp.generated.resources.release_note
import chitchatmultiplatform.composeapp.generated.resources.service_information
import chitchatmultiplatform.composeapp.generated.resources.settings_route
import chitchatmultiplatform.composeapp.generated.resources.sync
import navigation.settings.SettingsOptions
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.BackgroundColorEmphasis
import utils.extension.noRippleClickAble

@Composable
fun SettingsMain(
    onClick: (SettingsOptions) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        listSettingsOptions
            .filter { it.option.index > 0 }
            .groupBy { it.option.index }
            .forEach { data ->
                data.value.forEach { option ->
                    SettingsOptionsItem(
                        options = option,
                        onClick = { onClick(option.option) }
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(BackgroundColorEmphasis)
                    )
                }
                // Last Group
                if (data.value[0].option.index != SettingsOptions.FourthGroup.ReleaseNote.index)
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(BackgroundColorEmphasis)
                    )
            }
    }
}

@Composable
fun SettingsOptionsItem(
    options: SettingsItem,
    onClick: (SettingsOptions) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().noRippleClickAble { onClick(options.option) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(options.icon),
            contentDescription = stringResource(options.name)
        )
        Text(
            text = stringResource(options.name),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Normal
        )
    }
}

data class SettingsItem(
    val icon: DrawableResource,
    val name: StringResource,
    val option: SettingsOptions
)

val listSettingsOptions = listOf(
    SettingsItem(
        icon = Res.drawable.ic_notifications,
        name = Res.string.settings_route,
        option = SettingsOptions.Main
    ),
    SettingsItem(
        icon = Res.drawable.ic_notifications,
        name = Res.string.notifications,
        option = SettingsOptions.FirstGroup.Notifications
    ),
    SettingsItem(
        icon = Res.drawable.chat_unfocus,
        name = Res.string.chat,
        option = SettingsOptions.SecondGroup.Chat
    ),
    SettingsItem(
        icon = Res.drawable.contacts_unfocus,
        name = Res.string.contacts,
        option = SettingsOptions.SecondGroup.Contacts
    ),
    SettingsItem(
        icon = Res.drawable.ic_horizontal_more,
        name = Res.string.others,
        option = SettingsOptions.SecondGroup.Others
    ),
    SettingsItem(
        icon = Res.drawable.ic_display,
        name = Res.string.display,
        option = SettingsOptions.ThirdGroup.Display
    ),
    SettingsItem(
        icon = Res.drawable.ic_sync,
        name = Res.string.sync,
        option = SettingsOptions.ThirdGroup.Sync
    ),
    SettingsItem(
        icon = Res.drawable.ic_lock,
        name = Res.string.password_and_lock,
        option = SettingsOptions.ThirdGroup.PasswordAndLock
    ),
    SettingsItem(
        icon = Res.drawable.ic_info,
        name = Res.string.service_information,
        option = SettingsOptions.FourthGroup.ServiceInformation
    ),
    SettingsItem(
        icon = Res.drawable.ic_release_note,
        name = Res.string.release_note,
        option = SettingsOptions.FourthGroup.ReleaseNote
    )
)