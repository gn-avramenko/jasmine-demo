/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.core.model.domain.ObjectReferenceJS
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.server.demo.rest.DemoCreateUserRequestJS
import com.gridnine.jasmine.web.core.mainframe.MainFrame
import com.gridnine.jasmine.web.core.ui.ListButtonHandler
import com.gridnine.jasmine.web.core.ui.ObjectsList
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.demo.DemoRestClient
import com.gridnine.jasmine.web.demo.DemoUserNewAccountEditor
import com.gridnine.jasmine.web.demo.DemoWebMessagesJS

class DemoUserAccountCreateUserListButtonHandler :ListButtonHandler<DemoUserAccountIndexJS>{
    private val indexId = MiscUtilsJS.toServerClassName(DemoUserAccountIndexJS.indexId)
    override fun getId(): String {
        return "DemoUserAccountCreateUserListButton"
    }

    override fun getWeight(): Double {
        return 1.0
    }

    override fun isApplicable(objectId: String): Boolean {
        return objectId == indexId
    }

    override fun isEnabled(value: ObjectsList<DemoUserAccountIndexJS>): Boolean {
        return true
    }

    override fun onClick(value: ObjectsList<DemoUserAccountIndexJS>) {
        UiLibraryAdapter.get().showDialog<DemoUserNewAccountEditor>(value.getDataGrid()){
            title = DemoWebMessagesJS.CreateUser
            editor = DemoUserNewAccountEditor(null)
            button {
                displayName = DemoWebMessagesJS.Create
                handler = {wd ->
                    val request = DemoCreateUserRequestJS()
                    request.vm = wd.getContent().getData()
                    DemoRestClient.demo_demo_createUser(request).then {
                        if(it.objectUid != null){
                            wd.close()
                            MainFrame.get().openTab(ObjectReferenceJS(DemoUserAccountIndexJS.objectId, it.objectUid!!, null), true)
                            return@then
                        }
                        wd.getContent().showValidation(it.vv!!)
                    }
                }
            }
            cancelButton()
        }
    }

    override fun getIcon(): String? {
        return null
    }

    override fun getDisplayName(): String {
        return DemoWebMessagesJS.CreateUser
    }

}