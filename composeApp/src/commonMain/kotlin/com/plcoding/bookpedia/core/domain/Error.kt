package com.plcoding.bookpedia.core.domain

interface Error

sealed interface DataError : Error{

    enum class Remote : DataError{
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        INTERNAL_SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        UNKNOWN,
        SERIALIZATION_ERROR
    }
    enum class Local : DataError{
        DATABASE_ERROR,
        NOT_FOUND,
        UNKNOWN
    }
}