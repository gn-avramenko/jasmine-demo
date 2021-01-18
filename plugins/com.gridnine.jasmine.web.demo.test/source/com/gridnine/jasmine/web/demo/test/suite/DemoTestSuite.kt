package com.gridnine.jasmine.web.demo.test.suite


import com.gridnine.jasmine.server.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.StandardRestClient
import com.gridnine.jasmine.web.core.activator.CoreActivatorJS
import com.gridnine.jasmine.web.core.application.EnvironmentJS
import com.gridnine.jasmine.web.core.mainframe.MainFrame
import com.gridnine.jasmine.web.core.mainframe.workspaceEditor.WorkspaceEditorTabHandler
import com.gridnine.jasmine.web.core.remote.StandardRpcManager
import com.gridnine.jasmine.web.core.test.activator.CoreTestActivator
import com.gridnine.jasmine.web.core.test.ext.before
import com.gridnine.jasmine.web.core.test.ext.describe
import com.gridnine.jasmine.web.core.test.ext.it
import com.gridnine.jasmine.web.core.ui.ClientRegistry
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.components.MenuButtonConfiguration
import com.gridnine.jasmine.web.core.ui.components.StandardMenuItem
import com.gridnine.jasmine.web.demo.DemoWebMessagesInitializerJS
import com.gridnine.jasmine.web.demo.DomainReflectionUtilsJS
import com.gridnine.jasmine.web.demo.RestReflectionUtilsJS
import com.gridnine.jasmine.web.demo.UiReflectionUtilsJS
import com.gridnine.jasmine.web.demo.admin.complex.DemoComplexDocumentObjectHandler
import com.gridnine.jasmine.web.demo.admin.user.DemoUserAccountChangePasswordButtonHandler
import com.gridnine.jasmine.web.demo.admin.user.DemoUserAccountCreateUserListButtonHandler
import com.gridnine.jasmine.web.demo.admin.user.DemoUserAccountObjectHandler
import com.gridnine.jasmine.web.easyui.adapter.EasyUiLibraryAdapter
import kotlin.browser.window
import kotlin.collections.hashMapOf
import kotlin.collections.set
import kotlin.js.Promise

class DemoTestSuite {
    fun describeSuite() {
        describe("demo-test-suite") {
            buildBefore()
            CreateNewAccountTest().createNewAccountTest()
        }
    }

    fun buildBefore() {
        before {
            EnvironmentJS.test = true
            val config = hashMapOf<String, Any?>()
            config[StandardRpcManager.BASE_REST_URL_KEY] = "/ui-rest"
            val coreActivator = CoreActivatorJS()
            coreActivator.configure(config)
            val testActivator = CoreTestActivator()
            testActivator.configure("http://localhost:8080/ui-rest")
            DomainReflectionUtilsJS.registerWebDomainClasses()
            RestReflectionUtilsJS.registerWebRestClasses()
            UiReflectionUtilsJS.registerWebUiClasses()
            coreActivator.activate().then {
                StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())
            }.then {
                EnvironmentJS.publish(UiLibraryAdapter::class, EasyUiLibraryAdapter())
                var mainFrame = MainFrame()
                mainFrame.configure {
                    tools.add(MenuButtonConfiguration{
                        icon = "core:settings"
                        elements.add(StandardMenuItem{
                            title = "Редактор рабочей области"
                            handler = {
                                mainFrame.openTab(WorkspaceEditorTabHandler(), Unit)
                            }
                        })
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
                ClientRegistry.get().register(DemoUserAccountChangePasswordButtonHandler())
                ClientRegistry.get().register(DemoUserAccountCreateUserListButtonHandler())
                ClientRegistry.get().register(DemoComplexDocumentObjectHandler())
                DemoWebMessagesInitializerJS.initialize()
                UiLibraryAdapter.get().showWindow(mainFrame)
                EnvironmentJS.publish(mainFrame)
            }
        }
    }
}