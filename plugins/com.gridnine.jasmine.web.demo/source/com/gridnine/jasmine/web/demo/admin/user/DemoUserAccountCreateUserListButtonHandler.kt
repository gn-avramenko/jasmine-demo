/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.core.model.domain.ObjectReferenceJS
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.server.demo.rest.DemoCreateUserRequestJS
import com.gridnine.jasmine.web.core.mainframe.MainFrame
import com.gridnine.jasmine.web.core.ui.*
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.demo.DemoRestClient
import com.gridnine.jasmine.web.demo.DemoUserNewAccountEditor
import com.gridnine.jasmine.web.demo.DemoWebMessagesJS
import kotlin.js.Promise

class DemoUserAccountCreateUserListButtonHandler :TestableListButtonHandler<DemoUserAccountIndexJS, WebDialog<DemoUserNewAccountEditor>>(){
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

    override fun onTestClick(value: ObjectsList<DemoUserAccountIndexJS>): Promise<WebDialog<DemoUserNewAccountEditor>> {
        val dialog = UiLibraryAdapter.get().showDialog<DemoUserNewAccountEditor>(value.getDataGrid()){
            title = DemoWebMessagesJS.CreateUser
            editor = DemoUserNewAccountEditor(null)
            button {
                displayName = DemoWebMessagesJS.Create
                testableHandler = {wd ->
                    Promise {resolve, reject ->
                        val request = DemoCreateUserRequestJS()
                        request.vm = wd.getContent().getData()
                        DemoRestClient.demo_demo_createUser(request).then {
                            if (it.objectUid != null) {
                                wd.close()
                                val res = MainFrame.get().openTab(ObjectReferenceJS(DemoUserAccountIndexJS.objectId, it.objectUid!!, null), true)
                                res.then {
                                    resolve(it!!)
                                }
                                return@then
                            }
                            wd.getContent().showValidation(it.vv!!)
                            resolve(it.vv!!)
                        }
                    }
                }
            }
            cancelButton()
        }
        return Promise.resolve(dialog)
    }

    override fun getIcon(): String? {
        return null
    }

    override fun getDisplayName(): String {
        return DemoWebMessagesJS.CreateUser
    }

}