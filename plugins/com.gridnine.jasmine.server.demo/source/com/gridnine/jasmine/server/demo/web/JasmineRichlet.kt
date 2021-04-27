/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.web

import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndex
import com.gridnine.jasmine.common.standard.model.domain.ListWorkspaceItem
import com.gridnine.jasmine.common.standard.model.domain.SortOrder
import com.gridnine.jasmine.common.standard.model.domain.SortOrderType
import com.gridnine.jasmine.common.standard.model.l10n.StandardL10nMessagesFactory
import com.gridnine.jasmine.server.core.ui.components.TabTool
import com.gridnine.jasmine.server.standard.ui.mainframe.ListTabHandler
import com.gridnine.jasmine.server.standard.ui.mainframe.MainFrame
import com.gridnine.jasmine.server.standard.ui.mainframe.MainFrameTabHandler
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
            tools.add(TabTool("Нагрузочный тест"){
                page.desktop.enableServerPush(true);
                TestThread(page.desktop).start()
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

    class TestThread(private val desktop:Desktop): Thread(){
        override fun run() {
            while (true) {
                sleep(1500)
                val item = ListWorkspaceItem()
                item.listId = DemoUserAccountIndex::class.qualifiedName
                item.displayName =  "User accounts"
                item.columns.add(DemoUserAccountIndex.loginProperty.name)
                item.columns.add(DemoUserAccountIndex.nameProperty.name)
                item.sortOrders.add(SortOrder().let {
                    it.field = DemoUserAccountIndex.loginProperty.name
                    it.orderType = SortOrderType.ASC
                    it
                })
                val handler = ListTabHandler()
                Executions.activate(desktop);
                try {
                    MainFrame.get().openTab(handler as MainFrameTabHandler<Any>, item)
                } finally {
                    Executions.deactivate(desktop);
                }
                sleep(1500)
                Executions.activate(desktop);
                try {
                    MainFrame.get().closeAllTabs()
                } finally {
                    Executions.deactivate(desktop);
                }
            }
        }
    }

}