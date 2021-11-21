package de.lukasneugebauer.nextcloudcookbook.core.presentation.components.authorized_image

import de.lukasneugebauer.nextcloudcookbook.core.data.NcAccount

data class AuthorizedImageState(
    val account: NcAccount? = null,
    val error: String? = null
)