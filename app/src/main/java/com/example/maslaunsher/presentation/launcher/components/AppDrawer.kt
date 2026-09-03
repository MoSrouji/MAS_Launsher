package com.example.maslaunsher.presentation.launcher.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maslaunsher.domain.model.AppModel

/**
 * The main App Drawer container displaying the search bar and grid of installed applications.
 * 
 * EDUCATIONAL NOTE (Composition & Slot Layouts):
 * By composing `AppSearchBar` at the top of a [Column] and placing a [LazyVerticalGrid] 
 * below it, we establish a clean vertical layout structure. 
 * `LazyVerticalGrid` only renders items visible on screen, maximizing scroll performance.
 *
 * @param apps The list of [AppModel] items to display in the grid (can be filtered).
 * @param searchQuery The current user text string typed into the search bar.
 * @param onQueryChange Callback invoked when the user modifies the search query text.
 * @param onClearQuery Callback invoked when the user clicks the clear button in the search bar.
 * @param onAppClick Callback invoked when the user selects/taps an application item.
 * @param modifier Optional [Modifier] for configuring layout behavior from the parent view.
 */
@Composable
fun AppDrawer(
    apps: List<AppModel>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onAppClick: (AppModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Search & Filter input bar at the top of the drawer
        AppSearchBar(
            searchQuery = searchQuery,
            onQueryChange = onQueryChange,
            onClearQuery = onClearQuery
        )

        // Show empty state if search query returns zero matching apps
        if (apps.isEmpty() && searchQuery.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No apps found for \"$searchQuery\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            // Display grid of installed apps (4 columns)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = apps,
                    key = { app -> "${app.packageName}/${app.className}" }
                ) { app ->
                    AppItem(
                        app = app,
                        onClick = { onAppClick(app) }
                    )
                }
            }
        }
    }
}
