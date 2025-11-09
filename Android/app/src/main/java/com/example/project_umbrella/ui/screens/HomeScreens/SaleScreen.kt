package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.project_umbrella.R
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.ui.navigation.NavigationDestination
import com.example.project_umbrella.ui.screens.inventory.AddProductTopAppBar

object SaleDestination: NavigationDestination {
    override val route = "Sale"
    override val titleRes = R.string.new_sale
}

@Composable
fun SaleScreen(
    navigateBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AddProductTopAppBar(
                title = stringResource(R.string.new_sale),
                navigateBack = navigateBack
            )
        }
    ) { innerPadding ->
        SaleBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
        )
    }
}

@Composable
fun SaleBody(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedButton(
            onClick = { },
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(0.8f)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.contact),
                    contentDescription = null
                )
                Spacer(modifier = modifier.size(10.dp))
                Text(
                    text = stringResource(R.string.customers),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

//        ScrollableTable()
    }
}

@Composable
fun ScrollableTable(products: List<Product>) {
    LazyColumn (

    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Product", style = MaterialTheme.typography.bodyMedium)
                Text("Quantity", style = MaterialTheme.typography.bodyMedium)
                Text("Total", style = MaterialTheme.typography.bodyMedium)
            }
        }

        items(products) { products ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Product", style = MaterialTheme.typography.bodyMedium)
                Text("Quantity", style = MaterialTheme.typography.bodyMedium)
                Text("Total", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}