package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.ui.navigation.AppNavHost
import ru.practicum.android.diploma.ui.theme.MyAppTheme

class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)

        setContent {
            MyAppTheme {
                AppNavHost()
            }
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
