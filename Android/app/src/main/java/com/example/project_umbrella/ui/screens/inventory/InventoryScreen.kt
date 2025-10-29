package com.example.project_umbrella.ui.screens.inventory

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_umbrella.R
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.ui.AppViewModelProvider
import com.example.project_umbrella.ui.navigation.NavigationDestination

object InventoryDestination: NavigationDestination {
    override val route = "Inventory"
    override val titleRes = R.string.inventory
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    navigateToProductEntry: () -> Unit,
    navigateToProductDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InventoryScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val inventoryUiState by viewModel.inventoryUiState.collectAsState()
    val totalProducts by viewModel.totalProducts.collectAsState()
    val totalStocks by viewModel.totalStockCount.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxSize(),
       topBar = {
           InventoryScreenTopAppBar(
               title = stringResource(R.string.inventory),
               isIcon = true
           )
       },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProductEntry,
                shape = CircleShape,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xff0081f7)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.add_product)
                )
            }
        },
    ) { innerPadding ->
        InventoryBody(
            productList = inventoryUiState.productsList,
            totalProducts = totalProducts,
            totalStocks = totalStocks,
            navigateToProduct = navigateToProductDetail,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .background(color = MaterialTheme.colorScheme.background),
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InventoryBody(
    productList: List<Product>,
    totalProducts: Int,
    totalStocks: Int,
    navigateToProduct: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    if (productList.isEmpty()) {
        EmptyScreen()
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TotalProducts(totalProducts = totalProducts)
                    TotalStocks(totalStocks = totalStocks)
                }
            }

            itemsIndexed(productList) { _, product ->
                InventoryItem(product, navigateToProduct)
            }
        }
    }
}

@Composable
private fun InventoryItem(product: Product, onProductClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onProductClick(product.productId) }
            .padding(horizontal = 0.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ){
            Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
            if (product.quantity < 20) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xfff9ded2)
                ) {
                    Text(
                        text = "Low",
                        color = Color(0xfff98048),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "View Details",
            tint = Color(0xff9c9c9c),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun TotalProducts(totalProducts: Int) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Text(text = stringResource(R.string.total_products), style = MaterialTheme.typography.bodyMedium, color = Color(0xff919191))
        Text(text = "$totalProducts", style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun TotalStocks(totalStocks: Int) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Text(text = stringResource(R.string.total_stocks), style = MaterialTheme.typography.bodyMedium, color = Color(0xff919191))
        Text(text = "$totalStocks", style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreenTopAppBar(
    title:String,
    isIcon: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )

        if (isIcon)
            IconButton(
                modifier = Modifier.padding(0.dp),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search_icon),
                    contentDescription = null
                )
            }
    }

}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.empty_inventory),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}