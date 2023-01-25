//package uz.uzkass.smartpos.supply.usecases.demo
//
//import kotlinx.coroutines.flow.flow
//import uz.uzkass.smartpos.supply.core.data.LoginRequest
//import uz.uzkass.smartpos.supply.services.demo.DemoService
//
//class DemoUseCaseImpl(
//    private val demoService: DemoService
//) : DemoUseCase {
//
//    override fun getCurrentAccount() = flow<MainData> {
//        emit(demoService.getCurrentAccount())
//    }
//
//    override fun login(loginRequest: LoginRequest) = flow<String> {
//        emit(demoService.login(loginRequest))
//    }
//
//}