/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.server.demo.rest.DemoChangePasswordRequestJS
import com.gridnine.jasmine.web.core.CoreWebMessagesJS
import com.gridnine.jasmine.web.core.mainframe.ObjectEditor
import com.gridnine.jasmine.web.core.ui.ObjectEditorButton
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.utils.UiUtils
import com.gridnine.jasmine.web.demo.DemoRestClient
import com.gridnine.jasmine.web.demo.DemoWebMessagesJS
import kotlin.browser.window

class DemoUserAccountChangePasswordButtonHandler:ObjectEditorButton<DemoUserAccountWebEditor> {
    override fun getId(): String {
        return DemoUserAccountChangePasswordButtonHandler::class.simpleName!!
    }

    override fun isApplicable(objectId: String): Boolean {
        return objectId == DemoUserAccountIndexJS.objectId
    }

    override fun getIcon(): String? {
        return null
    }

    override fun getDisplayName(): String {
        return DemoWebMessagesJS.changePassword
    }

    override fun getWeight(): Double {
        return 101.toDouble()
    }

    override fun onClick(value: ObjectEditor<DemoUserAccountWebEditor>) {
        UiLibraryAdapter.get().showDialog<DemoUserAccountChangePasswordWebEditor>(value.rootWebEditor){
            title = DemoWebMessagesJS.changePassword
            expandToMainFrame = false
            editor = DemoUserAccountChangePasswordWebEditor(null)
            button {
                displayName = CoreWebMessagesJS.ok
                handler = {dialog ->
                    val vm = dialog.getEditor().getData()
                    val request = DemoChangePasswordRequestJS()
                    request.userAccountUid = value.obj.objectUid!!
                    request.vm = vm
                    DemoRestClient.demo_demo_changePassword(request).then {
                        UiUtils.showMessage(it.message)
                        if(!it.success){
                            dialog.getEditor().showValidation(it.vv!!)
                            return@then
                        }
                        dialog.close()
                    }

                }
            }
            cancelButton()
        }
    }

    override fun isEnabled(value: ObjectEditor<DemoUserAccountWebEditor>): Boolean {
        return !value.readOnly
    }

}