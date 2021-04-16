/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.common.core.model.BaseNavigatorVariantVM
import com.gridnine.jasmine.common.demo.model.domain.DemoComplexDocument
import com.gridnine.jasmine.common.demo.model.ui.*
import com.gridnine.jasmine.common.standard.model.l10n.StandardL10nMessagesFactory
import com.gridnine.jasmine.server.core.ui.common.ViewEditor
import com.gridnine.jasmine.server.core.ui.common.ViewEditorInterceptor
import com.gridnine.jasmine.server.core.ui.utils.UiUtils
import com.gridnine.jasmine.server.standard.ui.mainframe.BaseUiObjectHandler
import java.time.LocalDate
import java.util.*
import kotlin.reflect.KClass


class DemoComplexDocumentNestedDocumentTileSpaceEditorInterceptor : ViewEditorInterceptor<DemoComplexDocumentNestedDocumentsEditor> {
    override fun onInit(editor: DemoComplexDocumentNestedDocumentsEditor) {
        editor.setRemoveHandler { item ->
            val data = item.getData() as BaseNavigatorVariantVM
            UiUtils.confirm(StandardL10nMessagesFactory.areYouSureToDelete(data.title)) {
                editor.removeTab(data.uid)
            }
        }
        editor.setAddHandler {
            UiUtils.choseVariant(NestedDocumentVariant::class) {
                when (it) {
                    NestedDocumentVariant.VARIANT1 -> {
                        val item = DemoComplexDocumentVariant1EditorVM()
                        item.intValue = 0
                        item.uid = UUID.randomUUID().toString()
                        item.title = "Новый вариант1"
                        editor.addTab(item, null)
                    }
                    NestedDocumentVariant.VARIANT2 -> {
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

    override fun getEditorClass(): KClass<DemoComplexDocumentNestedDocumentsEditor> {
        return DemoComplexDocumentNestedDocumentsEditor::class
    }

}
class DemoComplexDocumentUiHandler : BaseUiObjectHandler(DemoComplexDocument::class) {
    override fun createEditor(): ViewEditor<*, *, *> {
        return DemoComplexDocumentTileSpace()
    }
}

