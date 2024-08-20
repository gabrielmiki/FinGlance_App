package miki.learn.finglance.ui.screen.expanded

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinGlanceTheme
import miki.learn.finglance.R
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.screen.utils.ProfileHeader
import miki.learn.finglance.ui.utils.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedPortfolioScreen(
    @DrawableRes profileImage: Int,
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit,
    userData: UserData?
) {
    val items = listOf(
        NavigationItem(
            name = R.string.portfolio_navigation_item,
            icon = Icons.Rounded.MonetizationOn
        ),
        NavigationItem(
            name = R.string.search_navigation_item,
            icon = Icons.Rounded.Search
        ),
        NavigationItem(
            name = R.string.profile_navigation_item,
            icon = Icons.Rounded.Settings
        ),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                for (navItem in items) {
                    NavigationDrawerItem(
                        selected = navItem.name == 0,
                        label = {
                            Text(
                                text = stringResource(navItem.name),
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        },
                        onClick = { }
                    )
                }
            }
        }
    ) {
        Scaffold(

        ) {contentPadding ->
            Row(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxSize()
                        .padding(contentPadding)
                ) {
                    ProfileHeader(
                        profileImage = profileImage,
                        userData = userData,
                        onLogOut = onLogOut,
                        modifier = Modifier
                            .weight(1F)
                    )
                    Spacer(
                        modifier = Modifier
                            .weight(4F)
                    )
                    // PortifolioStatistics
                }
                Spacer(modifier = Modifier.weight(1F))
            }

        }
    }
}

//@Preview(showBackground = true, widthDp = 1000)
//@Composable
//fun ExpandedPortfolioScreenPreview() {
//    FinGlanceTheme {
//        ExpandedPortfolioScreen(R.drawable.ic_launcher_background,)
//    }
//}