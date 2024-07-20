package presenter.add_chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.create_announcement
import chitchatmultiplatform.composeapp.generated.resources.ic_cancel
import chitchatmultiplatform.composeapp.generated.resources.img_placeholder
import chitchatmultiplatform.composeapp.generated.resources.start_new_chat
import chitchatmultiplatform.composeapp.generated.resources.start_secret_chat
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kuro.chitchat.messagebar.MessageBar
import com.kuro.chitchat.messagebar.rememberMessageBarState
import data.model.dto.ChatRoomDto
import data.model.dto.UserDto
import data.model.toModel
import domain.models.RoomType
import domain.models.UserModel
import navigation.add_chat.AddChatComponent
import navigation.add_chat.TabAddChatChild
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presenter.add_chat.component.AddChatSearch
import presenter.add_chat.component.AddChatTab
import presenter.add_chat.component.AddChatTopBar
import presenter.add_chat.tabs.TabAddChat
import presenter.add_chat.tabs.TabAddContacts
import presenter.add_chat.tabs.TabAddEmployee
import ui.theme.BackgroundColorEmphasis
import utils.RequestState
import utils.extension.noRippleClickAble
import viewmodel.AddChatViewModel

@KoinExperimentalAPI
@Composable
fun AddChatScreen(
    component: AddChatComponent,
    viewModel: AddChatViewModel = koinViewModel()
) {
    val searchText by derivedStateOf { viewModel.searchValue.value }
    val childStack by component.childStack.subscribeAsState()
    val searchResponse by viewModel.searchResponse
    val createChatResponse by viewModel.createChatRoomResponse
    val listUserSelected = viewModel.userSelected
    val isSelected by derivedStateOf { listUserSelected.isNotEmpty() }

    val messageBarState = rememberMessageBarState()
    val listResult = remember { mutableStateListOf<UserDto>() }

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    LaunchedEffect(searchResponse) {
        when (searchResponse) {
            is RequestState.Error -> {
                messageBarState.addError((searchResponse as RequestState.Error).exception)
            }

            is RequestState.Success -> {
                listResult.addAll((searchResponse as RequestState.Success).data)
            }

            else -> {

            }
        }
    }

    LaunchedEffect(createChatResponse) {
        when (createChatResponse) {
            is RequestState.Error -> {
                messageBarState.addError((createChatResponse as RequestState.Error).exception)
            }

            is RequestState.Success -> {
                println("Test create chat: ${(createChatResponse as RequestState.Success<ChatRoomDto?>).data}")
            }

            else -> {

            }
        }
    }

    MessageBar(
        messageBarState = messageBarState,
        showCopyButton = false
    ) {
        Scaffold(
            topBar = {
                Column {
                    AddChatTopBar(
                        title = stringResource(getTitle(component.title)),
                        userSelected = listUserSelected.size,
                        onSelectClick = {
                            viewModel.createRoomAndInvite(listUserSelected)
                        },
                        onCancel = { component.pop() }
                    )
                    if (isSelected) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            listUserSelected.forEach { selectedUser ->
                                SelectedEmployee(
                                    user = selectedUser,
                                    onRemove = {
                                        viewModel.updateListSelected(selectedUser)
                                    }
                                )
                            }
                        }
                    }
                    AddChatSearch(
                        searchValue = searchText,
                        onTextChange = { viewModel.onSearchTextChange(it) },
                        onSearch = { viewModel.search() }
                    )
                    AddChatTab(
                        navigation = childStack,
                        onTabSelected = { item -> component.onTabSelect(item) }
                    )
                }
            }
        ) {
            Children(
                modifier = Modifier.fillMaxWidth().background(BackgroundColorEmphasis).padding(it),
                stack = childStack,
                animation = stackAnimation(slide() + fade())
            ) { child ->
                when (child.instance) {
                    is TabAddChatChild.EmployeeScreen -> {
                        TabAddEmployee(listResult.map { result -> result.toModel() }) { user ->
                            viewModel.updateListSelected(user)
                        }
                    }

                    is TabAddChatChild.ChatScreen -> {
                        TabAddChat()
                    }

                    is TabAddChatChild.ContactsScreen -> {
                        TabAddContacts()
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectedEmployee(
    user: UserModel,
    onRemove: (UserModel) -> Unit
) {
    Box {
        Image(
            modifier = Modifier.size(16.dp)
                .background(Color.LightGray, CircleShape)
                .align(Alignment.TopEnd)
                .zIndex(1f)
                .noRippleClickAble { onRemove(user) },
            painter = painterResource(Res.drawable.ic_cancel),
            colorFilter = ColorFilter.tint(Color.White),
            contentScale = ContentScale.Inside,
            contentDescription = "Remove"
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp).clip(CircleShape),
                model = ImageRequest.Builder(LocalPlatformContext.current).data(user.profilePhoto)
                    .crossfade(durationMillis = 1000).scale(Scale.FILL).build(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.img_placeholder),
                error = painterResource(Res.drawable.img_placeholder),
                contentDescription = "Avatar"
            )
            Text(
                modifier = Modifier,
                text = user.name,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                minLines = 1,
                softWrap = false
            )
        }
    }

}


private fun getTitle(title: String): StringResource {
    return when (title) {
        RoomType.NORMAL.type -> Res.string.start_new_chat
        RoomType.SECRET.type -> Res.string.start_secret_chat
        else -> Res.string.create_announcement
    }
}
