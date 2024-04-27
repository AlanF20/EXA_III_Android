package com.example.exa_ii

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GuitarViewModel(
    private val guitarDao: GuitarDao
): ViewModel() {
    var state by mutableStateOf(GuitarState())
        private set
    init {
        viewModelScope.launch {
            guitarDao.getAllGuitars().collectLatest {
                state = state.copy(
                    guitarList = it
                )
            }
        }
    }
    fun insertGuitar(guitar: Guitar) = viewModelScope.launch {
        guitarDao.insert(guitar)
    }
    fun updateGuitar(guitar: Guitar) = viewModelScope.launch {
        guitarDao.update(guitar)
    }
    fun deleteGuitar(guitar: Guitar) = viewModelScope.launch {
        guitarDao.delete(guitar)
    }
}