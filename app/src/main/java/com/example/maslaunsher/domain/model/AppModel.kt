package com.example.maslaunsher.domain.model

import android.graphics.drawable.Drawable

/**
 * Represents an application installed on the device.
 * 
 * We use this data class to decouple our UI from the complex Android System objects 
 * like [android.content.pm.ResolveInfo].
 *
 * @property label The human-readable name of the app (e.g., "Chrome").
 * @property packageName The unique identifier for the app (e.g., "com.android.chrome").
 * @property icon The visual icon of the app, retrieved from the system.
 * @property className The specific activity name that needs to be started to launch the app.
 */
data class AppModel(
    val label: String,
    val packageName: String,
    val icon: Drawable?,
    val className: String
)
