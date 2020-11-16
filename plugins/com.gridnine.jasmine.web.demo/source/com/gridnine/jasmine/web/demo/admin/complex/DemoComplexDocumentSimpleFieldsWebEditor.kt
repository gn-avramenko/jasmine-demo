/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.server.demo.model.domain.DemoEnumJS
import com.gridnine.jasmine.web.core.ui.DefaultUIParameters
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.WebEditor
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.*
import com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditorVMJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditorVSJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditorVVJS

class DemoComplexDocumentSimpleFieldsWebEditor(private val parent:WebComponent): WebEditor<DemoComplexDocumentSimpleFieldsEditorVMJS, DemoComplexDocumentSimpleFieldsEditorVSJS, DemoComplexDocumentSimpleFieldsEditorVVJS> {

    private val delegate:WebGridLayoutContainer = UiLibraryAdapter.get().createGridLayoutContainer(this) {}

    val stringPropertyWidget: TextBoxWidget
    val floatPropertyWidget: FloatNumberBoxWidget
    val integerPropertyWidget: IntegerNumberBoxWidget
    val booleanPropertyWidget: BooleanBoxWidget
    val enumPropertyWidget:EnumValueWidget<DemoEnumJS>
    val datePropertyWidget:DateBoxWidget
    val dateTimePropertyWidget:DateTimeBoxWidget

    init {
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val stringPropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("stringProperty")
                ?: "???") { par ->
            TextBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(stringPropertyWidgetCell))
        stringPropertyWidget = stringPropertyWidgetCell.widget
        delegate.addRow()
        val floatPropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("floatProperty")
                ?: "???") { par ->
            FloatNumberBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(floatPropertyWidgetCell))
        floatPropertyWidget = floatPropertyWidgetCell.widget
        delegate.addRow()
        val integerPropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("integerProperty")
                ?: "???") { par ->
            IntegerNumberBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(integerPropertyWidgetCell))
        integerPropertyWidget = integerPropertyWidgetCell.widget
        delegate.addRow()
        val booleanPropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("booleanProperty")
                ?: "???") { par ->
            BooleanBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(booleanPropertyWidgetCell))
        booleanPropertyWidget = booleanPropertyWidgetCell.widget
        delegate.addRow()
        val enumPropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("enumProperty")
                ?: "???") { par ->
            EnumValueWidget<DemoEnumJS>(par) {
                width = "100%"
                enumClass = DemoEnumJS::class
            }
        }
        delegate.addCell(WebGridLayoutCell(enumPropertyWidgetCell))
        enumPropertyWidget = enumPropertyWidgetCell.widget
        delegate.addRow()
        val datePropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("dateProperty")
                ?: "???") { par ->
            DateBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(datePropertyWidgetCell))
        datePropertyWidget = datePropertyWidgetCell.widget
        delegate.addRow()
        val dateTimePropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentSimpleFieldsEditor"]?.get("dateTimeProperty")
                ?: "???") { par ->
            DateTimeBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(dateTimePropertyWidgetCell))
        dateTimePropertyWidget = dateTimePropertyWidgetCell.widget
    }

    override fun readData(vm: DemoComplexDocumentSimpleFieldsEditorVMJS, vs: DemoComplexDocumentSimpleFieldsEditorVSJS) {
        stringPropertyWidget.setValue(vm.stringProperty)
        vs.stringProperty?.let { stringPropertyWidget.configure(it) }
        datePropertyWidget.setValue(vm.dateProperty)
        vs.dateProperty?.let { datePropertyWidget.configure(it) }
        floatPropertyWidget.setValue(vm.floatProperty)
        vs.floatProperty?.let { floatPropertyWidget.configure(it) }
        integerPropertyWidget.setValue(vm.integerProperty)
        vs.integerProperty?.let { integerPropertyWidget.configure(it) }
        booleanPropertyWidget.setValue(vm.booleanProperty)
        vs.booleanProperty?.let { booleanPropertyWidget.configure(it) }
        enumPropertyWidget.setValue(vm.enumProperty)
        vs.enumProperty?.let { enumPropertyWidget.configure(it) }
        dateTimePropertyWidget.setValue(vm.dateTimeProperty)
        vs.dateTimeProperty?.let { dateTimePropertyWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        stringPropertyWidget.setReadonly(value)
        floatPropertyWidget.setReadonly(value)
        integerPropertyWidget.setReadonly(value)
        booleanPropertyWidget.setReadonly(value)
        enumPropertyWidget.setReadonly(value)
        datePropertyWidget.setReadonly(value)
        dateTimePropertyWidget.setReadonly(value)
    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun destroy() {
       delegate.destroy()
    }

    override fun getData(): DemoComplexDocumentSimpleFieldsEditorVMJS {
        val result = DemoComplexDocumentSimpleFieldsEditorVMJS()
        result.stringProperty = stringPropertyWidget.getValue()
        result.floatProperty = floatPropertyWidget.getValue()
        result.integerProperty = integerPropertyWidget.getValue()
        result.booleanProperty= booleanPropertyWidget.getValue()
        result.enumProperty = enumPropertyWidget.getValue()
        result.dateProperty = datePropertyWidget.getValue()
        result.dateTimeProperty = dateTimePropertyWidget.getValue()
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

    override fun showValidation(validation: DemoComplexDocumentSimpleFieldsEditorVVJS) {
        validation.stringProperty?.let { stringPropertyWidget.showValidation(it) }
        validation.floatProperty?.let { floatPropertyWidget.showValidation(it) }
        validation.integerProperty?.let { integerPropertyWidget.showValidation(it) }
        validation.enumProperty?.let { enumPropertyWidget.showValidation(it) }
        validation.dateProperty?.let { datePropertyWidget.showValidation(it) }
        validation.dateTimeProperty?.let { dateTimePropertyWidget.showValidation(it) }
    }

}