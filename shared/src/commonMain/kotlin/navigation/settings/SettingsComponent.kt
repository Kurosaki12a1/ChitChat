package navigation.settings

import com.arkivanov.decompose.ComponentContext

class SettingsComponent(
    componentContext: ComponentContext,
    private val onPop: () -> Unit
) : ComponentContext by componentContext {
    fun popBackStack() {
        onPop.invoke()
    }
}