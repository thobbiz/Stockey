package com.example.project_umbrella.ui.components

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

@Composable
fun ProductInAndOut() {
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
            colors = CardDefaults.cardColors(containerColor = Color(0xff0081f7))
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
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        "3027",
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        stringResource(R.string.product_in),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
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
            colors = CardDefaults.cardColors(containerColor = Color(0xff01B2EA))
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
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        "2698",
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        stringResource(R.string.product_out),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
