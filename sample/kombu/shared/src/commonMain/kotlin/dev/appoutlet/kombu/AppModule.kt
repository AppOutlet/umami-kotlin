package dev.appoutlet.kombu

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module

/**
 * Root Koin module. `@ComponentScan` discovers every `@Single Navigation<T>` (and other DI
 * declarations) under the [COMPONENT_SCAN_PACKAGE], which powers the navigation multibinding.
 */
@Module
@Configuration
@ComponentScan(COMPONENT_SCAN_PACKAGE)
class AppModule

/**
 * Marker for the generated Koin application configuration. Referenced by [App] to start the
 * container without manual module registration.
 */
@KoinApplication
class KombuKoinApplication

private const val COMPONENT_SCAN_PACKAGE = "dev.appoutlet.kombu"