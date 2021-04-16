/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.web

import com.gridnine.jasmine.common.standard.model.l10n.StandardL10nMessagesFactory
import com.gridnine.jasmine.server.core.ui.components.TabTool
import com.gridnine.jasmine.server.standard.ui.mainframe.MainFrame
import com.gridnine.jasmine.server.standard.ui.mainframe.workspace.WorkspaceEditorTabHandler
import com.gridnine.jasmine.server.zk.ui.components.findZkComponent
import org.zkoss.zk.ui.*
import org.zkoss.zul.Div


class JasmineRichlet : GenericRichlet() {

    override fun service(page: Page) {
        page.title = "Jasmine"
        val mainFrame = MainFrame{
            title = "Jasmine"
            tools.add(TabTool(StandardL10nMessagesFactory.Workspace_editor()){
                MainFrame.get().openTab(WorkspaceEditorTabHandler(), Unit)
            })
        }
        val comp = findZkComponent(mainFrame).getZkComponent()
        val div = Div()
        div.hflex = "1"
        div.vflex = "1"
        div.appendChild(comp)
        div.attributes["rootComponent"] = mainFrame
        div.page = page
    }

}