package com.example.maslaunsher.presentation.launcher

import android.content.ComponentName
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.maslaunsher.presentation.launcher.components.AppDrawer

/**
 * The root UI screen component for our Launcher.
 * 
 * EDUCATIONAL NOTE (ViewModel & Screen Architecture):
 * [LauncherScreen] connects the Jetpack Compose UI tree with our business logic in 
 * [LauncherViewModel]. It collects state ([apps], [searchQuery]) using [collectAsState]
 * and delegates user actions back to the ViewModel methods.
 *
 * @param viewModel The [LauncherViewModel] injected by Hilt containing home/drawer screen state.
 */
@Composable
fun LauncherScreen(
    viewModel: LauncherViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    
    // Collect reactive StateFlows from ViewModel into Compose state
    val apps by viewModel.apps.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AppDrawer(
                apps = apps,
                searchQuery = searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                onClearQuery = viewModel::clearSearchQuery,
                onAppClick = { app ->
                    // --- LAUNCH LOGIC ---
                    // To start another app, we create an EXPLICIT INTENT using 
                    // the packageName and className stored in our AppModel.
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_LAUNCHER)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or 
                                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                        component = ComponentName(app.packageName, app.className)
                    }
                    
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // Handle cases where the app might have been uninstalled 
                        // between the last refresh and the click.
                        e.printStackTrace()
                    }
                }
            )
        }
    }
}
