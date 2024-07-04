package com.kuro.chitchat.di

import PlatformScreen
import PlatformScreenModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.AuthViewModel
import viewmodel.SetUpViewModel

val appModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SetUpViewModel(get(), get()) }
    factory<PlatformScreen> { PlatformScreenModule() }
}