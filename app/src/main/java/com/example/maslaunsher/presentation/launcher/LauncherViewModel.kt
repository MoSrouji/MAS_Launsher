package com.example.maslaunsher.presentation.launcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maslaunsher.domain.model.AppModel
import com.example.maslaunsher.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Home Screen and App Drawer.
 * 
 * EDUCATIONAL NOTE (Reactive Architecture & StateFlow):
 * The ViewModel acts as the "brain" for the UI layer. It isolates UI state from
 * business/data logic. By exposing [StateFlow] streams, the UI layer can reactively
 * re-render when the underlying state updates without needing manual callbacks.
 * 
 * @property appRepository The repository source for retrieving installed applications.
 */
@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    /**
     * Internal mutable state holding the complete list of installed applications.
     */
    private val _allApps = MutableStateFlow<List<AppModel>>(emptyList())

    /**
     * Internal mutable state holding the current search query typed by the user.
     */
    private val _searchQuery = MutableStateFlow("")

    /**
     * External immutable [StateFlow] exposing the user's current search query text.
     */
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    /**
     * External immutable [StateFlow] exposing the reactively filtered list of applications.
     * 
     * EDUCATIONAL NOTE (combine operator):
     * The [combine] flow operator joins [_allApps] and [_searchQuery]. Whenever EITHER 
     * the app list changes OR the search query changes, the transformation lambda is 
     * executed automatically to produce a filtered list.
     * 
     * [stateIn] converts a cold flow into a hot [StateFlow] scoped to [viewModelScope].
     * `SharingStarted.WhileSubscribed(5000)` keeps the flow active for 5 seconds after 
     * the last subscriber detaches (e.g., during screen rotation), preventing unnecessary 
     * re-calculations.
     */
    val apps: StateFlow<List<AppModel>> = combine(_allApps, _searchQuery) { appList, query ->
        if (query.isBlank()) {
            appList
        } else {
            appList.filter { app ->
                app.label.contains(query, ignoreCase = true) ||
                app.packageName.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Automatically fetch installed apps when the ViewModel is initialized
        loadApps()
    }

    /**
     * Fetches installed applications from the repository and populates [_allApps].
     */
    private fun loadApps() {
        viewModelScope.launch {
            appRepository.getInstalledApps().collect { appList ->
                _allApps.value = appList
            }
        }
    }

    /**
     * Updates the user's current search filter query.
     * 
     * @param query The new search string entered by the user.
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    /**
     * Clears the current search query, resetting the app list to display all installed apps.
     */
    fun clearSearchQuery() {
        _searchQuery.value = ""
    }
}
