package com.kuro.chitchat.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.AuthViewModel

val appModule = module {
    viewModel { AuthViewModel(get()) }
}