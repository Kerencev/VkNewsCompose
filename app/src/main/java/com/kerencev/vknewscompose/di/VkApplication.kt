package com.kerencev.vknewscompose.di

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kerencev.vknewscompose.di.component.ApplicationComponent
import com.kerencev.vknewscompose.di.component.DaggerApplicationComponent

class VkApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(context = this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as VkApplication).component
}