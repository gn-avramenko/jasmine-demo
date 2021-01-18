package com.gridnine.jasmine.web.demo.test.suite

import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.server.standard.model.domain.ListWorkspaceItemJS
import com.gridnine.jasmine.web.core.mainframe.ListWorkspaceItemHandler
import com.gridnine.jasmine.web.core.mainframe.MainFrame
import com.gridnine.jasmine.web.core.test.ext.Assert
import com.gridnine.jasmine.web.core.test.ext.describe
import com.gridnine.jasmine.web.core.test.ext.it
import com.gridnine.jasmine.web.core.ui.TestableWebDialog
import com.gridnine.jasmine.web.core.ui.WebDialog
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.demo.DemoUserNewAccountEditor
import com.gridnine.jasmine.web.demo.DemoUserNewAccountEditorVVJS
import kotlin.js.Promise

class CreateNewAccountTest {
    fun createNewAccountTest() {
        val assert = com.gridnine.jasmine.web.core.test.ext.require("assert") as Assert
        describe("work-with-account") {
            it("test-create-new-account") {
                val item = ListWorkspaceItemJS()
                item.listId = DemoUserAccountIndexJS.indexId.substringBeforeLast("JS")
                item.uid = MiscUtilsJS.createUUID()
                item.displayName = "Учетные записи"
                MainFrame.get().openTab(ListWorkspaceItemHandler(), item).then { list ->
                    list as ListWorkspaceItemHandler.ListPanel
                    list.simulateButtonClick("DemoUserAccountCreateUserListButton")
                }.then {
                    val dialog = it as TestableWebDialog<DemoUserNewAccountEditor>
                    dialog.getContent().loginWidget.setValue("user100")
                    dialog.simulateButtonClick(0)
                }.then {
                    it as DemoUserNewAccountEditorVVJS
                    assert.ok(it.password != null)
                }.catch {
                    console.log(it)
                }
            }
        }
    }
}