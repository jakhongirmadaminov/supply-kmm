package uz.uzkass.smartpos.supply


public abstract class PagingSource<T> {


}

class PagingConfig constructor(
    val pageSize: Int = 15,
    val pageMax: Int = Int.MAX_VALUE
)

class Pager<T> constructor(
    val pagingSource: PagingSource<T>,
    val pagingConfig: PagingConfig
) {

    val itemList = mutableListOf<T>()

    fun getSize(): Int {
        return itemList.size
    }

    fun getItem(index: Int) {

    }




}

