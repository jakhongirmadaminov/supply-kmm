package uz.uzkass.smartpos.supply.android.ui.main.home

import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.uzkass.smartpos.supply.android.ui.main.home.data.ResponseHome
import uz.uzkass.smartpos.supply.core.utils.getJson

interface HomeService {

    fun getUserStatistic(): Flow<ResponseHome>

}

class HomeServiceImpl constructor(private val authHttpClient: HttpClient) : HomeService {
    override fun getUserStatistic(): Flow<ResponseHome> {
        return flow {
            emit(authHttpClient.getJson(HOME_STATISTIC_URL))
        }
    }


    companion object{
        const val HOME_STATISTIC_URL = "api/mobile/v1/dashboard/order-statistics"
    }

}

