package miki.learn.finglance.ui.screen.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import miki.learn.finglance.FinGlanceScreen

data class BottomNavigationIcon(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

object ItemsList {
    val itemsList = listOf(
        BottomNavigationIcon(
            title = "Portfolio",
            route = FinGlanceScreen.PORTFOLIO.name,
            selectedIcon = Icons.Filled.MonetizationOn,
            unselectedIcon = Icons.Outlined.MonetizationOn
        ),
        BottomNavigationIcon(
            title = "Search",
            route = FinGlanceScreen.SEARCH.name,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),
        BottomNavigationIcon(
            title = "Profile",
            route = FinGlanceScreen.PROFILE.name,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
    )
}