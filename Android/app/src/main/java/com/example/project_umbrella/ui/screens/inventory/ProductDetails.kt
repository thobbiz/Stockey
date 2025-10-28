package com.example.project_umbrella.ui.screens.inventory

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_umbrella.R
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.ui.AppViewModelProvider
import com.example.project_umbrella.ui.navigation.NavigationDestination
import com.example.project_umbrella.ui.theme.ProjectumbrellaTheme
import kotlinx.coroutines.launch

object ProductDetailsDestination : NavigationDestination {
    override val route = "product_details"
    override val titleRes = R.string.product_details
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navigateToEditItem: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: ProductDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            InventoryScreenTopAppBar(title = stringResource(id = R.string.product_details))
        }
    ) {innerPadding ->
        ProductDetailsBody(
            productDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteProduct()
                    navigateBack()
                }
            },
            onEditProduct = navigateToEditItem,
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

@Composable
private fun ProductDetailsBody(
    productDetailsUiState: ProductDetailsUiState,
    onEditProduct: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        
        ProductDetails(
            product = productDetailsUiState.productInfo.toProduct(),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onEditProduct,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff0081f7),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(5.dp),
                enabled = true
            ) {
                Text(stringResource(R.string.edit), style = MaterialTheme.typography.bodyMedium)
            }
            OutlinedButton(
                onClick = { deleteConfirmationRequired = true },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    stringResource(R.string.delete),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        if(deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = { 
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ProductDetails(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
           ProductDetailsRow(
               labelResId = R.string.product,
               productDetail = product.name,
               modifier = Modifier.padding(
                   horizontal = 16.dp
               )
           )
            ProductDetailsRow(
                labelResId = R.string.quantity_in_store,
                productDetail = "${product.quantity} ${product.measurementUnit}",
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            ProductDetailsRow(
                labelResId = R.string.selling_price,
                productDetail = product.formatedSellingPrice(),
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            ProductDetailsRow(
                labelResId = R.string.cost_price,
                productDetail = product.formatedCostPrice(),
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }
    }
}

@Composable
private fun ProductDetailsRow(
    @StringRes labelResId: Int,
    productDetail: String,
    modifier: Modifier = Modifier
) {
        Row(modifier = modifier) {
            Text(text = stringResource(labelResId), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = productDetail, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    ProjectumbrellaTheme {
        ProductDetailsBody(
            ProductDetailsUiState (
                    outOfStock = true,
                productInfo = ProductInfo(4, "Coaster", "3100.0", "2000.0", "57", measurementUnit = "packs")
                    ),
            onEditProduct = { /*TODO*/ },
            onDelete = { /*TODO*/ }
        )
    }
}