package presenter.add_chat.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.search_chat_placeholder
import org.jetbrains.compose.resources.stringResource
import ui.theme.BackgroundColorEmphasis
import ui.theme.ForegroundEmphasisDefaultDefault

@Composable
fun AddChatSearch(
    searchValue: String,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    TextField(
        value = searchValue,
        onValueChange = { onTextChange(it) },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = BackgroundColorEmphasis,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(Res.string.search_chat_placeholder),
                style = MaterialTheme.typography.body2,
                color = ForegroundEmphasisDefaultDefault
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch.invoke() }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }

    )
}