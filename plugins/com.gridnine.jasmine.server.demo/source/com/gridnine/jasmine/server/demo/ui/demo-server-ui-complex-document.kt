/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.server.core.model.ui.*
import com.gridnine.jasmine.server.demo.model.domain.DemoComplexDocument
import com.gridnine.jasmine.server.demo.model.domain.DemoEnum
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.web.demo.*
import com.gridnine.jasmine.web.server.components.*
import com.gridnine.jasmine.web.server.mainframe.BaseServerUiObjectHandler
import com.gridnine.jasmine.web.server.widgets.*

class DemoComplexDocumentServerUiEditor : ServerUiViewEditor<DemoComplexDocumentTileSpaceVM, DemoComplexDocumentTileSpaceVS, DemoComplexDocumentTileSpaceVV>, BaseServerUiNodeWrapper<ServerUiTileSpaceWidget<DemoComplexDocumentTileSpaceVM, DemoComplexDocumentTileSpaceVS, DemoComplexDocumentTileSpaceVV>>() {

    val overviewEditor: DemoComplexDocumentOverviewServerUiEditor
    val simpleFieldsEditor: DemoComplexDocumentSimpleFieldsServerUiEditor
    val tableEditor: DemoComplexDocumentTableEditor
    lateinit var nestedDocument:DemoComplexDocumentNestedDocumentsEditorVM
    init {
        val config = ServerUiTileSpaceWidgetConfiguration<DemoComplexDocumentTileSpaceVM> {
            width = "100%"
            height = "100%"
            vmFactory = { DemoComplexDocumentTileSpaceVM() }
        }
        overviewEditor = config.overview("Обзор", DemoComplexDocumentOverviewServerUiEditor())
        simpleFieldsEditor = config.tile("simpleFields", "Простые поля", DemoComplexDocumentSimpleFieldsServerUiEditor())
        tableEditor = config.tile("table", "Таблица", DemoComplexDocumentTableEditor())
        _node = ServerUiTileSpaceWidget(config)
    }

    override fun setData(data: DemoComplexDocumentTileSpaceVM, settings: DemoComplexDocumentTileSpaceVS?) {
        _node.setData(data, settings)
        nestedDocument = data.nestedDocuments
    }

    override fun getData(): DemoComplexDocumentTileSpaceVM {
        val result = _node.getData()
        result.nestedDocuments = nestedDocument
        return result
    }

    override fun showValidation(validation: DemoComplexDocumentTileSpaceVV?) {
        _node.showValidation(validation)
    }

    override fun setReadonly(value: Boolean) {
        _node.setReadonly(value)
    }

    override fun navigate(key: String): Boolean {
        return _node.navigate(key)
    }


}

class DemoComplexDocumentOverviewServerUiEditor : ServerUiViewEditor<DemoComplexDocumentOverviewEditorVM, DemoComplexDocumentOverviewEditorVS, DemoComplexDocumentOverviewEditorVV>, BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>() {

    val stringPropertyWidget: ServerUiTextBoxWidget

