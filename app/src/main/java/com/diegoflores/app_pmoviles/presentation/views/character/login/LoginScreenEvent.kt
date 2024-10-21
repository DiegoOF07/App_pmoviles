package com.diegoflores.app_pmoviles.presentation.views.character.login

sealed interface LoginScreenEvent{
    data class onNameChange(val name: String): LoginScreenEvent
    data object onSaveName: LoginScreenEvent
}