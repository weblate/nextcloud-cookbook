package de.lukasneugebauer.nextcloudcookbook.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.lukasneugebauer.nextcloudcookbook.core.domain.use_case.ClearPreferencesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val clearPreferencesUseCase: ClearPreferencesUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            clearPreferencesUseCase()
        }
    }
}
