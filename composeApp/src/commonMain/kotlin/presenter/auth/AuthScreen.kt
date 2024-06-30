package presenter.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.app_logo
import chitchatmultiplatform.composeapp.generated.resources.sign_in_hint
import chitchatmultiplatform.composeapp.generated.resources.sign_in_to_continue
import chitchatmultiplatform.composeapp.generated.resources.welcome_to
import component.GoogleButton
import component.MessageBar
import domain.model.ApiResponse
import navigation.auth.AuthComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.theme.BackGroundAppColor
import ui.theme.TextAppColor
import utils.RequestState
import viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    component: AuthComponent,
    viewModel: AuthViewModel = koinInject()
) {
    val signedInState = viewModel.signedInState
    val messageBarState = viewModel.messageBarState
    val apiResponse = viewModel.apiResponse


    LaunchedEffect(apiResponse) {
        when (apiResponse.value) {
            is RequestState.Success -> {
                val response = (apiResponse.value as RequestState.Success<ApiResponse>).data
                if (response.success) {
                    component.navigateToHomeScreen()
                } else {
                    viewModel.saveSignedInState(signedIn = false)
                }
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f),
            contentAlignment = Alignment.Center
        ) {
            MessageBar(messageBarState = messageBarState.value)
        }

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(Res.drawable.app_logo),
            contentDescription = "Chit Chat"
        )
        Text(
            text = stringResource(Res.string.welcome_to),
            style = MaterialTheme.typography.subtitle1,
            color = BackGroundAppColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(Res.string.sign_in_to_continue),
            style = MaterialTheme.typography.h4,
            color = TextAppColor,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(Res.string.sign_in_hint),
            style = MaterialTheme.typography.body2,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        GoogleButton(
            loadingState = signedInState.value,
            onClick = {
                viewModel.saveSignedInState(signedIn = true)
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}