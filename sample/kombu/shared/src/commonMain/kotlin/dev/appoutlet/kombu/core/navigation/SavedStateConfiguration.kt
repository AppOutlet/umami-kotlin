package dev.appoutlet.kombu.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * Serializers module that knows how to (de)serialize every route polymorphically, so the whole
 * back stack survives configuration changes, process death, and web browser navigation.
 */
fun getSavedStateConfiguration(navigation: List<Navigation<*>>) = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            navigation.forEach { subclass(it.routeClass, it.routeSerializer) }
        }
    }
}