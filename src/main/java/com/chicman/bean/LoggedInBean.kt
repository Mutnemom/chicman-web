package com.chicman.bean

import com.chicman.model.Member
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@SessionScope
@Component
open class LoggedInBean {
    @JvmField
    var loggedInUser: Member? = null
}