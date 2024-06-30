package navigation.home

import com.arkivanov.decompose.ComponentContext

class HomeComponent(
    componentContext: ComponentContext,
    private val onNavigateTo: (String) -> Unit
) : ComponentContext by componentContext {

}