package com.example.project_umbrella.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import com.example.project_umbrella.ui.screens.HomeScreens.SaleDestination
import com.example.project_umbrella.ui.screens.HomeScreens.SaleScreen
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
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            HomeScreen(
                navigateToSale = { navController.navigate(SaleDestination.route) }
            )
        }

        composable (
            route = InventoryDestination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            InventoryScreen(
                navigateToProductEntry = { navController.navigate(AddProductDestination.route) },
                navigateToProductDetail = { navController.navigate("${ProductDetailsDestination.route}/${it}") }
            )
        }

        composable (
            route  = AddProductDestination.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(250)
                ) + fadeIn(
                    animationSpec = tween(250),
                    initialAlpha = 0.5f
                )
                              },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutVertically (
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(250)
                ) + fadeOut(
                    animationSpec = tween(250),
                    targetAlpha = 0.5f
                )
            }
        ){
            AddProductScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg) { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(250))
            },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(250)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(250)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(250)) }
        ){
            ProductDetailsScreen(
                navigateToEditItem = { navController.navigate("${EditProductDestination.route}/${it}") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable (
            route =  EditProductDestination.routeWithArgs,
            arguments = listOf(navArgument(EditProductDestination.productIdArg) { type = NavType.IntType }),
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(250)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(250)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(250)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(250)) }
        ) {
            EditProductScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        composable (
            route =  SaleDestination.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(250)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(250)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(250)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(250)) }
        ) {
            SaleScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}

fun NavHostController.navigateTo(navigationDestination: NavigationDestination) {
    this.navigate(navigationDestination.route) {
        anim {
            enter = 0
            exit = 0
            popEnter = 0
            popExit = 0
        }
        launchSingleTop = true
        popUpTo(navigationDestination.route)
    }
}

@Composable
fun NavHostController.currentScreen(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}