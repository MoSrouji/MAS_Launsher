package com.example.maslaunsher.presentation.launcher.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

/**
 * A search input bar component for filtering installed apps in the launcher drawer.
 *
 * EDUCATIONAL NOTE (State Hoisting):
 * In Jetpack Compose, a Composable should ideally be stateless when possible.
 * We pass [searchQuery] down into this component and pass events up via [onQueryChange] 
 * and [onClearQuery]. This pattern is known as "State Hoisting" and makes the UI 
 * reusable, testable, and previewable.
 *
 * @param searchQuery The current text entered by the user in the search field.
 * @param onQueryChange Callback invoked whenever the user types or deletes a character.
 * @param onClearQuery Callback invoked when the user clicks the clear ('X') icon button.
 * @param modifier Optional [Modifier] for configuring layout behavior and appearance from parent views.
 */
@Composable
fun AppSearchBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = {
            Text(text = "Search apps...")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            // Show clear button only when user has typed something
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search query"
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp), // Modern rounded search pill design
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Hide soft keyboard when user presses the Search key on keyboard
                focusManager.clearFocus()
            }
        )
    )
}
