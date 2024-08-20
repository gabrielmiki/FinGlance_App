package miki.learn.finglance.ui.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import miki.learn.finglance.FinGlanceApplication
import miki.learn.finglance.data.repository.StockApiRepositoryInterface
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.domain.model.Ticker
import miki.learn.finglance.ui.utils.Resource

sealed interface StockUiState {
    data class Success(
        val tickers: List<Ticker>,

        ) : StockUiState
    object Loading : StockUiState
    object Error : StockUiState
}

data class StockUiState2(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)

sealed class StockEvent {
    object Refresh : StockEvent()
    data class OnSearchQuery(val query: String) : StockEvent()
}

class StockViewModel(
    private val stockApiRepository: StockApiRepositoryInterface
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FinGlanceApplication)
                val stockRepository = application.container.stockApiRepository
                StockViewModel(stockApiRepository = stockRepository)
            }
        }
    }

//    init {
//        getCompanyListings()
//    }

    var state by mutableStateOf(StockUiState2())

    private var searchJob: Job? = null

    fun onEvet(event: StockEvent) {
        when (event) {
            is StockEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is StockEvent.OnSearchQuery -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            stockApiRepository
                .getListings(fetchFromRemote, query)
                .collect {result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {listings ->
                                state = state.copy(
                                    companies = listings
                                )
                            }
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }
}