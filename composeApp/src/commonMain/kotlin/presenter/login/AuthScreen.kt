package presenter.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import domain.model.ApiRequest
import domain.model.ApiResponse
import navigation.auth.AuthComponent
import org.koin.compose.koinInject
import presenter.login.component.AuthContent
import presenter.login.component.AuthTopBar
import utils.RequestState
import viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    component: AuthComponent,
    viewModel: AuthViewModel = koinInject()
) {
    val signedInState by viewModel.signedInState
    val messageBarState by viewModel.messageBarState
    val apiResponse by viewModel.apiResponse

    LaunchedEffect(apiResponse) {
        when (apiResponse) {
            is RequestState.Success -> {
                val response = (apiResponse as RequestState.Success<ApiResponse>).data
                if (response.success) {
                    component.navigateToNextScreen()
                } else {
                    viewModel.saveSignedInState(signedIn = false)
                }
            }

            is RequestState.Error -> {
                viewModel.saveSignedInState(signedIn = false)
            }

            else -> {

            }
        }
    }

    Scaffold(
        topBar = { AuthTopBar() },
        content = {
            AuthContent(
                signedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    viewModel.saveSignedInState(signedIn = true)
                },
                onResult = { account ->
                    if (account == null) {
                        viewModel.updateMessageBarState()
                        viewModel.saveSignedInState(false)
                    } else {
                        viewModel.verifyTokenOnBackend(ApiRequest(tokenId = account.idToken))
                    }
                }
            )
        }
    )
}