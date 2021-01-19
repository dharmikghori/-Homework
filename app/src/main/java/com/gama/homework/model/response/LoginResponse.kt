package com.gama.homework.model.response

class LoginResponse : BaseResponse() {
    var user: User? = null
}

class User {
    var userId = 0
    var userName = ""
}
