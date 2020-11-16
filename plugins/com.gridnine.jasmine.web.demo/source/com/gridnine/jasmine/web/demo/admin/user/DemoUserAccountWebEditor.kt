/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.web.core.ui.DefaultUIParameters
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.WebEditor
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.GridCellWidget
import com.gridnine.jasmine.web.core.ui.widgets.TextBoxWidget
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVMJS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVSJS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVVJS

class DemoUserAccountWebEditor(aParent: WebComponent): WebEditor<DemoUserAccountEditorVMJS, DemoUserAccountEditorVSJS, DemoUserAccountEditorVVJS> {

    private val parent:WebComponent
    private val delegate:WebGridLayoutContainer

    val loginWidget: TextBoxWidget

    val nameWidget: TextBoxWidget

    init {
        parent = aParent
        delegate = UiLibraryAdapter.get().createGridLayoutContainer(this) {}
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val loginCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserAccountEditor"]?.get("login")
                ?: "???") { par ->
            TextBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(loginCell))
        loginWidget = loginCell.widget
        delegate.addRow()
        val nameCell = GridCellWidget(delegate, L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserAccountEditor"]?.get("name")
                ?: "???") { par ->
            TextBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(nameCell))
        nameWidget = nameCell.widget
    }

    override fun readData(vm: DemoUserAccountEditorVMJS, vs: DemoUserAccountEditorVSJS) {
        loginWidget.setValue(vm.login)
        vs.login?.let { loginWidget.configure(it) }
        nameWidget.setValue(vm.name)
        vs.name?.let { nameWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        loginWidget.setReadonly(value)
        nameWidget.setReadonly(value)
    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun destroy() {
       delegate.destroy()
    }

    override fun getData(): DemoUserAccountEditorVMJS {
        val result = DemoUserAccountEditorVMJS()
        result.login = loginWidget.getValue()
        result.name  = nameWidget.getValue()
        return result
    }

    override fun getChildren(): List<WebComponent> {
        return arrayListOf(delegate)
    }

    override fun getHtml(): String {
        return delegate.getHtml()
    }

    override fun decorate() {
        delegate.decorate()
    }

    override fun showValidation(validation: DemoUserAccountEditorVVJS) {
        validation.login?.let { loginWidget.showValidation(it) }
        validation.name?.let { nameWidget.showValidation(it) }
    }

}