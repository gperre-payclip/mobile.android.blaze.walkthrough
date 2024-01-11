package com.payclip.blaze.spike.walkthrough

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainViewModel @Inject constructor() : ViewModel() {

    private val _isWalkthroughActive = MutableStateFlow(true)
    val isWalkthroughActive: StateFlow<Boolean>
        get() = _isWalkthroughActive

    private val _selectedStep = MutableStateFlow(0)
    val selectedStep: StateFlow<Int>
        get() = _selectedStep

    fun onFinishWalkthrough() = viewModelScope.launch {
        _isWalkthroughActive.emit(false)
    }

    fun goNextStep() = viewModelScope.launch {
        if (selectedStep.value < 7) {
            _selectedStep.value++
        } else {
            _selectedStep.emit(0)
        }
    }
}
