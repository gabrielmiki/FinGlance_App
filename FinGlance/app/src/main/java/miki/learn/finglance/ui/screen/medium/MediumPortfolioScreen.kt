package miki.learn.finglance.ui.screen.medium

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
fun MediumPortfolioScreen(
    @DrawableRes profileImage: Int,
    modifier: Modifier = Modifier,
    userData: UserData?,
    onLogOut: () -> Unit,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel
) {

    val uiState by portfolioViewModel.portfolioUiState.collectAsState()

    Scaffold(

    ) {contentPadding ->
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            MediumNavigationBar(
                viewModel = portfolioViewModel,
                navigateToItem = navigateToItem
            )
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(2F)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    ConvexPieChart(
                        data = uiState.portfolioStocks,
                        modifier = Modifier.size(400.dp)
                    )
                    PieChartPanel(
                        Modifier.size(300.dp)
                    ) {
                        TotalView(
                            total = 23548.0,
                            screenSizeBasedStyleText = MaterialTheme.typography.headlineLarge,
                            screenSizeBasedStyleAmount = MaterialTheme.typography.headlineMedium
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
}

@Composable
fun MediumNavigationBar(
    viewModel: PortfolioViewModel,
    navigateToItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val navigationItem = ItemsList.itemsList

    NavigationRail(
        modifier = modifier
    ) {

        navigationItem.forEachIndexed {index, item ->

            NavigationRailItem(
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
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            )

        }

    }
}

//@Preview(showBackground = true, widthDp = 700)
//@Composable
//fun MediumPortfolioScreenPreview() {
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
//        MediumPortfolioScreen(R.drawable.ic_launcher_background, statisticsData = data,)
//    }
//}