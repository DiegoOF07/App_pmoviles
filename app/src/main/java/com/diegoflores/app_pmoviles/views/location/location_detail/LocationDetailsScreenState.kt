package com.diegoflores.app_pmoviles.views.location.location_detail

import com.diegoflores.app_pmoviles.data.Location


data class LocationDetailsScreenState(
    val isLoading: Boolean = false,
    val data: Location? = null,
    val hasError: Boolean = false
)