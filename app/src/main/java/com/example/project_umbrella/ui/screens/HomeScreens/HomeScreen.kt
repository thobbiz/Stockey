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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        HomeScreenTopAppBar()
        Column(
            verticalArrangement = Arrangement.spacedBy(70.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            ProductInAndOut()

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Revenue",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.W900,
                    color = Color(0xff494D5A),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "$27,003.98",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    LineChart(MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun ProductInAndOut() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.22f)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xff046cdb))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.SpaceBetween,
                ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.productin),
                    contentDescription = stringResource(R.string.shopping_cart),
                    tint = Color.Unspecified
                    )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "3027",
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        stringResource(R.string.product_in),
                        color = Color(0xff80bfff),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.22f)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xff49a2e4))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.SpaceBetween,
                ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.productout),
                    tint = Color.Unspecified,
                    contentDescription = stringResource(R.string.info)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "2698",
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        stringResource(R.string.product_out),
                        color = Color(0xff96defd),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeScreenTopAppBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Dashboard", style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}