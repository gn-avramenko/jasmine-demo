/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.activator

import com.gridnine.jasmine.common.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.common.ActivatorJS
import com.gridnine.jasmine.web.core.common.EnvironmentJS
import com.gridnine.jasmine.web.core.common.RegistryJS
import com.gridnine.jasmine.web.core.remote.launch
import com.gridnine.jasmine.web.core.ui.WebUiLibraryAdapter
import com.gridnine.jasmine.web.standard.StandardRestClient
import com.gridnine.jasmine.web.standard.mainframe.MainFrame
import kotlinx.browser.window

const val pluginId = "com.gridnine.jasmine.web.demo"

fun main() {
    EnvironmentJS.restBaseUrl = "/ui-rest"
    RegistryJS.get().register(WebDemoActivator())
    if(window.asDynamic().testMode as Boolean? == true){
        return
    }
    launch {
        RegistryJS.get().allOf(ActivatorJS.TYPE).forEach { it.activate() }
//        console.log(workspace)
//        val item = StandardRestClient.standard_standard_getWorkspaceItem(GetWorkspaceItemRequestJS().apply { uid =workspace.workspace.groups.flatMap { it.items }.last().id })
//        console.log(item)
        val mainFrame = MainFrame {
            title = "Jasmine"
            navigationWidth = 200
        }
        val workspace = StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())
        mainFrame.setWorkspace(workspace.workspace)
        EnvironmentJS.publish(mainFrame)
        WebUiLibraryAdapter.get().showWindow(mainFrame)
    }
}

class WebDemoActivator : ActivatorJS{
    override suspend fun activate() {
        console.log("demo module activated")
    }

    override fun getId(): String {
        return pluginId
    }

}