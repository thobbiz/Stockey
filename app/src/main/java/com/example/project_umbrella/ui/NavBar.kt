package com.example.project_umbrella.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val nav_items = listOf(
    NavigationItem(label = "Home", icon = Icons.Outlined.Home, iconClicked = Icons.Filled.Home, navigateTo = {}),
    NavigationItem(label = "Inventory", icon = Icons.Outlined.AccountBox, iconClicked = Icons.Filled.AccountBox, navigateTo = {}),
    NavigationItem(label = "Notifications", icon = Icons.Outlined.MailOutline, iconClicked = Icons.Filled.MailOutline, navigateTo = {}),
    NavigationItem(label = "Settings", icon = Icons.Outlined.Settings, iconClicked = Icons.Filled.Settings, navigateTo = {})
)

@Composable
fun NavBar () {
    Card(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.07f),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(contentColor = Color.Unspecified, containerColor = Color.White)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            nav_items.forEachIndexed { _, item ->
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            item.navigateTo()
                        }
                )
            }
        }
    }
}

data class NavigationItem(
    val label:String,
    val icon: ImageVector,
    val iconClicked: ImageVector,
    val navigateTo: () -> Unit
)