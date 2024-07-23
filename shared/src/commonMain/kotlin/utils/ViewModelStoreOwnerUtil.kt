package utils

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlin.jvm.Synchronized

internal fun InstanceKeeperOwner.viewModelStoreOwner(): ViewModelStoreOwner =
    instanceKeeper.getOrCreate(::ViewModelStoreOwnerInstance)

private class ViewModelStoreOwnerInstance : ViewModelStoreOwner, InstanceKeeper.Instance {

    private var instance: ViewModelStore? = null

    private fun getInstance(): ViewModelStore {
        if (instance == null) {
            instance = ViewModelStore()
        }
        return instance!!
    }

    override val viewModelStore: ViewModelStore
        get() = getInstance()

    override fun onDestroy() {
        println("Có vào destroy không?")
        viewModelStore.clear()
    }

}