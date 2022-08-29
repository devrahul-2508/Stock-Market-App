package com.example.stockmarketapp.presentation.company_lisitngs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyListingState())
    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingEvent){
        when(event){
            is CompanyListingEvent.Refresh->{
              getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingEvent.OnSearchQueryChange->{
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query : String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote,query)
                .collect{
                    when(it){
                        is Resource.Success->{
                                it.data.let{ listings->
                                    state = state.copy(
                                        companies = listings!!
                                    )



                                }
                        }
                        is Resource.Error->{

                        }
                        is Resource.Loading->{
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                    }
                }
        }
    }
}