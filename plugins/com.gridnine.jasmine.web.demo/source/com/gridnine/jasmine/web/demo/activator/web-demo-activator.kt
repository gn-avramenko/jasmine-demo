/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.activator

import com.gridnine.jasmine.web.core.common.ActivatorJS
import com.gridnine.jasmine.web.core.common.EnvironmentJS
import com.gridnine.jasmine.web.core.common.RegistryJS
import com.gridnine.jasmine.web.core.remote.launch
import kotlinx.browser.window

const val moduleId = "com.gridnine.jasmine.web.demo"

fun main() {
    EnvironmentJS.restBaseUrl = "/ui-rest"
    RegistryJS.get().register(WebDemoActivator())
    if(window.asDynamic().testMode as Boolean? == true){
        return
    }
    launch {
        RegistryJS.get().allOf(ActivatorJS.TYPE).forEach { it.activate() }
    }
}

class WebDemoActivator : ActivatorJS{
    override suspend fun activate() {
        console.log("demo module activated")
    }

    override fun getId(): String {
        return moduleId
    }

}