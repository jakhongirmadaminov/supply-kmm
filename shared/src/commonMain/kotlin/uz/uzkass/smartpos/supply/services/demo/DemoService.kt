package uz.uzkass.smartpos.supply.services.demo

import uz.uzkass.smartpos.supply.core.data.LoginRequest

interface DemoService {

    suspend fun login(loginRequest: LoginRequest): String

}