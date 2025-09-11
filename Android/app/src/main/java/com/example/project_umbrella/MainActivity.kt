package com.example.project_umbrella

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.project_umbrella.ui.NavBar
import com.example.project_umbrella.ui.navigation.InventoryNavHost
import com.example.project_umbrella.ui.navigation.currentScreen
import com.example.project_umbrella.ui.navigation.navigateTo
import com.example.project_umbrella.ui.screens.HomeScreens.HomeDestination
import com.example.project_umbrella.ui.screens.inventory.InventoryDestination
import com.example.project_umbrella.ui.screens.inventory.ProductDetailsDestination
import com.example.project_umbrella.ui.theme.ProjectumbrellaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectumbrellaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    InventoryApp()
                }
            }
        }
    }
}

@Composable
fun InventoryApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            bottomBar = {
                if(navController.currentScreen() != ProductDetailsDestination.route)
                    navController.currentScreen()?.let {
                        NavBar(
                            navigateToHome = {
                                navController.navigateTo(
                                    HomeDestination
                                )
                                             },
                            navigateToInventory = {
                                navController.navigateTo(
                                    InventoryDestination
                                )
                                                  },
                            navigateToSettings = {},
                            navigateToGraphs =  {},
                            currentScreen = it
                        )
                    }
            }
        ) { innerPadding ->
            InventoryNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}