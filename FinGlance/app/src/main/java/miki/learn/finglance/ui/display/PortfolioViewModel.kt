package miki.learn.finglance.ui.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import miki.learn.finglance.FinGlanceApplication
import miki.learn.finglance.R
import miki.learn.finglance.data.local.PortfolioEntity
import miki.learn.finglance.data.repository.PortfolioRepository
import miki.learn.finglance.data.repository.PortfolioRepositoryInterface
import miki.learn.finglance.data.repository.StatisticsRepository
import miki.learn.finglance.domain.model.PortfolioStock
import miki.learn.finglance.ui.screen.utils.StatisticsData

data class PortfolioUiState(
    val portfolioStocks: List<PortfolioStock> = emptyList(),
    var totalPortfolioValue: Double = portfolioStocks.map { it.amount * it.price }.sum()
)

//fun PortfolioEntity.toPortfolioStockDetail(): PortfolioStockDetail {
//    return PortfolioStockDetail(
//        name = name,
//        symbol = symbol,
//        amount = amount,
//        price = price,
//        id = id
//    )
//}
//
//fun PortfolioStockDetail.toPortfolioEntity(): PortfolioEntity {
//    return PortfolioEntity(
//        name = name,
//        symbol = symbol,
//        amount = amount,
//        price = price,
//        id = id
//    )
//}
//
//fun PortfolioEntity.toPortfolioUiState(isEntryValid: Boolean = false): PortfolioUiState {
//    return PortfolioUiState(
//        portfolioStockDetail = this.toPortfolioStockDetail(),
//        isEntryValid = isEntryValid
//    )
//}

class PortfolioViewModel(
    val portfolioRepository: PortfolioRepositoryInterface,
    val statisticsRepository: StatisticsRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinGlanceApplication)
                val portfolioRepository = application.container.portfolioDbRepository
                val statisticsRepository = application.container.statisticsRepository
                PortfolioViewModel(
                    portfolioRepository = portfolioRepository,
                    statisticsRepository = statisticsRepository
                    )
            }
        }
    }

    var portfolioUiState: StateFlow<PortfolioUiState> =
        portfolioRepository.getPortfolio().map { PortfolioUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PortfolioUiState()
            )


    var selectedItemIndex by mutableStateOf(0)
        private set

    // private val stockId: Int = checkNotNull(savedStateHandle[])

//    init {
//        viewModelScope.launch {
//            portfolioUiState = portfolioRepository.getStock()
//                .filterNotNull()
//                .first()
//                .toItemUiState(true)
//        }
//    }

    fun updateSelectedItem(index: Int) {
        selectedItemIndex = index
    }

    suspend fun saveItem(portfolioStock: PortfolioStock) {
        portfolioRepository.insertNewStock(portfolioStock)
    }

    // No need to copy over, this is part of starter code
//    private fun validateInput(portfolioUiState: PortfolioUiState = portfolioUiState.portfolioStockDetail): Boolean {
//        return with(portfolioUiState) {
//            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
//        }
//    }

//    fun calculatePortfolioReturns(): List<StatisticsData> {
//        var result = listOf<StatisticsData>()
//        viewModelScope.launch {
//            result = listOf<StatisticsData>(
//                StatisticsData
//                    (
//                    graph = R.drawable.ic_launcher_background,
//                    type = "Returns",
//                    data = statisticsRepository.calculateMonthlyStocksResults().getOrNull(0) ?: listOf()
//                )
//            )
//        }
//
//        return result
//    }
}