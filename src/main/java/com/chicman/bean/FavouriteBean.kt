package com.chicman.bean

import com.chicman.model.Customer
import com.chicman.model.Favourite
import com.chicman.model.Product
import com.chicman.repository.FavouriteRepository
import com.chicman.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.util.*
import javax.validation.constraints.NotNull

@SessionScope
@Component
class FavouriteBean {

    @Autowired
    private val favouriteRepo: FavouriteRepository? = null
    @Autowired
    private val productRepo: ProductRepository? = null

    private val favouriteList: MutableList<Product?>
    private var loggedInUser: Customer? = null

    fun getFavouriteList(): List<Product?> {
        return favouriteList
    }

    fun attachToCustomerFavourite(loggedInUser: Customer) {
        this.loggedInUser = loggedInUser
        var itemFavourite: Favourite
        for (product in favouriteList) {
            itemFavourite = Favourite(loggedInUser.customerId, product!!.productId)
            favouriteRepo!!.saveAndFlush(itemFavourite)
        }
        getFavouritesFromDatabase(loggedInUser)
    }

    private fun getFavouritesFromDatabase(loggedInUser: Customer) {
        resetProductList()
        var storedProduct: Optional<Product?>
        val favouriteOfLoggedInUserList =
            favouriteRepo!!.findByCustomerId(
                loggedInUser.customerId,
                Sort.by(Sort.Direction.DESC, "submission")
            )

        for (item in favouriteOfLoggedInUserList) {
            storedProduct = productRepo!!.findById(item.productId)
            storedProduct.ifPresent { favouriteList.add(it) }
        }
    }

    fun resetProductList() {
        favouriteList.clear()
    }

    fun addProductToFavourite(product: @NotNull Product?) {
        if (loggedInUser != null) {
            val item = Favourite(loggedInUser!!.customerId, product!!.productId)
            favouriteRepo!!.saveAndFlush(item)
            getFavouritesFromDatabase(loggedInUser!!)
        } else {
            favouriteList.add(product)
        }
    }

    init {
        favouriteList = ArrayList()
    }
}