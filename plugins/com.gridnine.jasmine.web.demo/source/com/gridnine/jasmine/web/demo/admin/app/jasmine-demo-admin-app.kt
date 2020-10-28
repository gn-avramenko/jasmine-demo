/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.app

import com.gridnine.jasmine.server.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.StandardRestClient
import com.gridnine.jasmine.web.core.activator.CoreActivatorJS
import com.gridnine.jasmine.web.core.application.EnvironmentJS
import com.gridnine.jasmine.web.core.mainframe.MainFrame
import com.gridnine.jasmine.web.core.remote.StandardRpcManager
import com.gridnine.jasmine.web.core.ui.ClientRegistry
import com.gridnine.jasmine.web.core.ui.ObjectHandler
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.components.MenuButtonConfiguration
import com.gridnine.jasmine.web.core.ui.components.StandardMenuItem
import com.gridnine.jasmine.web.demo.DemoWebMessagesInitializerJS
import com.gridnine.jasmine.web.demo.DomainReflectionUtilsJS
import com.gridnine.jasmine.web.demo.RestReflectionUtilsJS
import com.gridnine.jasmine.web.demo.UiReflectionUtilsJS
import com.gridnine.jasmine.web.demo.admin.user.DemoUserAccountObjectHandler
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
    DomainReflectionUtilsJS.registerWebDomainClasses()
    UiReflectionUtilsJS.registerWebUiClasses()
    RestReflectionUtilsJS.registerWebRestClasses()

    coreActivator.activate().then { StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())}.then {

        var mainFrame = MainFrame()
        mainFrame.configure {
            tools.add(MenuButtonConfiguration{
                icon = "core:settings"
                elements.add(StandardMenuItem{
                    title = "Выход"
                    handler = {
                        window.alert("Выход")
                    }
                })
            })


        }
        mainFrame.build(it.workspace)
        ClientRegistry.get().register(DemoUserAccountObjectHandler())
        DemoWebMessagesInitializerJS.initialize()
        UiLibraryAdapter.get().showWindow(mainFrame)
        EnvironmentJS.publish(mainFrame)

    }
}