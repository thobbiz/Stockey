package com.example.project_umbrella.ui.screens.inventory

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_umbrella.R
import com.example.project_umbrella.ui.AppViewModelProvider
import com.example.project_umbrella.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object EditProductDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_product
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineSCope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            EditProductTopAppBar(
                title = stringResource(EditProductDestination.titleRes),
                canNavigateBack = true,
                navigateBack = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        AddProductBody(
            productUiState = viewModel.productUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineSCope.launch {
                    viewModel.updateProduct()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateBack: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cancel)
                    )
                }
            }
        }

    )
}
