/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.ui

import com.gridnine.jasmine.common.core.model.BaseIdentityJS
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditor
import com.gridnine.jasmine.common.demo.model.ui.DemoUserNewAccountEditor
import com.gridnine.jasmine.common.demo.rest.DemoCreateUserRequestJS
import com.gridnine.jasmine.web.core.ui.WebUiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.components.Dialog
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.demo.ActionsIds
import com.gridnine.jasmine.web.demo.DemoRestClient
import com.gridnine.jasmine.web.standard.WebMessages
import com.gridnine.jasmine.web.standard.editor.ObjectEditorHandler
import com.gridnine.jasmine.web.standard.editor.OpenObjectData
import com.gridnine.jasmine.web.standard.editor.WebEditor
import com.gridnine.jasmine.web.standard.list.ListLinkButtonHandler
import com.gridnine.jasmine.web.standard.mainframe.MainFrame
import com.gridnine.jasmine.web.standard.utils.StandardUiUtils

class WebDemoUserAccountEditorHandler:ObjectEditorHandler{
    override fun createEditor(): WebEditor<*, *, *> {
        return DemoUserAccountEditor()
    }

    override fun getActionsGroupId(): String {
        return ActionsIds.com_gridnine_jasmine_common_demo_model_domain_DemoUserAccount
    }

    override fun getId(): String {
        return DemoUserAccountIndexJS.objectId
    }
}

class WebDemoCreateNewUserAccountListButtonHandler: ListLinkButtonHandler<BaseIdentityJS> {
    override suspend fun invoke(selected: List<BaseIdentityJS>) {
        invokeAndReturnResult()
    }

    fun invokeAndReturnResult():Dialog<DemoUserNewAccountEditor> {
        val editor = DemoUserNewAccountEditor()
        return WebUiLibraryAdapter.get().showDialog(editor){
            title = "Создание нового пользователя"
            button {
                displayName = WebMessages.apply
                handler = {
                    val vm = it.getContent().getData()
                    val response = DemoRestClient.demo_demo_createUser(DemoCreateUserRequestJS().also {
                        it.vm = vm
                    })
                    if(StandardUiUtils.hasValidationErrors(response.vv)){
                        it.getContent().showValidation(response.vv)
                        response.vv
                    } else{
                        it.close()
                        MainFrame.get().openTab(OpenObjectData(DemoUserAccountIndexJS.objectId, response.objectUid!!, null))
                    }
                }
            }
            cancelButton()
        }
    }

}

