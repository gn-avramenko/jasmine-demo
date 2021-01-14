package com.gridnine.jasmine.web.demo.test.suite


import com.gridnine.jasmine.web.core.activator.CoreActivatorJS
import com.gridnine.jasmine.web.core.remote.StandardRpcManager
import com.gridnine.jasmine.web.core.test.activator.CoreTestActivator
import com.gridnine.jasmine.web.core.test.ext.before
import com.gridnine.jasmine.web.core.test.ext.describe
import com.gridnine.jasmine.web.core.test.ext.it
import com.gridnine.jasmine.web.demo.DomainReflectionUtilsJS
import com.gridnine.jasmine.web.demo.RestReflectionUtilsJS
import com.gridnine.jasmine.web.demo.UiReflectionUtilsJS
import kotlin.collections.hashMapOf
import kotlin.collections.set
import kotlin.js.Promise

class DemoTestSuite{
    fun describeSuite(){
        describe("demo-test-suite"){
            buildBefore()
            CreateNewAccountTest().createNewAccountTest()
        }
    }

    fun buildBefore(){
        before {
            val config = hashMapOf<String,Any?>()
            config[StandardRpcManager.BASE_REST_URL_KEY] = "/ui-rest"
            val coreActivator = CoreActivatorJS()
            coreActivator.configure(config)
            val testActivator = CoreTestActivator()
            testActivator.configure("http://localhost:8080/ui-rest")
            DomainReflectionUtilsJS.registerWebDomainClasses()
            RestReflectionUtilsJS.registerWebRestClasses()
            UiReflectionUtilsJS.registerWebUiClasses()
            coreActivator.activate().then {
                Promise<Unit>{resolve, reject ->
                    resolve(Unit)
                }
            }
        }
    }
}