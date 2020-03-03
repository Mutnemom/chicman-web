package com.chicman.bean

import com.chicman.model.Checkout
import com.chicman.model.Member
import com.chicman.model.Product
import com.chicman.repository.CheckoutRepository
import com.chicman.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.util.*

@SessionScope
@Component
open class CartBean {

    @Autowired
    private val checkoutRepo: CheckoutRepository? = null

    @Autowired
    private val productRepo: ProductRepository? = null

    var productList: MutableList<Product> = ArrayList()

    private var member: Member? = null

    fun addProductToCart(product: Product) {
        if (member != null) {
            val itemCheckout = Checkout(9 /* member.id */, product.productId)
            checkoutRepo!!.saveAndFlush(itemCheckout)
        }
        productList.add(product)
    }

    fun resetProductList() {
        productList.clear()
    }

    fun removeProductFromCart(removeItem: Product) {
        if (member != null) {
            var checkItem: Checkout
            val checkList = checkoutRepo!!.findByCustomerId(9 /* member.id */)
            for (i in checkList.size downTo 1) {
                checkItem = checkList[i - 1]
                if (!checkItem.checkedOut && checkItem.productId == removeItem.productId) {
                    checkoutRepo.delete(checkItem)
                    break
                }
            }
        }
        productList.remove(removeItem)
    }

    fun attachToCustomerCart(member: Member) {
        this.member = member
        var itemCheckout: Checkout

        for (product in productList) {
            itemCheckout = Checkout(
                9, /* this.member.id */
                product.productId
            )

            checkoutRepo!!.saveAndFlush(itemCheckout)
        }

        productList = ArrayList()
        var storedProduct: Optional<Product>
        val checkList = checkoutRepo!!.findByCustomerId(9 /* this.member.id */)

        for (item in checkList) {
            if (!item.checkedOut) {
                storedProduct = productRepo!!.findById(item.productId)
                storedProduct.ifPresent { product: Product -> productList.add(product) }
            }
        }
    }

    fun checkoutAllProductFromCart() {
        if (member != null) {
            for (removeItem in productList) {
                val checkList = checkoutRepo!!.findByCustomerId(9 /* member.id */)
                for (checkItem in checkList) {
                    if (!checkItem.checkedOut && checkItem.productId == removeItem.productId) {
                        checkItem.checkedOut = true
                        checkItem.checkedTime = Date()
                        checkoutRepo.saveAndFlush(checkItem)
                        break
                    }
                }
            }
        }
        productList = ArrayList()
    }
}