package presenter.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kuro.chitchat.auth_google.GoogleButtonUiContainer
import com.kuro.chitchat.auth_google.GoogleUser
import com.kuro.chitchat.messagebar.MessageBar
import com.kuro.chitchat.messagebar.rememberMessageBarState
import component.GoogleButton
import domain.models.LoginState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthContent(
    signedInState: Boolean,
    loadingState: Boolean,
    loginState: LoginState,
    onButtonClicked: () -> Unit,
    onResult: (GoogleUser?) -> Unit
) {
    val state = rememberMessageBarState()

    LaunchedEffect(loginState) {
        if (loginState.error != null) {
            state.addError(loginState.error!!)
        } else if (loginState.message != null) {
            state.addSuccess(loginState.message!!)
        }
    }

    MessageBar(
        messageBarState = state,
        showCopyButton = false
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CentralContent(
                signedInState = signedInState,
                loadingState = loadingState,
                onButtonClicked = onButtonClicked,
                onResult = onResult
            )
        }
    }
}

@Composable
fun CentralContent(
    signedInState: Boolean,
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    onResult: (GoogleUser?) -> Unit
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
    GoogleButtonUiContainer(onGoogleSignInResult = {
        onResult(it)
    }) {
        GoogleButton(
            loadingState = signedInState || loadingState,
            onClick = { onButtonClicked.invoke() }
        )

        LaunchedEffect(signedInState) {
            if (signedInState) {
                this@GoogleButtonUiContainer.onClick()
            }
        }
    }

}