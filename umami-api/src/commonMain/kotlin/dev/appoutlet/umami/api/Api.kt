package dev.appoutlet.umami.api

import io.ktor.resources.Resource

@Resource("/api")
internal class Api {

    @Resource("/auth")
    class Auth(val parent: Api = Api()) {

        @Resource("/login")
        class Login(val parent: Auth = Auth())

        @Resource("/verify")
        class Verify(val parent: Auth = Auth())

        @Resource("/logout")
        class Logout(val parent: Auth = Auth())
    }

    @Resource("/admin")
    class Admin(val parent: Api = Api()) {

        @Resource("/users")
        class Users(val parent: Admin = Admin())

        @Resource("/websites")
        class Websites(val parent: Admin = Admin())

        @Resource("/teams")
        class Teams(val parent: Admin = Admin())
    }

    @Resource("/links")
    class Links(val parent: Api = Api()) {

        @Resource("/{id}")
        class Id(val parent: Links = Links(), val id: String)
    }

    @Resource("/websites")
    class Websites(val parent: Api = Api()) {

        @Resource("/{id}")
        class Id(val parent: Websites = Websites(), val id: String) {

            @Resource("/reset")
            class Reset(val parent: Id)
        }
    }

    @Resource("/me")
    class Me(val parent: Api = Api()) {

        @Resource("/teams")
        class Teams(val parent: Me = Me())

        @Resource("/websites")
        class Websites(val parent: Me = Me())
    }
}
