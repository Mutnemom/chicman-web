package com.chicman.bean

import com.chicman.model.Favourite
import com.chicman.model.Member
import com.chicman.model.Product
import com.chicman.repository.FavouriteRepository
import com.chicman.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.util.*

@SessionScope
@Component
open class FavouriteBean {

    @Autowired
    private val favouriteRepo: FavouriteRepository? = null
    @Autowired
    private val productRepo: ProductRepository? = null

    private val favouriteList: MutableList<Product?>? = mutableListOf()
    private var loggedInUser: Member? = null

    val favourites: MutableList<Product>
        get() = favouriteList?.filterNotNull()?.toMutableList() ?: mutableListOf()


    fun attachToCustomerFavourite(member: Member) {
        this.loggedInUser = member
        var itemFavourite: Favourite
        favourites.forEach {
            itemFavourite = Favourite(9 /* loggedInUser.id */, it.productId)
            favouriteRepo!!.saveAndFlush(itemFavourite)
        }
        getFavouritesFromDatabase(loggedInUser!!)
    }

    private fun getFavouritesFromDatabase(loggedInUser: Member) {
        resetProductList()
        var storedProduct: Optional<Product?>
        val favouriteOfLoggedInUserList =
            favouriteRepo!!.findByCustomerId(
                9 /* loggedInUser.id */,
                Sort.by(Sort.Direction.DESC, "submission")
            )

        for (item in favouriteOfLoggedInUserList) {
            storedProduct = productRepo!!.findById(item.productId)
            storedProduct.ifPresent { favouriteList?.add(it) }
        }
    }

    fun resetProductList() {
        favouriteList?.clear()
    }

    fun addProductToFavourite(product: Product?) {
        if (loggedInUser != null) {
            val item = Favourite(9 /* loggedInUser.id */, product!!.productId)
            favouriteRepo!!.saveAndFlush(item)
            getFavouritesFromDatabase(loggedInUser!!)
        } else {
            favouriteList?.add(product)
        }
    }

}