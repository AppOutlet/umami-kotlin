package dev.appoutlet.kombu.core.navigation

import org.koin.core.annotation.Single

/**
 * Collects every [Navigation] in the Koin graph (multibinding of all `@Single Navigation<T>`)
 * so the root scaffold can register and render each destination.
 */
@Single
class NavigationAggregator(val navigation: List<Navigation<*>>)