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
import com.gridnine.jasmine.web.server.utils.ServerUiUtils
import com.gridnine.jasmine.web.server.widgets.*
import java.time.LocalDate
import java.util.*

class DemoComplexDocumentServerUiEditor : ServerUiTileSpaceWidget<DemoComplexDocumentTileSpaceVM, DemoComplexDocumentTileSpaceVS, DemoComplexDocumentTileSpaceVV>() {

    lateinit var overviewEditor: DemoComplexDocumentOverviewServerUiEditor
    lateinit var simpleFieldsEditor: DemoComplexDocumentSimpleFieldsServerUiEditor
    lateinit var tableEditor: DemoComplexDocumentTableEditor
    lateinit var nestedDocumentsEditor:DemoComplexDocumentNestedDocumentsServerUiEditor

    override fun createInitializer(): ServerUiTileSpaceWidgetConfiguration<DemoComplexDocumentTileSpaceVM>.() -> Unit ={
        width = "100%"
        height = "100%"
        vmFactory = { DemoComplexDocumentTileSpaceVM() }
        overviewEditor = overview("Обзор", DemoComplexDocumentOverviewServerUiEditor())
        simpleFieldsEditor = tile("simpleFields", "Простые поля", DemoComplexDocumentSimpleFieldsServerUiEditor())
        tableEditor = tile("table", "Таблица", DemoComplexDocumentTableEditor())
        nestedDocumentsEditor = tile("nestedDocuments", "Вложенные объекты", DemoComplexDocumentNestedDocumentsServerUiEditor())
    }
}

class DemoComplexDocumentOverviewServerUiEditor : ServerUiViewEditor<DemoComplexDocumentOverviewEditorVM, DemoComplexDocumentOverviewEditorVS, DemoComplexDocumentOverviewEditorVV>, BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>() {

    val stringPropertyWidget: ServerUiTextBoxWidget

