package uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model

import kotlinx.serialization.SerialName

data class SubCategoryModel(
    @SerialName("id")
    val id: kotlin.Long? = null,

    @SerialName("name")
    val name: kotlin.String? = null,

    @SerialName("category")
    val category: kotlin.Boolean? = null,

    )