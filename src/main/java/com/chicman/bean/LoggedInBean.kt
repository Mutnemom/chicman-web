package com.chicman.bean

import com.chicman.model.Customer
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@SessionScope
@Component
class LoggedInBean {
    @JvmField
    var loggedInUser: Customer? = null
}