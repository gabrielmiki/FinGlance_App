package miki.learn.finglance.ui.screen.compact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FinGlanceTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.display.StockEvent
import miki.learn.finglance.ui.display.StockViewModel
import miki.learn.finglance.ui.utils.PreviewData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactSearchScreen(
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

    Scaffold(
        bottomBar = {
            CompactNavigationBar(
                navigateToItem = navigateToItem,
                viewModel = portfolioViewModel
            )
        },
    ) {innerPadding ->
        Column(
            modifier = modifier.fillMaxSize().padding(innerPadding)
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

@Composable
fun CompanyList(
    modifier: Modifier = Modifier,
    companyListSize: Int,
    companyList: List<CompanyListing>,
    navigateToDetail: (String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(companyListSize) {i ->
            val company = companyList[i]
            CompanyItem(
                company = company,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navigateToDetail(company.symbol)
                    }
            )
            if(i < companyListSize) {
                Divider(modifier = Modifier.padding(
                    horizontal = 16.dp
                ))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        label = {
            Text(text = "Search")
        },
        maxLines = 1,
        singleLine = true
    )
}

@Composable
fun CompanyItem(
    company: CompanyListing,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1F)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = company.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = company.exchange,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "(${company.symbol})",
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    FinGlanceTheme {
        SearchBox(searchQuery = "", onValueChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyListPreview() {
    FinGlanceTheme {
        CompanyList(
            companyListSize = PreviewData.companyListings.size,
            companyList = PreviewData.companyListings,
            navigateToDetail = {}
        )
    }
}
