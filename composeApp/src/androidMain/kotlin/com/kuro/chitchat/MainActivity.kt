package com.kuro.chitchat

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.retainedComponent
import domain.repository.remote.SocketRepository
import kotlinx.coroutines.launch
import navigation.RootComponent
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val socketRepository: SocketRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponent(it)
        }
        setContent {
            App(root)
        }
    }

    override fun onDestroy() {
        lifecycleScope.launch { socketRepository.disconnectToWebsocket() }
        super.onDestroy()
    }
}
