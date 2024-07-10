package navigation.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import kotlinx.serialization.Serializable

class SettingsComponent(
    componentContext: ComponentContext,
    private val onPop: () -> Unit
) : ComponentContext by componentContext {

    // StackNavigation for managing navigation items
    private val navigation = StackNavigation<SettingsOptions>()

    // Child stack for managing the current stack of child components
    val childStack = childStack(
        source = navigation,
        serializer = SettingsOptions.serializer(),
        initialConfiguration = SettingsOptions.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun onNavigateOptions(options: SettingsOptions) {
        navigation.pop()
        navigation.push(options)
    }

    fun pop() {
        navigation.pop()
    }

    fun popBackStack() {
        onPop.invoke()
    }

    private fun createChild(
        options: SettingsOptions,
        context: ComponentContext
    ): SettingsChild {
        return when (options) {
            SettingsOptions.Main -> SettingsChild.Main
            SettingsOptions.FirstGroup.Notifications -> SettingsChild.Notifications
            SettingsOptions.SecondGroup.Chat -> SettingsChild.Chat
            SettingsOptions.SecondGroup.Contacts -> SettingsChild.Contacts
            SettingsOptions.SecondGroup.Others -> SettingsChild.Others
            SettingsOptions.FourthGroup.ReleaseNote -> SettingsChild.ReleaseNote
            SettingsOptions.ThirdGroup.Display -> SettingsChild.Display
            SettingsOptions.ThirdGroup.PasswordAndLock -> SettingsChild.PasswordAndLock
            SettingsOptions.ThirdGroup.Sync -> SettingsChild.Sync
            SettingsOptions.FourthGroup.ServiceInformation -> SettingsChild.ServiceInformation
        }
    }

    // Check if the tab item should pop back stack
    private fun shouldPopBackStack(options: SettingsOptions): Boolean {
        return isPresentedInBackStack(options) && childStack.active.configuration != options
    }

    // Check if the tab item is already presented in the back stack
    private fun isPresentedInBackStack(options: SettingsOptions): Boolean {
        childStack.backStack.forEach {
            if (it.configuration == options) return true
        }
        return false
    }
}

@Serializable
sealed class SettingsOptions(val index: Int) {

    @Serializable
    data object Main : SettingsOptions(0)

    @Serializable
    sealed class FirstGroup : SettingsOptions(1) {
        @Serializable
        data object Notifications : FirstGroup()
    }

    @Serializable
    sealed class SecondGroup : SettingsOptions(2) {
        @Serializable
        data object Chat : SecondGroup()

        @Serializable
        data object Contacts : SecondGroup()

        @Serializable
        data object Others : SecondGroup()

    }

    @Serializable
    sealed class ThirdGroup : SettingsOptions(3) {
        @Serializable
        data object Display : ThirdGroup()

        @Serializable
        data object Sync : ThirdGroup()

        @Serializable
        data object PasswordAndLock : ThirdGroup()
    }

    @Serializable
    sealed class FourthGroup : SettingsOptions(4) {
        @Serializable
        data object ServiceInformation : FourthGroup()

        @Serializable
        data object ReleaseNote : FourthGroup()
    }
}

@Serializable
sealed class SettingsChild {
    @Serializable
    data object Main : SettingsChild()

    @Serializable
    data object Notifications : SettingsChild()

    @Serializable
    data object Chat : SettingsChild()

    @Serializable
    data object Contacts : SettingsChild()

    @Serializable
    data object Others : SettingsChild()

    @Serializable
    data object Display : SettingsChild()

    @Serializable
    data object Sync : SettingsChild()

    @Serializable
    data object PasswordAndLock : SettingsChild()

    @Serializable
    data object ServiceInformation : SettingsChild()

    @Serializable
    data object ReleaseNote : SettingsChild()
}

