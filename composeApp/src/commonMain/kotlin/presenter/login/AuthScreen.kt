package presenter.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import data.model.dto.ApiResponse
import domain.models.ApiRequest
import navigation.auth.AuthComponent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presenter.login.component.AuthContent
import presenter.login.component.AuthTopBar
import utils.RequestState
import viewmodel.AuthViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AuthScreen(
    component: AuthComponent,
    viewModel: AuthViewModel = koinViewModel()
) {
    val signedInState by viewModel.signedInState
    val loginState by viewModel.loginState
    val apiResponse by viewModel.apiResponse

    LaunchedEffect(Unit) {
        viewModel.init()
    }

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
                loadingState = apiResponse is RequestState.Loading,
                loginState = loginState,
                onButtonClicked = { viewModel.saveSignedInState(signedIn = true) },
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