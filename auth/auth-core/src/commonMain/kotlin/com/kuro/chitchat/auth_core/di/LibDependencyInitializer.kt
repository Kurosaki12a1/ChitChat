package com.kuro.chitchat.auth_core.di


import com.kuro.chitchat.auth_core.KMPAuthInternalApi
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

@KMPAuthInternalApi
object LibDependencyInitializer {

    @KMPAuthInternalApi
    var koinApp: KoinApplication? = null
        private set

    @KMPAuthInternalApi
    fun initialize(module: Module) {
        initialize(listOf(module))
    }

    @KMPAuthInternalApi
    fun initialize(modules: List<Module> = emptyList()) {
        if (isInitialized()) return
        val configModule = module {
            includes(modules)
        }
        koinApp = koinApplication {
            modules(configModule + platformModuleAuth)
        }.also {
            it.koin.onLibraryInitialized()
        }

    }

    private fun isInitialized() = koinApp != null


}

private fun Koin.onLibraryInitialized() {
    println("Library is initialized")
}

