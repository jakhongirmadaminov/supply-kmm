package uz.uzkass.smartpos.supply.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ErrorTranslator {

    private val _errorMessage: Channel<String> = Channel(Channel.BUFFERED)
    var errorMessage = _errorMessage.receiveAsFlow()


    suspend fun translateServerError(errorSource: Throwable?) {
        errorSource?.message?.let {
            try {
                var startIndex = -1
                var endIndex = -1
                if (it.contains("title")) {
                    val index = it.indexOf("title")

                    for (i: Int in index + 6..it.length) {
                        if (it[i] == '\"') {
                            if (startIndex == -1) {
                                startIndex = i
                            } else {
                                endIndex = i
                                break
                            }
                        }
                    }

                }
                _errorMessage.send(it.substring(startIndex+1, endIndex) ?: "Something went wrong!")
            } catch (ex: Throwable) {

            }
        }
    }

}

@kotlinx.serialization.Serializable
data class ServerErrorModel(
    val title: String? = null,
    val status: Int? = null,
    val detail: String? = null,
    val path: String? = null,
    val message: String? = null
)