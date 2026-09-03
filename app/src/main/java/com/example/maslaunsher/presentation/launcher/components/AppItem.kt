package com.example.maslaunsher.presentation.launcher.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.example.maslaunsher.domain.model.AppModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * A UI component that displays a single app's icon and label.
 * 
 * @param app The app data to display.
 * @param onClick A lambda function that will be executed when the user taps this app.
 */
@Composable
fun AppItem(
    app: AppModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Icon
        // We use Coil to display the Drawable icon safely
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(app.icon)
                .crossfade(true)
                .build(),
            contentDescription = app.label,
            modifier = Modifier
                .size(56.dp) // Standard launcher icon size
                .padding(bottom = 4.dp)
        )

        // App Label
        Text(
            text = app.label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
