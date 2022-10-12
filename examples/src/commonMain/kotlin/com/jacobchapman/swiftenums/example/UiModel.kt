package com.jacobchapman.swiftenums.example

import com.jacobchapman.swiftenums.SwiftEnum

@SwiftEnum
sealed class UiModel {

    object Loading : UiModel()

    data class Content(
        val title: String,
        val description: String
    ) : UiModel()

    data class Error(
        val message: String
    ) : UiModel()
}

fun getUiModelSampleContent(): UiModel = UiModel.Content("Title Here", "Description Here")