package uz.uzkass.smartpos.supply.usecases.demo

import kotlinx.coroutines.flow.Flow
import uz.uzkass.smartpos.supply.core.data.LoginRequest

interface DemoUseCase {

    fun getCurrentAccount(): Flow<MainData>

    fun login(loginRequest: LoginRequest) : Flow<String>

}