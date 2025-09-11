package com.example.project_umbrella.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.project_umbrella.ui.screens.HomeScreens.HomeDestination
import com.example.project_umbrella.ui.screens.HomeScreens.HomeScreen
import com.example.project_umbrella.ui.screens.inventory.AddProductDestination
import com.example.project_umbrella.ui.screens.inventory.AddProductScreen
import com.example.project_umbrella.ui.screens.inventory.EditProductDestination
import com.example.project_umbrella.ui.screens.inventory.EditProductScreen
import com.example.project_umbrella.ui.screens.inventory.InventoryDestination
import com.example.project_umbrella.ui.screens.inventory.InventoryScreen
import com.example.project_umbrella.ui.screens.inventory.ProductDetailsDestination
import com.example.project_umbrella.ui.screens.inventory.ProductDetailsScreen


@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(
            route = HomeDestination.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) }
        ) {
            HomeScreen()
        }

        composable (
            route = InventoryDestination.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) }
            ) {
            InventoryScreen(
                navigateToProductEntry = { navController.navigate(AddProductDestination.route) },
                navigateToProductDetail = { navController.navigate("${ProductDetailsDestination.route}/${it}") }
            )
        }

        composable (
            route  = AddProductDestination.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) }
            ){
            AddProductScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg) { type = NavType.IntType }),
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) }
        ){
            ProductDetailsScreen(
                navigateToEditItem = { navController.navigate(EditProductDestination.route) },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable (
            route =  EditProductDestination.routeWithArgs,
            arguments = listOf(navArgument(EditProductDestination.productIdArg) {
                type = NavType.IntType
            }),
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(500)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(500)) }
            ) {
            EditProductScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}

fun NavHostController.navigateTo(navigationDestination: NavigationDestination) {
    this.navigate(navigationDestination.route) {
        launchSingleTop = true
        popUpTo(navigationDestination.route)
    }
}

@Composable
fun NavHostController.currentScreen(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}