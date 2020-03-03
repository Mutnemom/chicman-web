package com.chicman.bean

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@SessionScope
@Component
open class GuestBean {
    var token: String? = null
}