package uz.uzkass.smartpos.supply.core.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.takeFrom

suspend inline fun <reified T> HttpClient.postJson(
    urlAddress: String,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val response = post {
        contentType(ContentType.Application.Json)
        url.takeFrom(urlAddress)
        block()
    }
    if (response.status == HttpStatusCode.OK) {
        return response.body()
    } else {
        throw ServerResponseException(response, response.bodyAsText())
    }
}


suspend inline fun <reified T> HttpClient.putJson(
    urlAddress: String,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val response = put {
        contentType(ContentType.Application.Json)
        url.takeFrom(urlAddress)
        block()
    }
    if (response.status == HttpStatusCode.OK) {
        return response.body()
    } else {
        throw ServerResponseException(response, response.bodyAsText())
    }
}

suspend inline fun <reified T> HttpClient.getJson(
    urlAddress: String,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val response = get {
        contentType(ContentType.Application.Json)
        url.takeFrom(urlAddress)
        block()
    }
    if (response.status == HttpStatusCode.OK) {
        return response.body()
    } else {
        throw ServerResponseException(
            response, try {
                response.bodyAsText()
            } catch (e: Exception) {
                e.toString()
            }
        )
    }
}

suspend inline fun <reified T> HttpClient.submitFormDefault(
    urlAddress: String,
    contentType: ContentType = ContentType.MultiPart.FormData,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val response = submitForm {
        contentType(contentType)
        url.takeFrom(urlAddress)
        block()
    }
    if (response.status == HttpStatusCode.OK) {
        return response.body()
    } else {
        throw ServerResponseException(response, response.bodyAsText())
    }
}


suspend inline fun <reified T> HttpClient.deleteJson(
    urlAddress: String,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val response = delete {
        contentType(ContentType.Application.Json)
        url.takeFrom(urlAddress)
        block()
    }
    if (response.status == HttpStatusCode.OK) {
        return response.body()
    } else {
        throw ServerResponseException(response, response.bodyAsText())
    }
}
