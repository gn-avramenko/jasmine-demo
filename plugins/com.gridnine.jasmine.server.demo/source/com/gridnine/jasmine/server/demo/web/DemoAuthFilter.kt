/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: jasmine
 * This file is auto generated, don't modify it manually
 *****************************************************************/

@file:Suppress("unused", "RemoveRedundantQualifierName", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "RemoveEmptyPrimaryConstructor", "FunctionName")

package com.gridnine.jasmine.server.demo.web


import com.gridnine.jasmine.server.core.utils.AuthUtils
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse


class DemoAuthFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        AuthUtils.setCurrentUser("admin");
        try {
            chain.doFilter(request, response)
        } finally {
            AuthUtils.resetCurrentUser()
        }
    }

}