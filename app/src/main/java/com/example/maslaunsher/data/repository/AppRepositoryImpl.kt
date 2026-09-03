package com.example.maslaunsher.data.repository

import android.content.Context
import android.content.Intent
import com.example.maslaunsher.domain.model.AppModel
import com.example.maslaunsher.domain.repository.AppRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AppRepository] that uses the Android system's PackageManager.
 * 
 * The [@Singleton] annotation tells Hilt that we only want one instance of this 
 * repository to exist throughout the app's lifecycle.
 */
@Singleton
class AppRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppRepository {

    /**
     * This is the core logic of a launcher.
     * 
     * We create an "Intent" that says: "I'm looking for anything that is MAIN and LAUNCHER."
     * Then we ask the PackageManager to find all activities that match this criteria.
     */
    override fun getInstalledApps(): Flow<List<AppModel>> = flow {
        val packageManager = context.packageManager
        
        // 1. Define the criteria for a launchable app
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        // 2. Query the system for matching activities
        // Note: queryIntentActivities returns ResolveInfo, which contains details about the app
        val resolveInfos = packageManager.queryIntentActivities(mainIntent, 0)


        // 3. Convert system objects into our clean AppModel
        val apps = resolveInfos.map { resolveInfo ->
            AppModel(
                label = resolveInfo.loadLabel(packageManager).toString(),
                packageName = resolveInfo.activityInfo.packageName,
                icon = resolveInfo.loadIcon(packageManager),
                className = resolveInfo.activityInfo.name

            )
        }.sortedBy { it.label.lowercase() } // Sort alphabetically for the user

        // 4. Emit the result to the flow
        emit(apps)
    }
}
