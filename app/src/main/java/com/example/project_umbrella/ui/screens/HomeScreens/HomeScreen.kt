package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_umbrella.R
import com.example.project_umbrella.ui.navigation.NavigationDestination

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        ProductInAndOut()

        Text(
            text = "Revenue",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xffacbdc8),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "$27,003.98",
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
            )

            LineChart()
        }
    }
}

@Composable
private fun ProductInAndOut() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xff046cdb))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = stringResource(R.string.shopping_cart)
                    )
                Text(
                    "3044",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Thin
                )
                Text(
                    stringResource(R.string.product_in),
                    color = Color(0xffe6eaed),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xff49a2e4))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = stringResource(R.string.info)
                )
                Text(
                    "3044",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Thin
                )
                Text(
                    stringResource(R.string.product_out),
                    color = Color(0xffe6eaed),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}