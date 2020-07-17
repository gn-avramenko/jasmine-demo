/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: jasmine
 * This file is auto generated, don't modify it manually
 *****************************************************************/

@file:Suppress("unused", "RemoveRedundantQualifierName", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "RemoveEmptyPrimaryConstructor", "FunctionName")

package com.gridnine.jasmine.server.demo.rest


import com.gridnine.jasmine.server.core.storage.Storage
import com.gridnine.jasmine.server.core.utils.DesUtils
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndex
import org.slf4j.LoggerFactory
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val authInfo = ThreadLocal<AuthInfo>()

class AuthInfo(val loginName: String)

class DemoAuthFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val restId = httpRequest.pathInfo.substring(1)
        val cookie = httpRequest.cookies?.find { it.name == AUTH_COOKIE }
        var cookieValue = cookie?.value
        if (cookieValue == null) {
            cookieValue = httpRequest.getHeader("AUTH_COOKIE")
        }
        if (cookieValue == null) {
            if (restId == "demo_auth_checkAuth" || restId == "standard_standard_meta" || restId == "demo_auth_login") {
                chain.doFilter(request, response)
                return
            }
            httpResponse.status = 403
            return
        }
        if (restId == "logout") {
            if (cookie != null) {
                cookie.maxAge = 0
                response.addCookie(cookie)
            }
            return
        }
        val decoded = DesUtils.decode(cookieValue).split("|")
        try {
            val login = decoded[0]
            val password = decoded[1]
            val userAccount = Storage.get().findUniqueDocument(DemoUserAccountIndex::class, DemoUserAccountIndex.login, login)
            if (userAccount == null || password != userAccount.password) {
                if (cookie != null) {
                    cookie.maxAge = 0
                    response.addCookie(cookie)
                }
                httpResponse.status = 403
                return
            }
            authInfo.set(AuthInfo(login))
        } catch (e: Exception) {
            LoggerFactory.getLogger(javaClass).error("unable to check credentials", e)
            if(cookie != null) {
                cookie.maxAge = 0
                response.addCookie(cookie)
                httpResponse.status = 403
            }
            return
        }
        try {
            chain.doFilter(request, response)
        } finally {
            authInfo.remove()
        }
    }

    companion object {
        const val AUTH_COOKIE = "AUTH_COOKIE"
        fun getAuthInfo(): AuthInfo? {
            return authInfo.get()
        }

        fun setCookie(login: String, password: String, response: HttpServletResponse) {
            val cookie = Cookie(AUTH_COOKIE, DesUtils.encode("$login|$password"))
            cookie.maxAge = 2147483647
            response.addCookie(cookie)
        }

        fun removeCookie(response: HttpServletResponse) {
            val cookie = Cookie(AUTH_COOKIE, null)
            cookie.maxAge = 0
            response.addCookie(cookie)
        }
    }

}