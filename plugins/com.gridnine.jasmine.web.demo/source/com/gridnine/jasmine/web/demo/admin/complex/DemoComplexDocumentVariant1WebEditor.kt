/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.web.core.ui.DefaultUIParameters
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.WebEditor
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.GridCellWidget
import com.gridnine.jasmine.web.core.ui.widgets.IntegerNumberBoxWidget
import com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant1EditorVMJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant1EditorVSJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant1EditorVVJS

class DemoComplexDocumentVariant1WebEditor(private val parent: WebComponent): WebEditor<DemoComplexDocumentVariant1EditorVMJS, DemoComplexDocumentVariant1EditorVSJS, DemoComplexDocumentVariant1EditorVVJS> {

    private val delegate:WebGridLayoutContainer

    val intValueWidget: IntegerNumberBoxWidget

    private lateinit var title:String

    private lateinit var uid:String

    init {
        delegate = UiLibraryAdapter.get().createGridLayoutContainer(this) {}
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val intValueCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant1Editor"]?.get("intValue")
                ?: "???") { par ->
            IntegerNumberBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(intValueCell))
        intValueWidget = intValueCell.widget
    }

    override fun readData(vm: DemoComplexDocumentVariant1EditorVMJS, vs: DemoComplexDocumentVariant1EditorVSJS) {
        title = vm.title
        uid = vm.uid
        intValueWidget.setValue(vm.intValue)
        vs.intValue?.let { intValueWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        intValueWidget.setReadonly(value)
    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun destroy() {
       delegate.destroy()
    }

    override fun getData(): DemoComplexDocumentVariant1EditorVMJS {
        val result = DemoComplexDocumentVariant1EditorVMJS()
        result.uid = uid
        result.title = title
        result.intValue = intValueWidget.getValue()!!
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

    override fun showValidation(validation: DemoComplexDocumentVariant1EditorVVJS) {
        validation.intValue?.let { intValueWidget.showValidation(it) }
    }

}