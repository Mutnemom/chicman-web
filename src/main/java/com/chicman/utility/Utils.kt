package com.chicman.utility

import com.chicman.model.Product
import com.vaadin.data.ValueContext
import com.vaadin.data.validator.EmailValidator
import com.vaadin.data.validator.StringLengthValidator
import com.vaadin.server.Page
import com.vaadin.server.Resource
import com.vaadin.server.StreamResource
import com.vaadin.server.UserError
import com.vaadin.shared.Position
import com.vaadin.ui.Label
import com.vaadin.ui.Notification
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.stream.Collectors

object Utils {
    fun notify(message: String?, delay: Int, position: Position?, icon: Resource?) {
        val notification = Notification(message)
        notification.delayMsec = delay
        notification.position = position
        notification.icon = icon
        notification.show(Page.getCurrent())
    }

    @JvmStatic
    fun float2Text(floatNumber: Float): String {
        var textNumber = floatNumber.toString()
        textNumber = textNumber.trim { it <= ' ' }
        textNumber = textNumber.substring(0, textNumber.indexOf("."))
        val stringBuilder = StringBuilder()
        if (textNumber.length > 6) {
            stringBuilder.append(textNumber.substring(0, textNumber.length - 6))
            stringBuilder.append(",")
            textNumber = textNumber.substring(textNumber.length - 6)
            textNumber = textNumber.substring(textNumber.indexOf(",") + 1)
        }
        if (textNumber.length > 3) {
            stringBuilder.append(textNumber.substring(0, textNumber.length - 3))
            stringBuilder.append(",")
            stringBuilder.append(textNumber.substring(textNumber.length - 3))
        } else {
            stringBuilder.append(textNumber)
        }
        return stringBuilder.toString()
    }

    fun isValidUsername(username: TextField): Boolean {
//        return username.value.matches("[A-Za-z0-9]+".toRegex())
        return username.value.isNotEmpty()
    }

    fun isValidPasswordLogin(password: PasswordField): Boolean {
        val validator = StringLengthValidator(Messages.get("error.password"), 6, 10)
        val validationResult = validator.apply(password.value, ValueContext(password))
        val error = validationResult.isError
        val userError: UserError
        if (error) {
            userError = UserError(validationResult.errorMessage)
            password.componentError = userError
        } else {
            password.componentError = null
        }
        return !error
    }

    fun isValidEmail(email: TextField): Boolean {
        email.placeholder = ""
        val validator = EmailValidator(Messages.get("error.email"))
        val result = validator.apply(email.value, ValueContext(email))
        if (result.isError) {
            val userError = UserError(result.errorMessage)
            email.componentError = userError
        } else {
            email.componentError = null
        }
        return !result.isError
    }

    @JvmStatic
    fun isValidExpiryDate(expiryDate: TextField): Boolean {
        expiryDate.placeholder = Messages.get("form.credit.expire.placeholder")
        var error = false
        val value = expiryDate.value
        val userError = UserError(Messages.get("form.credit.expire.error"))
        if (value.length >= 4) {
            if (value.length == 4) {
                if (value.matches("[0-9]+".toRegex())) {
                    val mon = value.substring(0, 2)
                    val year = value.substring(2)
                    expiryDate.value = "$mon/$year"
                } else {
                    error = true
                }
            } else if (!value.matches("(\\d{2})/(\\d{2})".toRegex())) {
                error = true
            }
        } else {
            error = true
        }
        if (error) {
            expiryDate.componentError = userError
        } else {
            expiryDate.componentError = null
        }
        return !error
    }

    fun isValidPassword(password: PasswordField): Boolean {
        val validator = StringLengthValidator("รหัสผ่าน 6-10 ตัว", 6, 10)
        val validationResult = validator.apply(password.value, ValueContext(password))
        var error = validationResult.isError
        val userError: UserError
        if (error) {
            userError = UserError(validationResult.errorMessage)
            password.componentError = userError
        } else {
            if (password.value.matches("[A-Za-z0-9]+".toRegex())) {
                if (password.value.matches("[A-Za-z]+".toRegex())) {
                    error = true
                    userError = UserError(Messages.get("auth.password.alpha.only"))
                    password.componentError = userError
                } else if (password.value.matches("[0-9]+".toRegex())) {
                    error = true
                    userError = UserError(Messages.get("auth.password.numeric.only"))
                    password.componentError = userError
                } else {
                    error = false
                    password.componentError = null
                }
            } else {
                error = true
                userError = UserError(Messages.get("auth.password.alphanumeric.only"))
                password.componentError = userError
            }
        }
        return !error
    }

    fun isValidPasswordConfirm(passwordConfirm: PasswordField, password: PasswordField): Boolean {
        var error = false
        if (!passwordConfirm.value.isEmpty() && passwordConfirm.value == password.value) {
            passwordConfirm.componentError = null
        } else {
            passwordConfirm.componentError = UserError(Messages.get("auth.password.confirm.failed"))
            error = true
        }
        return !error
    }

    @JvmStatic
    fun text2Double(value: String?, defaultValue: Double): Double {
        var doubleValue: Double
        if (value == null) {
            doubleValue = 0.0
        } else {
            try {
                doubleValue = value.toDouble()
            } catch (ex: NumberFormatException) {
                doubleValue = defaultValue
                ex.printStackTrace()
            }
        }
        return doubleValue
    }

    fun filterMenWatchList(productList: List<Product>?): List<Product> {
        return when (productList) {
            null -> listOf()
            else -> productList.stream()
                .filter { product: Product -> product.productCategoriesId == 2 }
                .collect(Collectors.toList())
        }
    }

    fun filterWomenWatchList(productList: List<Product>?): List<Product> {
        return when (productList) {
            null -> listOf()
            else -> productList.stream()
                .filter { product: Product -> product.productCategoriesId == 3 }
                .collect(Collectors.toList())
        }
    }

    fun ratingClassified(productRate: Label, product: Product) {
        when (product.productRate) {
            1 -> productRate.addStyleName("rating1")
            2 -> productRate.addStyleName("rating2")
            3 -> productRate.addStyleName("rating3")
            4 -> productRate.addStyleName("rating4")
            5 -> productRate.addStyleName("rating5")
        }
    }

    class ImageSource(private val image: ByteArray) : StreamResource.StreamSource {
        override fun getStream(): InputStream {
            return ByteArrayInputStream(image)
        }

        companion object {
            private const val serialVersionUID = -5616420547002465758L
        }

    }
}