    init {
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration {
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        _node.addRow()
        stringPropertyWidget = ServerUiTextBoxWidget(ServerUiTextBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Строка:", stringPropertyWidget)))
    }

    override fun setData(data: DemoComplexDocumentOverviewEditorVM, settings: DemoComplexDocumentOverviewEditorVS?) {
        stringPropertyWidget.setValue(data.stringProperty)
        stringPropertyWidget.configure(settings?.stringProperty)
    }

    override fun getData(): DemoComplexDocumentOverviewEditorVM {
        val result = DemoComplexDocumentOverviewEditorVM()
        result.stringProperty = stringPropertyWidget.getValue()
        return result
    }

    override fun showValidation(validation: DemoComplexDocumentOverviewEditorVV?) {
        stringPropertyWidget.showValidation(validation?.stringProperty)
    }

    override fun setReadonly(value: Boolean) {
        stringPropertyWidget.setReadonly(value)
    }

    override fun navigate(key: String): Boolean {
        return false
    }

}


class DemoComplexDocumentSimpleFieldsServerUiEditor : ServerUiViewEditor<DemoComplexDocumentSimpleFieldsEditorVM, DemoComplexDocumentSimpleFieldsEditorVS, DemoComplexDocumentSimpleFieldsEditorVV>, BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>() {

    val stringPropertyWidget: ServerUiTextBoxWidget

    var floatPropertyWidget: ServerUiBigDecimalBoxWidget

    var integerPropertyWidget: ServerUiIntBoxWidget

    var booleanPropertyWidget: ServerUiBooleanBoxWidget

    var entityRefPropertyWidget: ServerUiEntityValueWidget<DemoUserAccount>

    var enumPropertyWidget: ServerUiEnumValueWidget<DemoEnum>

    var datePropertyWidget: ServerUiDateBoxWidget

    var dateTimePropertyWidget: ServerUiDateTimeBoxWidget

    init {
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration {
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        stringPropertyWidget = ServerUiTextBoxWidget(ServerUiTextBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Строка:", stringPropertyWidget)))
        floatPropertyWidget = ServerUiBigDecimalBoxWidget(ServerUiBigDecimalBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Дробное число:", floatPropertyWidget)))
        integerPropertyWidget = ServerUiIntBoxWidget(ServerUiBigIntBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Целое число:", integerPropertyWidget)))
        booleanPropertyWidget = ServerUiBooleanBoxWidget(ServerUiBooleanBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Флаг:", booleanPropertyWidget)))
        entityRefPropertyWidget = ServerUiEntityValueWidget(ServerUiEntityValueWidgetConfiguration {
            width = "100%"
            handler = ServerUiAutocompleteHandler.createMetadataBasedAutocompleteHandler(DemoUserAccount::class.qualifiedName!!)
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Ссылка:", entityRefPropertyWidget)))
        enumPropertyWidget = ServerUiEnumValueWidget(ServerUiEnumValueWidgetConfiguration {
            width = "100%"
            enumClass = DemoEnum::class
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Перечисление:", enumPropertyWidget)))
        datePropertyWidget = ServerUiDateBoxWidget(ServerUiDateBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Дата:", datePropertyWidget)))
        dateTimePropertyWidget = ServerUiDateTimeBoxWidget(ServerUiDateTimeBoxWidgetConfiguration {
            width = "100%"
        })
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Дата и время:", dateTimePropertyWidget)))
    }


    override fun setReadonly(value: Boolean) {
        stringPropertyWidget.setReadonly(value)
        floatPropertyWidget.setReadonly(value)
        integerPropertyWidget.setReadonly(value)
        booleanPropertyWidget.setReadonly(value)
        entityRefPropertyWidget.setReadonly(value)
        enumPropertyWidget.setReadonly(value)
        datePropertyWidget.setReadonly(value)
        dateTimePropertyWidget.setReadonly(value)
    }

    override fun navigate(key: String): Boolean {
        return false
    }

    override fun setData(data: DemoComplexDocumentSimpleFieldsEditorVM, settings: DemoComplexDocumentSimpleFieldsEditorVS?) {
        stringPropertyWidget.setValue(data.stringProperty)
        stringPropertyWidget.configure(settings?.stringProperty)
        floatPropertyWidget.setValue(data.floatProperty)
        floatPropertyWidget.configure(settings?.floatProperty)
        integerPropertyWidget.setValue(data.integerProperty)
        integerPropertyWidget.configure(settings?.integerProperty)
        booleanPropertyWidget.setValue(data.booleanProperty)
        booleanPropertyWidget.configure(settings?.booleanProperty)
        entityRefPropertyWidget.setValue(data.entityRefProperty)
        entityRefPropertyWidget.configure(settings?.entityRefProperty)
        enumPropertyWidget.setValue(data.enumProperty)
        enumPropertyWidget.configure(settings?.enumProperty)
        datePropertyWidget.setValue(data.dateProperty)
        datePropertyWidget.configure(settings?.dateProperty)
        dateTimePropertyWidget.setValue(data.dateTimeProperty)
        dateTimePropertyWidget.configure(settings?.dateTimeProperty)
    }

    override fun getData(): DemoComplexDocumentSimpleFieldsEditorVM {
        val result = DemoComplexDocumentSimpleFieldsEditorVM()
        result.stringProperty = stringPropertyWidget.getValue()
        result.floatProperty = floatPropertyWidget.getValue()
        result.integerProperty = integerPropertyWidget.getValue()
        result.booleanProperty = booleanPropertyWidget.getValue()
        result.entityRefProperty = entityRefPropertyWidget.getValue()
        result.enumProperty = enumPropertyWidget.getValue()
        result.dateProperty = datePropertyWidget.getValue()
        result.dateTimeProperty = dateTimePropertyWidget.getValue()
        return result
    }

    override fun showValidation(validation: DemoComplexDocumentSimpleFieldsEditorVV?) {
        stringPropertyWidget.showValidation(validation?.stringProperty)
        floatPropertyWidget.showValidation(validation?.floatProperty)
        integerPropertyWidget.showValidation(validation?.integerProperty)
        booleanPropertyWidget.showValidation(validation?.booleanProperty)
        entityRefPropertyWidget.showValidation(validation?.entityRefProperty)
        enumPropertyWidget.showValidation(validation?.enumProperty)
        datePropertyWidget.showValidation(validation?.dateProperty)
        dateTimePropertyWidget.showValidation(validation?.dateTimeProperty)
    }
}

class DemoComplexDocumentTableEditor:ServerUiViewEditor<DemoComplexDocumentTableEditorVM,DemoComplexDocumentTableEditorVS,DemoComplexDocumentTableEditorVV>,BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>(){


    val tableWidget: ServerUiTableWidget<DemoComplexDocumentTableVM,DemoComplexDocumentTableVS,DemoComplexDocumentTableVV>

    init{
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration {
            width = "100%"
        })
        _node.addRow()
        tableWidget = ServerUiTableWidget(ServerUiTableWidgetConfiguration{
            width = "100%"
            showToolsColumn = true
            vmFactory = {DemoComplexDocumentTableVM()}
            column("enumColumn", EnumSelectBoxWidgetDescription(false, DemoEnum::class.qualifiedName!!), "Перечисление", 100)
            column("entityRefColumn", EntitySelectBoxWidgetDescription(false, DemoUserAccount::class.qualifiedName!!), "Ссылка", 200)
            column("integerColumn", IntegerNumberBoxWidgetDescription(false,false), "Целое число", 50)
            column("floatColumn", FloatNumberBoxWidgetDescription(false), "Дробное число", 100)
            column("textColumn", TextBoxWidgetDescription(false), "Строка", 100)
        }
        )
        tableWidget.vsFactory = {null}
        _node.addCell(ServerUiGridLayoutCell(tableWidget,1))
    }

    override fun setData(vm: DemoComplexDocumentTableEditorVM, vs: DemoComplexDocumentTableEditorVS?){
        tableWidget.setData(vm.table, vs?.table)
    }

    override fun setReadonly(value: Boolean){
        tableWidget.setReadonly(value)
    }

    override fun getData(): DemoComplexDocumentTableEditorVM{
        val result = DemoComplexDocumentTableEditorVM()
        result.table.addAll(tableWidget.getData())
        return result
    }

    override fun showValidation(validation: DemoComplexDocumentTableEditorVV?) {
        validation?.table?.let{tableWidget.showValidation(it)}
    }

    override fun navigate(key: String): Boolean {
        return false
    }

}

class DemoComplexDocumentServerUiHandler : BaseServerUiObjectHandler(DemoComplexDocument::class) {
    override fun createEditor(): ServerUiViewEditor<*, *, *> {
        return DemoComplexDocumentServerUiEditor()
    }
}

