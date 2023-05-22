package com.example.koinandktortutorial.ui.state

import androidx.annotation.Keep
import com.example.koinandktortutorial.data.dto.UserInfo

@Keep
sealed class UIState {
    data class OnDisplayData(val data: List<UserInfo>?) : UIState()
    data class OnError(val message: String? = "") : UIState()
    object OnLoading : UIState()
}