package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.project_umbrella.R
import com.example.project_umbrella.ui.components.ProductInAndOut
import com.example.project_umbrella.ui.components.TimeRangeSelector
import com.example.project_umbrella.ui.navigation.NavigationDestination

object HomeDestination: NavigationDestination {
    override val route = "Home"
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
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            ProductInAndOut()
            TimeRangeSelector()
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Revenue",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color(0xff919191),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "$27,003.98",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    LineChart(MaterialTheme.colorScheme.primary)
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
        Text(text = "Dashboard", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.SemiBold, color = Color.Black)
    }
}