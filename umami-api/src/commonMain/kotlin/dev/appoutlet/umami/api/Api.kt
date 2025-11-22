package dev.appoutlet.umami.api

import io.ktor.resources.Resource

@Resource("/api")
internal class Api {

    @Resource("/auth")
    class Auth(val parent: Api = Api()) {

        @Resource("/login")
        class Login(val parent: Auth = Auth())
    }
}
