/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.core.model.common.BaseIntrospectableObjectJS
import com.gridnine.jasmine.server.core.model.domain.ObjectReferenceJS
import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.web.core.mainframe.ListWorkspaceItemHandler
import com.gridnine.jasmine.web.core.ui.DefaultUIParameters
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.WebEditor
import com.gridnine.jasmine.web.core.ui.components.WebDataGridResponse
import com.gridnine.jasmine.web.core.ui.components.WebDataHorizontalAlignment
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.GridCellWidget
import com.gridnine.jasmine.web.core.ui.widgets.TextBoxWidget
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVMJS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVSJS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVVJS
import kotlin.js.Promise

class DemoUserAccountWebEditor(private val parent: WebComponent, private val delegate: WebGridLayoutContainer = UiLibraryAdapter.get().createGridLayoutContainer(parent) {
//    height = "100%"
})
    : WebComponent by delegate, WebEditor<DemoUserAccountEditorVMJS, DemoUserAccountEditorVSJS, DemoUserAccountEditorVVJS> {


    val loginWidget: TextBoxWidget

    val nameWidget: TextBoxWidget

    init {
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val loginCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserAccountEditor"]?.get("login")
                ?: "???") { par ->
            TextBoxWidget(par, {
                width = "100%"
            })
        }
        delegate.addCell(WebGridLayoutCell(loginCell))
        loginWidget = loginCell.widget
        delegate.addRow()
        val nameCell = GridCellWidget(delegate, L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserAccountEditor"]?.get("name")
                ?: "???") { par ->
            TextBoxWidget(par, {
                width = "100%"
            })
        }
        delegate.addCell(WebGridLayoutCell(nameCell))
        nameWidget = nameCell.widget
    }

    override fun readData(vm: DemoUserAccountEditorVMJS, vs: DemoUserAccountEditorVSJS) {
        loginWidget.setValue(vm.login)
        nameWidget.setValue(vm.name)
    }

    override fun setReadonly(value: Boolean) {
        loginWidget.setReadonly(value)
        nameWidget.setReadonly(value)
    }

    override fun destroy() {
        //noops
    }

}