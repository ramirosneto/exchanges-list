package br.com.ramirosneto.exchanges.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.ramirosneto.exchanges.app.presentation.ui.ExchangeDetailScreen
import br.com.ramirosneto.exchanges.app.presentation.ui.ExchangeListScreen
import br.com.ramirosneto.exchanges.app.presentation.ui.theme.ExchangeslistTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchangeslistTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "exchangeList") {
                    composable("exchangeList") { ExchangeListScreen(navController) }
                    composable("exchangeDetail/{exchangeId}") { backStackEntry ->
                        ExchangeDetailScreen(
                            exchangeId = backStackEntry.arguments?.getString("exchangeId") ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
