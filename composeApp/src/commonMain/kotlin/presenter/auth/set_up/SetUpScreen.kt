package presenter.auth.set_up

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil3.annotation.ExperimentalCoilApi
import navigation.auth.SetUpComponent
import org.koin.compose.koinInject
import presenter.auth.set_up.component.SetUpContent
import presenter.auth.set_up.component.SetUpTopBar
import viewmodel.SetUpViewModel

@ExperimentalCoilApi
@Composable
fun SetUpScreen(
    component: SetUpComponent,
    viewModel: SetUpViewModel = koinInject()
) {
    val apiResponse by viewModel.apiResponse
    val messageBarState by viewModel.messageBarState

    val user = component.user
    // val user by viewModel.user
    /*val firstName by viewModel.firstName
    val lastName by viewModel.lastName*/

    Scaffold(
        topBar = {
            SetUpTopBar(
                onSave = {
                    viewModel.updateUserInfo()
                },
                onDeleteAllConfirmed = {
                    viewModel.deleteUser()
                }
            )
        },
        content = {
            SetUpContent(
                apiResponse = apiResponse,
                messageBarState = messageBarState,
                firstName = user.name,
                onFirstNameChanged = {
                    viewModel.updateFirstName(it)
                },
                lastName = user.name,
                onLastNameChanged = {
                    viewModel.updateLastName(it)
                },
                emailAddress = user.emailAddress,
                profilePhoto = user.profilePhoto,
                onConfirm = {
                    viewModel.setConfirmedState(
                        userId = user.id,
                        isConfirmed = true
                    )
                }
            )
        }
    )

}
