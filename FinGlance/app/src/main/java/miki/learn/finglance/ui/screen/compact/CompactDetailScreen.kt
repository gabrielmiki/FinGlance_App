package miki.learn.finglance.ui.screen.compact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import miki.learn.finglance.domain.model.PortfolioStock
import miki.learn.finglance.ui.display.DetailViewModel
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.screen.utils.DialogAmountPopUp
import miki.learn.finglance.ui.screen.utils.StockChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactDetailScreen(
    detailViewModel: DetailViewModel,
    navigateBackPortfolioScreen: () -> Unit,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel,
) {

    val uiState = detailViewModel.state

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            CompactNavigationBar(
                navigateToItem = navigateToItem,
                viewModel = portfolioViewModel
            )
        }
    ) {innerPadding ->
        if (uiState.error == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                uiState.company?.let { company ->

                    Text(
                        text = company.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = company.symbol,
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = " Industry: ${company.industry}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Country: ${company.symbol}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = company.description,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    if (uiState.stockInfo.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Market Summary")
                        Spacer(modifier = Modifier.height(32.dp))
                        StockChart(
                            infos = uiState.stockInfo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .align(CenterHorizontally)
                        )
                    }

                    Button(
                        onClick = {
                            detailViewModel.onAddStockClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            if (detailViewModel.isDialogShown) {
                DialogAmountPopUp(
                    onConfirmClick = {
                        coroutineScope.launch {
                            detailViewModel.saveItem(
                                PortfolioStock(
                                    name = uiState.company?.name ?: "",
                                    symbol = uiState.company?.symbol ?: "",
                                    amount = detailViewModel.stockPortfolioAmount.toDouble(),
                                    price = uiState.stockInfo.last().close,
                                )
                            )
                        }
                        navigateBackPortfolioScreen()
                        detailViewModel.onDismissClick()
                    },
                    onDismissClick = { detailViewModel.onDismissClick() },
                    portfolioStock = PortfolioStock(
                        name = uiState.company?.name ?: "",
                        symbol = uiState.company?.symbol ?: "",
                        amount = 0.0,
                        price = uiState.stockInfo.last().close
                    ),
                    detailViewModel = detailViewModel
                )
            }
        }
    }
}