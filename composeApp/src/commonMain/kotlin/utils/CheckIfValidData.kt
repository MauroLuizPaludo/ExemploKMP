package com.aay.compose.utils



internal fun checkIfDataValid(
    xAxisData: List<String>,
) {
}

internal fun checkIfDataIsNegative(data: List<Double>) {
    data.forEach {
        if (it < 0.0) {
            throw Exception("The data can't contains negative values.")
        }
    }
}