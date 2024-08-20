package miki.learn.finglance.ui.screen.compact

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.screen.utils.ConvexPieChart
import miki.learn.finglance.ui.screen.utils.ItemsList
import miki.learn.finglance.ui.screen.utils.PieChartPanel
import miki.learn.finglance.ui.screen.utils.PortfolioStatistics
import miki.learn.finglance.ui.screen.utils.ProfileHeader
import miki.learn.finglance.ui.screen.utils.StatisticsData
import miki.learn.finglance.ui.screen.utils.TotalView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactPortfolioScreen(
    @DrawableRes profileImage: Int,
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit,
    userData: UserData?,
    navigateToItem: (String) -> Unit,
    viewModel: PortfolioViewModel,
    navigateToPortfolioEdit: () -> Unit
) {

    val uiState by viewModel.portfolioUiState.collectAsState()

    Scaffold(
        bottomBar = {
            CompactNavigationBar(
                navigateToItem = navigateToItem,
                viewModel = viewModel
            )
        },
    ) {contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
        ) {
            ProfileHeader(
                profileImage = profileImage,
                userData = userData,
                onLogOut = onLogOut,
                modifier = Modifier
                    .weight(1F)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(2F)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ConvexPieChart(
                    data = uiState.portfolioStocks,
                    modifier = Modifier.size(200.dp)
                )
                PieChartPanel(
                    Modifier.size(150.dp)
                ) {
                    TotalView(
                        total = uiState.totalPortfolioValue,
                        screenSizeBasedStyleText = MaterialTheme.typography.titleSmall,
                        screenSizeBasedStyleAmount = MaterialTheme.typography.titleSmall
                        )
                }
            }
            PortfolioStatistics(
                modifier = Modifier
                    .weight(2F)
                    .padding(16.dp)
            )
        }

    }
}

@Composable
fun CompactNavigationBar(
    navigateToItem: (String) -> Unit,
    viewModel: PortfolioViewModel
) {

    NavigationBar {

        val bottomItemsList = ItemsList.itemsList

        bottomItemsList.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = viewModel.selectedItemIndex == index,
                onClick = {
                    viewModel.updateSelectedItem(index)
                    navigateToItem(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if(index == viewModel.selectedItemIndex)
                            item.selectedIcon else item.unselectedIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileHeaderPreview() {
//    FinGlanceTheme {
//        val data = listOf(
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Returns"
//            ),
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Volatility"
//            ),
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Max Drawdown"
//            ),
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Min Drawdown"
//            ),
//        )
//        CompactPortfolioScreen(
//            profileImage = R.drawable.ic_launcher_background,
//            statisticsData = data,
//        )
//    }
//}