/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.app

import com.gridnine.jasmine.server.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.CoreWebMessagesJS
import com.gridnine.jasmine.web.core.StandardRestClient
import com.gridnine.jasmine.web.core.activator.CoreActivatorJS
import com.gridnine.jasmine.web.core.mainframe.MainFrame
import com.gridnine.jasmine.web.core.remote.StandardRpcManager
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.components.LinkButtonConfiguration
import com.gridnine.jasmine.web.core.ui.components.MenuButtonConfiguration
import com.gridnine.jasmine.web.core.ui.components.StandardMenuItem
import com.gridnine.jasmine.web.easyui.activator.EasyUiActivator
import kotlin.browser.window

fun main() {
    if(window.asDynamic().testMode){
        return
    }
    val config = hashMapOf<String,Any?>()
    config[StandardRpcManager.BASE_REST_URL_KEY] = "/ui-rest"
    val coreActivator = CoreActivatorJS()
    coreActivator.configure(config)
    EasyUiActivator().configure(config)
    coreActivator.activate().then { StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())}.then {
        var mainFrame = MainFrame()
        mainFrame.tools.add(MenuButtonConfiguration{
            title = CoreWebMessagesJS.settings
            elements.add(StandardMenuItem{
                title = "Выход"
                handler = {
                    window.alert("Выход")
                }
            })
        })
        mainFrame.initialize(it.workspace)
        UiLibraryAdapter.get().showWindow(mainFrame)
    }
}