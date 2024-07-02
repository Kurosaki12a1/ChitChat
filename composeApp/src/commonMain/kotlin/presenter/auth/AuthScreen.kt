package presenter.auth

import PlatformScreen
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import domain.model.ApiResponse
import navigation.NavigationItem
import navigation.auth.AuthComponent
import org.koin.compose.koinInject
import presenter.auth.component.AuthContent
import presenter.auth.component.AuthTopBar
import utils.RequestState
import viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    component: AuthComponent,
    viewModel: AuthViewModel = koinInject(),
    platformScreen: PlatformScreen = koinInject()
) {
    val signedInState by viewModel.signedInState
    val messageBarState by viewModel.messageBarState
    val apiResponse by viewModel.apiResponse


    LaunchedEffect(apiResponse) {
        when (apiResponse) {
            is RequestState.Success -> {
                val response = (apiResponse as RequestState.Success<ApiResponse>).data
                if (response.success) {
                    component.navigateToHomeScreen()
                } else {
                    viewModel.saveSignedInState(signedIn = false)
                }
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            AuthTopBar()
        },
        content = {
            AuthContent(
                signedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    viewModel.saveSignedInState(signedIn = true)
                }
            )
        }
    )

    platformScreen.Content(NavigationItem.AuthScreen, viewModel)
}