package com.plcoding.bookpedia.core.presentation

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.error_database
import cmp_bookpedia.composeapp.generated.resources.error_internal_server
import cmp_bookpedia.composeapp.generated.resources.error_request_timeout
import cmp_bookpedia.composeapp.generated.resources.error_serialization
import cmp_bookpedia.composeapp.generated.resources.error_service_unavailable
import cmp_bookpedia.composeapp.generated.resources.error_too_many_requests
import cmp_bookpedia.composeapp.generated.resources.error_unknown
import com.plcoding.bookpedia.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes =  when(this) {
        DataError.Local.UNKNOWN ->Res.string.error_unknown
        DataError.Remote.UNKNOWN ->  Res.string.error_unknown
        DataError.Remote.SERVICE_UNAVAILABLE ->  Res.string.error_service_unavailable
        DataError.Remote.REQUEST_TIMEOUT ->  Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS ->  Res.string.error_too_many_requests
        DataError.Remote.SERIALIZATION_ERROR ->  Res.string.error_serialization
        DataError.Remote.INTERNAL_SERVER_ERROR ->  Res.string.error_internal_server
        DataError.Local.DATABASE_ERROR ->  Res.string.error_database
        DataError.Local.NOT_FOUND -> TODO()
    }
    return UiText.StringResourceId(stringRes)
}
