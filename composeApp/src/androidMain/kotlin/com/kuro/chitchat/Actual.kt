import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.kuro.chitchat.common.StartActivityForResult
import com.kuro.chitchat.common.signIn
import domain.model.ApiRequest
import navigation.NavigationItem
import viewmodel.AuthViewModel

actual class PlatformScreenModule : PlatformScreen {
    @Composable
    actual override fun Content(screen: NavigationItem, viewModel: ViewModel) {
        when (screen) {
            is NavigationItem.AuthScreen -> {
                if (viewModel is AuthViewModel) {
                    val activity = LocalContext.current as Activity

                    StartActivityForResult(
                        key = viewModel.signedInState.value,
                        onResultReceived = { tokenId ->
                            viewModel.verifyTokenOnBackend(
                                request = ApiRequest(tokenId = tokenId)
                            )
                        },
                        onDialogDismissed = {
                            viewModel.saveSignedInState(signedIn = false)
                        }
                    ) { activityLauncher ->
                        if (viewModel.signedInState.value) {
                            signIn(
                                activity = activity,
                                launchActivityResult = { intentSenderRequest ->
                                    activityLauncher.launch(intentSenderRequest)
                                },
                                accountNotFound = {
                                    viewModel.saveSignedInState(signedIn = false)
                                    viewModel.updateMessageBarState()
                                }
                            )
                        }
                    }
                }
            }

            else -> {

            }
        }
    }
}
