package presenter.more.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.ic_privacy
import chitchatmultiplatform.composeapp.generated.resources.ic_sign_out
import chitchatmultiplatform.composeapp.generated.resources.privacy
import chitchatmultiplatform.composeapp.generated.resources.sign_out
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.BackgroundColorEmphasis

@Composable
fun BoxScope.BottomActionBar(
    onPrivacyClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
        verticalArrangement = Arrangement.Center
    ) {
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(BackgroundColorEmphasis))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f).clickable { onPrivacyClick.invoke() }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.ic_privacy),
                    contentDescription = "Privacy"
                )
                Text(
                    text = stringResource(Res.string.privacy),
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Divider(
                modifier = Modifier.width(1.dp).height(20.dp).background(BackgroundColorEmphasis)
            )
            Row(
                modifier = Modifier.weight(1f).clickable { onSignOutClick.invoke() }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.ic_sign_out),
                    contentDescription = "Sign Out"
                )
                Text(
                    text = stringResource(Res.string.sign_out),
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

}