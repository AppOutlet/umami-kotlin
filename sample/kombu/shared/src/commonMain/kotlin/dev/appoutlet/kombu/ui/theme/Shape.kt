package dev.appoutlet.kombu.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object KombuRadii {
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 12.dp
    val large = 16.dp
    val extraLarge = 28.dp
}

val KombuShapes = Shapes(
    extraSmall = RoundedCornerShape(KombuRadii.extraSmall),
    small = RoundedCornerShape(KombuRadii.small),
    medium = RoundedCornerShape(KombuRadii.medium),
    large = RoundedCornerShape(KombuRadii.large),
    extraLarge = RoundedCornerShape(KombuRadii.extraLarge),
)
