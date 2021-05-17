/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.activator

import com.gridnine.jasmine.common.core.model.BaseIndexJS
import com.gridnine.jasmine.common.demo.model.domain.DemoComplexDocumentIndexJS
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.common.standard.model.rest.GetListRequestJS
import com.gridnine.jasmine.common.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.common.ActivatorJS
import com.gridnine.jasmine.web.core.common.EnvironmentJS
import com.gridnine.jasmine.web.core.common.RegistryJS
import com.gridnine.jasmine.web.core.remote.WebCoreMetaRegistriesUpdater
import com.gridnine.jasmine.web.core.remote.launch
import com.gridnine.jasmine.web.core.ui.WebUiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.components.SimpleActionHandler
import com.gridnine.jasmine.web.core.ui.components.WebTabsContainerTool
import com.gridnine.jasmine.web.demo.DomainReflectionUtilsJS
import com.gridnine.jasmine.web.demo.UiReflectionUtilsJS
import com.gridnine.jasmine.web.demo.ui.DemoComplexDocumentNestedDocumentTileSpaceEditorInterceptor
import com.gridnine.jasmine.web.demo.ui.WebDemoComplexDocumentEditorHandler
import com.gridnine.jasmine.web.demo.ui.WebDemoUserAccountEditorHandler
import com.gridnine.jasmine.web.standard.ActionsIds
import com.gridnine.jasmine.web.standard.StandardRestClient
import com.gridnine.jasmine.web.standard.editor.OpenObjectData
import com.gridnine.jasmine.web.standard.editor.WebEditorInterceptorsRegistry
import com.gridnine.jasmine.web.standard.mainframe.ActionWrapper
import com.gridnine.jasmine.web.standard.mainframe.MainFrame
import com.gridnine.jasmine.web.standard.mainframe.WebActionsHandler
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
        val mainFrameTools = WebActionsHandler.get().getActionsFor(ActionsIds.standard_workspace_tools).actions.map {
            it as ActionWrapper
            WebTabsContainerTool().apply {
                displayName = it.displayName
                handler = {
                    it.getActionHandler<SimpleActionHandler>().invoke()
                }
            }
        }
        val mainFrame = MainFrame {
            title = "Jasmine"
            navigationWidth = 200
            tools.addAll(mainFrameTools)
        }
        val workspace = StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())
        mainFrame.setWorkspace(workspace.workspace)

        val idx = StandardRestClient.standard_standard_getList(GetListRequestJS().apply {
            listId = DemoComplexDocumentIndexJS.indexId.substringBeforeLast("JS")
            rows = 10
            page = 0
            columns.add("name")
        }).items[0] as BaseIndexJS
        val testItem = OpenObjectData(idx.document!!.type, idx.document!!.uid, "table")
//        val testItem = StandardRestClient.standard_standard_getWorkspaceItem(GetWorkspaceItemRequestJS().apply {
//            uid = workspace.workspace.groups.flatMap { it.items }[1].id
//        })

        window.asDynamic().testItem = testItem
        EnvironmentJS.publish(mainFrame)
        WebUiLibraryAdapter.get().showWindow(mainFrame)
    }
}

class WebDemoActivator : ActivatorJS{
    override suspend fun activate() {
        WebCoreMetaRegistriesUpdater.updateMetaRegistries(pluginId)
        DomainReflectionUtilsJS.registerWebDomainClasses()
        UiReflectionUtilsJS.registerWebUiClasses()
        RegistryJS.get().register(WebDemoUserAccountEditorHandler())
        RegistryJS.get().register(WebDemoComplexDocumentEditorHandler())
        WebEditorInterceptorsRegistry.get().register(DemoComplexDocumentNestedDocumentTileSpaceEditorInterceptor())
        console.log("demo module activated")
    }

    override fun getId(): String {
        return pluginId
    }

}