    init {
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration {
            width = "300px"
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        _node.addRow()
        stringPropertyWidget = ServerUiTextBoxWidget {
            width = "100%"
        }
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
            width = "300px"
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        stringPropertyWidget = ServerUiTextBoxWidget{
            width = "100%"
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Строка:", stringPropertyWidget)))
        floatPropertyWidget = ServerUiBigDecimalBoxWidget {
            width = "100%"
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Дробное число:", floatPropertyWidget)))
        integerPropertyWidget = ServerUiIntBoxWidget{
            width = "100%"
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Целое число:", integerPropertyWidget)))
        booleanPropertyWidget = ServerUiBooleanBoxWidget{
            width = "100%"
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Флаг:", booleanPropertyWidget)))
        entityRefPropertyWidget = ServerUiEntityValueWidget{
            width = "100%"
            handler = ServerUiAutocompleteHandler.createMetadataBasedAutocompleteHandler(DemoUserAccount::class.qualifiedName!!)
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Ссылка:", entityRefPropertyWidget)))
        enumPropertyWidget = ServerUiEnumValueWidget{
            width = "100%"
            enumClass = DemoEnum::class
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Перечисление:", enumPropertyWidget)))
        datePropertyWidget = ServerUiDateBoxWidget{
            width = "100%"
        }
        _node.addRow()
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Дата:", datePropertyWidget)))
        dateTimePropertyWidget = ServerUiDateTimeBoxWidget{
            width = "100%"
        }
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

class DemoComplexDocumentVariant1ServerUiEditor:ServerUiViewEditor<DemoComplexDocumentVariant1EditorVM, DemoComplexDocumentVariant1EditorVS, DemoComplexDocumentVariant1EditorVV>,BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>(){

    val intValueWidget:ServerUiIntBoxWidget
    lateinit var uidValue: String
    lateinit var titleValue: String

    init{
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration{
            width = "300px"
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        _node.addRow()
        intValueWidget= ServerUiIntBoxWidget{
            width = "100%"
            nullable = false
        }
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Целое число:", intValueWidget)))
    }

    override fun setData(data: DemoComplexDocumentVariant1EditorVM, settings: DemoComplexDocumentVariant1EditorVS?) {
        intValueWidget.setValue(data.intValue)
        settings?.intValue?.let { intValueWidget.configure(it)}
        uidValue = data.uid
        titleValue = data.title
    }

    override fun getData(): DemoComplexDocumentVariant1EditorVM {
        val result = DemoComplexDocumentVariant1EditorVM()
        result.intValue = intValueWidget.getValue()!!
        result.uid = uidValue
        result.title = titleValue
        return result
    }

    override fun showValidation(validation: DemoComplexDocumentVariant1EditorVV?) {
        intValueWidget.showValidation(validation?.intValue)
    }

    override fun setReadonly(value: Boolean) {
        intValueWidget.setReadonly(value)
    }

    override fun navigate(key: String) :Boolean{
        return false
    }

}

class DemoComplexDocumentVariant2ServerUiEditor:ServerUiViewEditor<DemoComplexDocumentVariant2EditorVM, DemoComplexDocumentVariant2EditorVS, DemoComplexDocumentVariant2EditorVV>,BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>(){

    val dateValueWidget:ServerUiDateBoxWidget
    lateinit var uidValue: String
    lateinit var titleValue: String

    init{
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration{
            width = "300px"
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        _node.addRow()
        dateValueWidget= ServerUiDateBoxWidget{
            width = "100%"
        }
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Дата:", dateValueWidget)))
    }

    override fun setData(data: DemoComplexDocumentVariant2EditorVM, settings: DemoComplexDocumentVariant2EditorVS?) {
        dateValueWidget.setValue(data.dateValue)
        settings?.dateValue?.let { dateValueWidget.configure(it)}
        uidValue = data.uid
        titleValue = data.title
    }

    override fun getData(): DemoComplexDocumentVariant2EditorVM {
        val result = DemoComplexDocumentVariant2EditorVM()
        result.dateValue = dateValueWidget.getValue()!!
        result.uid = uidValue
        result.title = titleValue
        return result
    }

    override fun showValidation(validation: DemoComplexDocumentVariant2EditorVV?) {
        dateValueWidget.showValidation(validation?.dateValue)
    }

    override fun setReadonly(value: Boolean) {
        dateValueWidget.setReadonly(value)
    }

    override fun navigate(key: String) :Boolean{
        return false
    }

}
class DemoComplexDocumentNestedDocumentServerUiEditorInterceptor : ServerUiEditorInterceptor<DemoComplexDocumentNestedDocumentsServerUiEditor>{
    override fun onInit(editor: DemoComplexDocumentNestedDocumentsServerUiEditor) {
        editor.setRemoveHandler {item->
            val data = item.getData() as BaseNavigatorVariantVM
            ServerUiUtils.confirm("Вы действительно хотите удалить<br> ${data.title}?"){
                editor.removeTab(data.uid)
            }
        }
        editor.setAddHandler {
            ServerUiUtils.choseVariant(NestedDocumentVariant::class){
                when(it){
                    NestedDocumentVariant.VARIANT1 ->{
                        val item = DemoComplexDocumentVariant1EditorVM()
                        item.intValue = 0
                        item.uid = UUID.randomUUID().toString()
                        item.title = "Новый вариант1"
                        editor.addTab(item, null)
                    }
                    NestedDocumentVariant.VARIANT2 ->{
                        val item = DemoComplexDocumentVariant2EditorVM()
                        item.dateValue = LocalDate.now()
                        item.uid = UUID.randomUUID().toString()
                        item.title = "Новый вариант2"
                        editor.addTab(item, null)
                    }
                }
            }
        }
    }
}
class DemoComplexDocumentNestedDocumentsServerUiEditor : ServerUiNavigatorWidget<DemoComplexDocumentNestedDocumentsEditorVM,DemoComplexDocumentNestedDocumentsEditorVS,DemoComplexDocumentNestedDocumentsEditorVV>(){

    override fun createInitializer(): ServerUiNavigatorWidgetConfiguration<DemoComplexDocumentNestedDocumentsEditorVM>.() -> Unit = {
        width = "100%"
        height = "100%"
        vmFactory = {DemoComplexDocumentNestedDocumentsEditorVM()}
        factories[DemoComplexDocumentVariant1EditorVM::class.qualifiedName!!] = {DemoComplexDocumentVariant1ServerUiEditor() }
        factories[DemoComplexDocumentVariant2EditorVM::class.qualifiedName!!] = {DemoComplexDocumentVariant2ServerUiEditor() }
        interceptors.add(DemoComplexDocumentNestedDocumentServerUiEditorInterceptor())
    }

}

class DemoComplexDocumentServerUiHandler : BaseServerUiObjectHandler(DemoComplexDocument::class) {
    override fun createEditor(): ServerUiViewEditor<*, *, *> {
        return DemoComplexDocumentServerUiEditor()
    }
}

