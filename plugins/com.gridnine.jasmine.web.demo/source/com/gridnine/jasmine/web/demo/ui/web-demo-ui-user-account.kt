/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.ui

import com.gridnine.jasmine.common.core.meta.L10nMetaRegistryJS
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditorVMJS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditorVSJS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditorVVJS
import com.gridnine.jasmine.web.core.ui.WebUiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.components.BaseWebNodeWrapper
import com.gridnine.jasmine.web.core.ui.components.DefaultUIParameters
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.demo.ActionsIds
import com.gridnine.jasmine.web.standard.editor.ObjectEditorHandler
import com.gridnine.jasmine.web.standard.editor.WebEditor
import com.gridnine.jasmine.web.standard.widgets.TextBoxWidget
import com.gridnine.jasmine.web.standard.widgets.WebGridCellWidget

class WebDemoUserAccountEditorHandler:ObjectEditorHandler{
    override fun createEditor(): WebEditor<*, *, *> {
        return DemoUserAccountWebEditor()
    }

    override fun getActionsGroupId(): String {
        return ActionsIds.com_gridnine_jasmine_common_demo_model_domain_DemoUserAccount
    }

    override fun getId(): String {
        return DemoUserAccountIndexJS.objectId
    }

}

class DemoUserAccountWebEditor:WebEditor<DemoUserAccountEditorVMJS, DemoUserAccountEditorVSJS, DemoUserAccountEditorVVJS>, BaseWebNodeWrapper<WebGridLayoutContainer>(){
    val loginWidget:TextBoxWidget

    val nameWidget:TextBoxWidget

    init {
        loginWidget = TextBoxWidget {
            width = "100%"
        }
        nameWidget = TextBoxWidget {
            width = "100%"
        }
        _node = WebUiLibraryAdapter.get().createGridContainer {
            width = DefaultUIParameters.controlWidthAsString
            column("100%")
            row{
                cell(WebGridCellWidget(L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditor"]?.get("login")?:"login",loginWidget))
            }
            row{
                cell(WebGridCellWidget(L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditor"]?.get("name")?:"name", nameWidget))
            }
        }

    }
    override fun getData(): DemoUserAccountEditorVMJS {
        return DemoUserAccountEditorVMJS().apply {
            login = loginWidget.getValue()
            name = nameWidget.getValue()
        }
    }

    override fun readData(vm: DemoUserAccountEditorVMJS, vs: DemoUserAccountEditorVSJS?) {
        loginWidget.setValue(vm.login)
        vs?.login?.let { loginWidget.configure(it) }
        nameWidget.setValue(vm.name)
        vs?.name?.let { nameWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        loginWidget.setReadonly(value)
        nameWidget.setReadonly(value)
    }

    override fun showValidation(vv: DemoUserAccountEditorVVJS?) {
        loginWidget.showValidation(vv?.login)
        nameWidget.showValidation(vv?.name)
    }

}