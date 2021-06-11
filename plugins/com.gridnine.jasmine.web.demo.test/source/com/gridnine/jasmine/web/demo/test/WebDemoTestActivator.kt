/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.test

import com.gridnine.jasmine.common.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.common.ActivatorJS
import com.gridnine.jasmine.web.core.common.EnvironmentJS
import com.gridnine.jasmine.web.core.ui.WebUiLibraryAdapter
import com.gridnine.jasmine.web.standard.StandardRestClient
import com.gridnine.jasmine.web.standard.mainframe.MainFrame

class WebDemoTestActivator:ActivatorJS {
    override suspend fun activate() {
        val mainFrame = MainFrame {
            title = "Jasmine"
            navigationWidth = 200
        }
        val workspace = StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())
        mainFrame.setWorkspace(workspace.workspace)
        EnvironmentJS.publish(mainFrame)
        WebUiLibraryAdapter.get().showWindow(mainFrame)
    }

    override fun getId(): String {
        return WebDemoTestActivator::class.simpleName!!
    }
}