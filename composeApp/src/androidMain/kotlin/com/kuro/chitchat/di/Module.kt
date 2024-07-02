package com.kuro.chitchat.di

import PlatformScreen
import PlatformScreenModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.AuthViewModel

val appModule = module {
    viewModel { AuthViewModel(get()) }
    factory<PlatformScreen> { PlatformScreenModule() }
}