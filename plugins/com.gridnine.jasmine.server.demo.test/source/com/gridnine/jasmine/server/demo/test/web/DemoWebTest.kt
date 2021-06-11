package com.gridnine.jasmine.server.demo.test.web


import com.gridnine.jasmine.server.core.test.BaseWebTest

@Suppress("unused")
class DemoWebTest : BaseWebTest() {
    override fun getPluginId(): String {
        return "com.gridnine.jasmine.server.demo.test"
    }
}