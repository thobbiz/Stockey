package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.project_umbrella.ui.components.ProductInAndOut
import com.example.project_umbrella.ui.components.TimeRangeSelector
import com.example.project_umbrella.ui.navigation.NavigationDestination

object HomeDestination: NavigationDestination {
    override val route = "Home"
    override val titleRes = R.string.home
}

@Composable
fun HomeScreen() {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HomeScreenTopAppBar()
        }
    ) {
        innerPadding ->
        HomeScreenBody(innerPadding)
    }
}

@Composable
fun HomeScreenBody(
    padding: PaddingValues
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
    ) {
        ProductInAndOut()
        TimeRangeSelector()
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.revenue),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
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
                LineChart(MaterialTheme.colorScheme.background)
            }
        }
    }
}
@Composable
private fun HomeScreenTopAppBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.dashboard),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )

        IconButton(
            modifier = Modifier.padding(0.dp),
            onClick = {}
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.add_icon),
                contentDescription = null
            )
        }
    }
}