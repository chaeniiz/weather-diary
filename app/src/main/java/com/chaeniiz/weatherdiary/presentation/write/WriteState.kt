package com.chaeniiz.weatherdiary.presentation.write

sealed class WriteState {
    object WriteDiarySucceed : WriteState()

    sealed class Error {
        object EmptyContent : WriteState()

        object EmptyLocation : WriteState()

        object Server : WriteState()
    }
}
