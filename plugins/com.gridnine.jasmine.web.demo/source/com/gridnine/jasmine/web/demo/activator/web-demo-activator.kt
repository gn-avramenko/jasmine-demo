/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.activator

import com.gridnine.jasmine.common.standard.model.rest.GetWorkspaceItemRequestJS
import com.gridnine.jasmine.common.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.common.ActivatorJS
import com.gridnine.jasmine.web.core.common.EnvironmentJS
import com.gridnine.jasmine.web.core.common.RegistryJS
import com.gridnine.jasmine.web.core.remote.launch
import com.gridnine.jasmine.web.standard.StandardRestClient
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
        val workspace = StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())
        console.log(workspace)
        val item = StandardRestClient.standard_standard_getWorkspaceItem(GetWorkspaceItemRequestJS().apply { uid =workspace.workspace.groups.flatMap { it.items }.last().id })
        console.log(item)
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