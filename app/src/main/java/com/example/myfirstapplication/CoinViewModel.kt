package com.example.myfirstapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CoinViewModel: ViewModel() {
    var coins by mutableStateOf<List<Coin>>(emptyList())
    var loading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    init {
        viewModelScope.launch {
            try {
                coins = ApiService.api.getCoins()
            } catch (e: Exception) {
                error = "Failed to load"
            }
            loading = false
        }
    }
}