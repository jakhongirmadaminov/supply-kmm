package uz.uzkass.smartpos.supply

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform