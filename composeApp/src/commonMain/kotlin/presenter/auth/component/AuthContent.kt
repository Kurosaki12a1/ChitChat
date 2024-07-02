package presenter.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.app_logo
import chitchatmultiplatform.composeapp.generated.resources.sign_in_to_continue
import chitchatmultiplatform.composeapp.generated.resources.sign_in_with_google
import component.GoogleButton
import component.MessageBar
import domain.model.MessageBarState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthContent(
    signedInState: Boolean,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1f)) {
            MessageBar(messageBarState = messageBarState)
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CentralContent(
                signedInState = signedInState,
                onButtonClicked = onButtonClicked
            )
        }
    }
}

@Composable
fun CentralContent(
    signedInState: Boolean,
    onButtonClicked: () -> Unit
) {
    Image(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .size(120.dp),
        painter = painterResource(Res.drawable.app_logo),
        contentDescription = "Google Logo"
    )
    Text(
        text = stringResource(Res.string.sign_in_with_google),
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.h5.fontSize
    )
    Text(
        modifier = Modifier
            .alpha(ContentAlpha.medium)
            .padding(bottom = 40.dp, top = 4.dp),
        text = stringResource(Res.string.sign_in_to_continue),
        fontSize = MaterialTheme.typography.subtitle1.fontSize,
        textAlign = TextAlign.Center
    )
    GoogleButton(
        loadingState = signedInState,
        onClick = onButtonClicked
    )
}