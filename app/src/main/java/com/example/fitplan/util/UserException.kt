package com.example.fitplan.util

class UserException(val code: Int) : Exception(

    when (code) {
        401 -> "$code Unauthorized Error"
        else -> "Unknown error"
    }
)
