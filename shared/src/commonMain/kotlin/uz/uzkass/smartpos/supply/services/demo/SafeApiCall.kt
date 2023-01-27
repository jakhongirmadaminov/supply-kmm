package uz.uzkass.smartpos.supply.services.demo


import io.ktor.client.plugins.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

@OptIn(InternalAPI::class)
suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(data = apiCall.invoke())
    } catch (e: RedirectResponseException) { // 3xx errors
        val networkError = getError(responseContent = e.response.content)

        NetworkResult.Error(
            errorCode = networkError.statusCode ?: e.response.status.value,
            errorMessage = networkError.statusMessage ?: e.message
        )
    } catch (e: ClientRequestException) { // 4xx errors
        val networkError = getError(responseContent = e.response.content)

        NetworkResult.Error(
            errorCode = networkError.statusCode ?: e.response.status.value,
            errorMessage = networkError.statusMessage ?: e.message
        )
    } catch (e: ServerResponseException) { // 5xx errors
        val networkError = getError(responseContent = e.response.content)

        NetworkResult.Error(
            errorCode = networkError.statusCode ?: e.response.status.value,
            errorMessage = networkError.statusMessage ?: e.message
        )
    } catch (e: Exception) {
        NetworkResult.Error(
            errorCode = 0,
            errorMessage = e.message ?: "An unknown error occurred"
        )
    }
}

suspend fun getError(responseContent: ByteReadChannel): ApiError {
    return kotlinx.serialization.json.Json.decodeFromString(string = responseContent.toString())
    // throw IllegalArgumentException("Not a parsable error")
}

@Serializable
data class ApiError(

    @SerialName("status_code")
    val statusCode: Int?,

    @SerialName("status_message")
    val statusMessage: String?,

    @SerialName("success")
    val success: Boolean?
)
