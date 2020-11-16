/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.web.core.ui.*
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.GridCellWidget
import com.gridnine.jasmine.web.core.ui.widgets.PasswordBoxWidget
import com.gridnine.jasmine.web.core.ui.widgets.TextBoxWidget
import com.gridnine.jasmine.web.demo.*

class DemoUserAccountCreateUserWebEditor(aParent: WebComponent?): WebEditor<DemoUserNewAccountEditorVMJS, DemoUserNewAccountEditorVSJS, DemoUserNewAccountEditorVVJS>, HasDivId {

    private val parent:WebComponent?
    private val delegate:WebGridLayoutContainer

    val loginWidget: TextBoxWidget

    val passwordWidget: PasswordBoxWidget

    val retypePasswordWidget: PasswordBoxWidget

    init {
        parent = aParent
        delegate = UiLibraryAdapter.get().createGridLayoutContainer(this) {}
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val loginCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserNewAccountEditor"]?.get("login")
                ?: "???") { par ->
            TextBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(loginCell))
        loginWidget = loginCell.widget
        delegate.addRow()
        val passwordCell = GridCellWidget(delegate, L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserNewAccountEditor"]?.get("password")
                ?: "???") { par ->
            PasswordBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(passwordCell))
        passwordWidget = passwordCell.widget

        delegate.addRow()
        val retypePasswordCell = GridCellWidget(delegate, L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoUserNewAccountEditor"]?.get("retypePassword")
                ?: "???") { par ->
            PasswordBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(retypePasswordCell))
        retypePasswordWidget = retypePasswordCell.widget
    }

    override fun readData(vm: DemoUserNewAccountEditorVMJS, vs: DemoUserNewAccountEditorVSJS) {
        loginWidget.setValue(vm.login)
        vs.login?.let { loginWidget.configure(it) }
        passwordWidget.setValue(vm.password)
        vs.password?.let { passwordWidget.configure(it) }
        retypePasswordWidget.setValue(vm.retypePassword)
        vs.retypePassword?.let { retypePasswordWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        loginWidget.setReadonly(value)
        passwordWidget.setReadonly(value)
        retypePasswordWidget.setReadonly(value)
    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun destroy() {
       delegate.destroy()
    }

    override fun getData(): DemoUserNewAccountEditorVMJS {
        val result = DemoUserNewAccountEditorVMJS()
        result.login = loginWidget.getValue()
        result.password  = passwordWidget.getValue()
        result.retypePassword  = retypePasswordWidget.getValue()
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

    override fun showValidation(validation: DemoUserNewAccountEditorVVJS) {
        validation.login?.let { loginWidget.showValidation(it) }
        validation.password?.let { passwordWidget.showValidation(it) }
        validation.retypePassword?.let { retypePasswordWidget.showValidation(it) }
    }

    override fun getId(): String {
        return delegate.getId()
    }

}