package miki.learn.finglance.ui.screen.medium

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.display.StockEvent
import miki.learn.finglance.ui.display.StockViewModel
import miki.learn.finglance.ui.screen.compact.CompactNavigationBar
import miki.learn.finglance.ui.screen.compact.CompanyList
import miki.learn.finglance.ui.screen.compact.SearchBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumSearchScreen(
    viewModel: StockViewModel,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel,
) {

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state

    Row(
        modifier = modifier.fillMaxSize()
    ) {
        MediumNavigationBar(
            viewModel = portfolioViewModel,
            navigateToItem = navigateToItem
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBox(
                searchQuery = state.searchQuery,
                onValueChange = { viewModel.onEvet(StockEvent.OnSearchQuery(it)) }
            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.onEvet(StockEvent.Refresh) }
            ) {
                CompanyList(
                    companyList = state.companies,
                    companyListSize = state.companies.size,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}