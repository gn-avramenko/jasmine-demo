/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.test

import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserNewAccountEditorVVJS
import com.gridnine.jasmine.common.standard.model.rest.ListWorkspaceItemDTJS
import com.gridnine.jasmine.web.core.test.WebCoreIndividualTestBase
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.demo.ui.WebDemoCreateNewUserAccountListButtonHandler
import com.gridnine.jasmine.web.standard.mainframe.MainFrame

class DemoCreateNewAccountTest:WebCoreIndividualTestBase() {
    fun testCreateNewAccount(){
        test("create-new-account") {

            MainFrame.get().openTab(ListWorkspaceItemDTJS().also {
                it.columns.add("name")
                it.displayName = "User accounts"
                it.listId = DemoUserAccountIndexJS.indexId.substringBeforeLast("JS")
                it.uid = MiscUtilsJS.createUUID()
            })
            val dialog = WebDemoCreateNewUserAccountListButtonHandler().invokeAndReturnResult()
            dialog.getContent().apply {
                loginWidget.setValue("user100")
            }
            val result = dialog.simulateClick(0) as DemoUserNewAccountEditorVVJS
            assert.ok(result.password != null)
        }
    }
}