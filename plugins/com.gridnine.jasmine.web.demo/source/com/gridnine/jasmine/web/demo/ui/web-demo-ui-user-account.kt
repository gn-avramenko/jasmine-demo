/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.ui

import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditor
import com.gridnine.jasmine.web.demo.ActionsIds
import com.gridnine.jasmine.web.standard.editor.ObjectEditorHandler
import com.gridnine.jasmine.web.standard.editor.WebEditor

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

