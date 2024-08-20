package miki.learn.finglance.ui.utils

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    @StringRes val name: Int,
    val icon: ImageVector
)