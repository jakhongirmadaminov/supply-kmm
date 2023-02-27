package uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model

import kotlinx.serialization.SerialName

data class CategoryModel(

    @SerialName("id")
    val id: kotlin.Long? = null,

    @SerialName("name")
    val name: kotlin.String? = null,

    @SerialName("parentId")
    val parentId: kotlin.Long? = null,

    @SerialName("productCount")
    val productCount: kotlin.Long? = null,

    )