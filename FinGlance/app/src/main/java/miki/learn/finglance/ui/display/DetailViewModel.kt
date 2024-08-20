package miki.learn.finglance.ui.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import miki.learn.finglance.FinGlanceApplication
import miki.learn.finglance.data.local.PortfolioEntity
import miki.learn.finglance.data.repository.PortfolioRepository
import miki.learn.finglance.data.repository.PortfolioRepositoryInterface
import miki.learn.finglance.data.repository.StockApiRepositoryInterface
import miki.learn.finglance.domain.model.CompanyInfo
import miki.learn.finglance.domain.model.DailyPrice
import miki.learn.finglance.domain.model.PortfolioStock
import miki.learn.finglance.ui.utils.Resource

data class CompanyStateUI(
    val stockInfo: List<DailyPrice> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DetailViewModel(
    private val stockApiRepository: StockApiRepositoryInterface,
    private val portfolioRepository: PortfolioRepositoryInterface
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinGlanceApplication)
                val stockRepository = application.container.stockApiRepository
                val portfolioRepository = application.container.portfolioDbRepository
                DetailViewModel(
                    stockApiRepository = stockRepository,
                    portfolioRepository = portfolioRepository
                )
            }
        }
    }

    var state by mutableStateOf(CompanyStateUI())

    var stockPortfolioAmount by mutableStateOf("0.0")
        private set

    var isDialogShown by mutableStateOf(false)
        private set

    fun getDetails(symbol: String){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val companyInfoResult = async { stockApiRepository.getCompanyInfo(symbol) }
            val dailyPriceResult = async { stockApiRepository.getDailyPrice(symbol) }
            when (val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        company = null,
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
            when (val result = dailyPriceResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        company = null,
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
        }
    }

    suspend fun saveItem(portfolioStock: PortfolioStock) {
        portfolioRepository.insertNewStock(portfolioStock)
    }

    fun updatePortfolioStockAmount(newAmount: String) {
        stockPortfolioAmount = newAmount
    }

    fun onAddStockClick() {
        isDialogShown = true
    }

    fun onDismissClick() {
        isDialogShown = false
    }
}