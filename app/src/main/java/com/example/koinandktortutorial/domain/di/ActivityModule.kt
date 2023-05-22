package com.example.koinandktortutorial.domain.di

import com.example.koinandktortutorial.ui.MainActivity
import org.koin.dsl.module

// This activity module will only last if activity is active
val activityModule = module {
    scope<MainActivity> {
        scoped {
            "Hello"
        }
    }
}