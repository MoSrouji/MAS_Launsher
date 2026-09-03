package com.example.maslaunsher.domain.repository

import com.example.maslaunsher.domain.model.AppModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing app-related data.
 * 
 * In Android development, we use interfaces to define *what* needs to be done,
 * while the implementation (in the data layer) defines *how* it's done.
 */
interface AppRepository {
    /**
     * Retrieves a list of all installed apps that can be launched.
     * 
     * We use [Flow] because the list of apps might change over time 
     * (e.g., when a user installs or uninstalls an app).
     */
    fun getInstalledApps(): Flow<List<AppModel>>
}
