package com.example.project_umbrella.ui.screens.inventory

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_umbrella.R
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.ui.AppViewModelProvider
import com.example.project_umbrella.ui.navigation.NavigationDestination
import com.example.project_umbrella.ui.theme.ProjectumbrellaTheme

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
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val inventoryUiState by viewModel.inventoryUiState.collectAsState()
    val totalProducts by viewModel.totalProducts.collectAsState()
    val totalStocks by viewModel.totalStockCount.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
       topBar = {
           InventoryScreenTopAppBar(
               title = stringResource(R.string.inventory)
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

@Composable
private fun InventoryBody(
    productList: List<Product>,
    totalProducts: Int,
    totalStocks: Int,
    navigateToProduct: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp),
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TotalProducts(totalProducts = totalProducts)
            TotalStocks(totalStocks = totalStocks)
        }
        InventoryProductList(
            productList = productList,
            onProductClick = navigateToProduct
        )
    }
}

@Composable
private fun InventoryProductList(
    productList: List<Product>,
    onProductClick: (Int) -> Unit
) {
    if (productList.isEmpty()) {
        EmptyScreen()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(0.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            itemsIndexed(productList) { _, product ->
                InventoryItem(product, onProductClick)
            }
        }
    }
}

@Composable
private fun InventoryItem(product: Product, onProductClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth().clickable(
               onClick = { onProductClick(product.productId) }
            ).height(55.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RoundedCornerShape(3.dp),
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp))
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ){
                    Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                    if (product.quantity < 20) {
                        Card(
                            modifier = Modifier.padding(start = 7.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xfff9ded2))
                        ) {
                            Text("Low", color = Color(0xfff98048), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(3.dp))
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { onProductClick(product.productId) },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(0.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "More",
                        tint = Color(0xff9c9c9c)
                    )
                }
            }
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
    title:String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )

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

var fake: List<Product> = listOf(
    Product(1, "Coaster", 3100.0, 2000.0, 4, measurementUnit = "Bags"),
    Product(2, "Feast", 5000.0, 7500.0, 107, measurementUnit = "Packs"),
    Product(3, "Happy Happy", 40.0, 100.0, 3, measurementUnit = "rolls")
)

@Preview(showBackground = true)
@Composable
fun InventoryBodyPreview() {
    ProjectumbrellaTheme {
        InventoryBody(productList = fake, navigateToProduct = {}, totalProducts = 23, totalStocks = 90)
    }
}