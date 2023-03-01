package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models

import dev.icerock.moko.network.generated.models.UnitDTO
import dev.icerock.moko.network.generated.models.Vat
import kotlinx.serialization.SerialName

data class ProductItemModel(
    val id: Long?,
    val label: String,
    val price: Double,

    val unitId: Long,
    val vatAmount: Double
) : java.io.Serializable
