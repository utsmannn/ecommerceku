package com.utsman.features.user

import com.utsman.libraries.core.state.Intent

sealed class UserIntent : Intent {
    data object GetUser : UserIntent()
}