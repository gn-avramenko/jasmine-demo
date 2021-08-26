/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.ui

import com.gridnine.jasmine.common.core.model.BaseNavigatorVariantVMJS
import com.gridnine.jasmine.common.demo.model.domain.DemoComplexDocumentIndexJS
import com.gridnine.jasmine.common.demo.model.ui.*
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.demo.ActionsIds
import com.gridnine.jasmine.web.standard.WebMessages
import com.gridnine.jasmine.web.standard.editor.ObjectEditorHandler
import com.gridnine.jasmine.web.standard.editor.WebEditor
import com.gridnine.jasmine.web.standard.editor.WebEditorInterceptor
import com.gridnine.jasmine.web.standard.utils.StandardUiUtils
import kotlin.js.Date

import kotlin.reflect.KClass

class WebDemoComplexDocumentEditorHandler:ObjectEditorHandler{
    override fun createEditor(): WebEditor<*, *, *> {
        return DemoComplexDocumentTileSpace()
    }

    override fun getActionsGroupId(): String {
        return ActionsIds.com_gridnine_jasmine_common_demo_model_domain_DemoComplexDocument
    }

    override fun getId(): String {
        return DemoComplexDocumentIndexJS.objectId
    }
}

class DemoComplexDocumentNestedDocumentTileSpaceEditorInterceptor : WebEditorInterceptor<DemoComplexDocumentNestedDocumentsEditor> {
    override fun onInit(editor: DemoComplexDocumentNestedDocumentsEditor) {
        editor.setRemoveHandler { item ->
            val data = item.getData() as BaseNavigatorVariantVMJS
            StandardUiUtils.confirm("Вы действительно хотите удалить ${data.title}?") {
                editor.removeTab(data.uid)
            }
        }
        editor.setAddHandler {
            StandardUiUtils.choseVariant(NestedDocumentVariantJS::class) {
                when (it) {
                    NestedDocumentVariantJS.VARIANT1 -> {
                        val item = DemoComplexDocumentVariant1EditorVMJS()
                        item.intValue = 0
                        item.uid = MiscUtilsJS.createUUID()
                        item.title = "Новый вариант1"
                        editor.addTab(item, null)
                    }
                    NestedDocumentVariantJS.VARIANT2 -> {
                        val item = DemoComplexDocumentVariant2EditorVMJS()
                        item.dateValue = Date()
                        item.uid = MiscUtilsJS.createUUID()
                        item.title = "Новый вариант2"
                        editor.addTab(item, null)
                    }
                }
            }
        }
    }

    override fun getEditorClass(): KClass<DemoComplexDocumentNestedDocumentsEditor> {
        return DemoComplexDocumentNestedDocumentsEditor::class
    }

}
//class DemoComplexDocumentTileSpace:TileSpaceWidget<DemoComplexDocumentTileSpaceVMJS,DemoComplexDocumentTileSpaceVSJS, DemoComplexDocumentTileSpaceVVJS >(){
//    lateinit var overviewEditor: com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentOverviewEditor
//    lateinit var simpleFieldsEditor: com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentSimpleFieldsEditor
//    lateinit var nestedDocumentsEditor: DemoComplexDocumentNestedDocumentsEditor
//    lateinit var tableEditor: com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentTableEditor
//    override fun createInitializer(): TileSpaceWidgetConfiguration<DemoComplexDocumentTileSpaceVMJS>.() -> Unit{
//        return{
//            width = "100%"
//            height = "100%"
//            vmFactory = { DemoComplexDocumentTileSpaceVMJS() }
//            overviewEditor = overview(L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentTileSpace"]?.get("overview")?:"overview", com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentOverviewEditor())
//            simpleFieldsEditor = tile("simpleFields", L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentTileSpace"]?.get("simpleFields")?:"simpleFields", com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentSimpleFieldsEditor())
//            nestedDocumentsEditor = tile("nestedDocuments", L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentTileSpace"]?.get("nestedDocuments")?:"nestedDocuments", DemoComplexDocumentNestedDocumentsEditor())
//            tableEditor = tile("table", L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentTileSpace"]?.get("table")?:"table", com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentTableEditor())
//        }
//    }
//}
//
//class DemoComplexDocumentNestedDocumentsEditor:NavigatorWidget<DemoComplexDocumentNestedDocumentsEditorVMJS,DemoComplexDocumentNestedDocumentsEditorVSJS,DemoComplexDocumentNestedDocumentsEditorVVJS>(){
//    override fun createInitializer(): NavigatorWidgetConfiguration<DemoComplexDocumentNestedDocumentsEditorVMJS>.() -> Unit{
//        return{
//            width = "100%"
//            height = "100%"
//            vmFactory = { DemoComplexDocumentNestedDocumentsEditorVMJS() }
//            factories[DemoComplexDocumentVariant1EditorVMJS::class] = {com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentVariant1Editor() }
//            factories[DemoComplexDocumentVariant2EditorVMJS::class] = {com.gridnine.jasmine.common.demo.model.ui.DemoComplexDocumentVariant2Editor() }
//        }
//    }
//}