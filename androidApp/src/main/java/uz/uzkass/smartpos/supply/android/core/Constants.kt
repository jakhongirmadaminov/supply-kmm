package uz.uzkass.smartpos.supply.android.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object Constants {
    const val ZERO = "0"
    const val PHONE_NUMBER_PREFIX = "+998"
    const val PHONE_NUMBER_LENGTH = 13
    const val UZB_PHONE_NUMBER_LENGTH = 9
    const val PHONE_NUMBER_PREFIX_LENGTH = 4
    const val NOT_AUTHORIZED_STATUS_CODE = 401
    const val SERVER_ERROR_STATUS_CODE = 500
    const val PINCODE_LENGTH = 4
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm"
}
