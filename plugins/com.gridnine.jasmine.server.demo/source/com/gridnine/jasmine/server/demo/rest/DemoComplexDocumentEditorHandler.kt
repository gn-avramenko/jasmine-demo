/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.server.core.utils.TextUtils
import com.gridnine.jasmine.server.demo.model.domain.DemoComplexDocument
import com.gridnine.jasmine.server.demo.model.domain.DemoNavigatorVariant1
import com.gridnine.jasmine.server.demo.model.domain.DemoNavigatorVariant2
import com.gridnine.jasmine.server.standard.StandardServerMessagesFactory
import com.gridnine.jasmine.server.standard.rest.ObjectEditorHandler
import com.gridnine.jasmine.web.demo.*
import kotlin.reflect.KClass

class DemoComplexDocumentEditorHandler :ObjectEditorHandler<DemoComplexDocument, DemoComplexDocumentTileSpaceVM, DemoComplexDocumentTileSpaceVS, DemoComplexDocumentTileSpaceVV>{
    override fun getObjectClass(): KClass<DemoComplexDocument> {
        return DemoComplexDocument::class
    }

    override fun getVMClass(): KClass<DemoComplexDocumentTileSpaceVM> {
        return DemoComplexDocumentTileSpaceVM::class
    }

    override fun getVSClass(): KClass<DemoComplexDocumentTileSpaceVS> {
        return DemoComplexDocumentTileSpaceVS::class
    }

    override fun getVVClass(): KClass<DemoComplexDocumentTileSpaceVV> {
        return DemoComplexDocumentTileSpaceVV::class
    }

    override fun read(entity: DemoComplexDocument, vmEntity: DemoComplexDocumentTileSpaceVM, ctx: MutableMap<String, Any?>) {
        vmEntity.overview = DemoComplexDocumentOverviewEditorVM()
        vmEntity.overview!!.stringProperty = entity.stringProperty
        vmEntity.simpleFields = DemoComplexDocumentSimpleFieldsEditorVM()
        vmEntity.simpleFields.stringProperty = entity.stringProperty
        vmEntity.simpleFields.booleanProperty = entity.booleanProperty
        vmEntity.simpleFields.dateProperty = entity.dateProperty
        vmEntity.simpleFields.dateTimeProperty = entity.dateTimeProperty
        vmEntity.simpleFields.entityRefProperty = entity.entityRefProperty
        vmEntity.simpleFields.enumProperty = entity.enumProperty
        vmEntity.simpleFields.floatProperty = entity.floatProperty
        vmEntity.simpleFields.integerProperty = entity.integerProperty
        vmEntity.nestedDocuments = DemoComplexDocumentNestedDocumentsEditorVM()
        entity.nestedDocuments.forEach { doc ->
            if(doc is DemoNavigatorVariant1){
                val variant = DemoComplexDocumentVariant1EditorVM()
                variant.intValue = doc.intValue
                variant.uid = doc.uid
                variant.title = "Вариант 1, значение ${doc.intValue}"
                vmEntity.nestedDocuments.values.add(variant)
            }
            if(doc is DemoNavigatorVariant2){
                val variant = DemoComplexDocumentVariant2EditorVM()
                variant.dateValue = doc.dateValue
                variant.uid = doc.uid
                variant.title = "Вариант 2, значение ${doc.dateValue}"
                vmEntity.nestedDocuments.values.add(variant)
            }
        }
    }

    override fun fillSettings(entity: DemoComplexDocument, vsEntity: DemoComplexDocumentTileSpaceVS, vmEntity: DemoComplexDocumentTileSpaceVM, ctx: MutableMap<String, Any?>) {
        vsEntity.overview = DemoComplexDocumentOverviewEditorVS()
        vsEntity.simpleFields = DemoComplexDocumentSimpleFieldsEditorVS()
        vsEntity.nestedDocuments = DemoComplexDocumentNestedDocumentsEditorVS()
        entity.nestedDocuments.forEach { doc ->
            if(doc is DemoNavigatorVariant1){
                val variant = DemoComplexDocumentVariant1EditorVS()
                variant.uid = doc.uid
                vsEntity.nestedDocuments.values.add(variant)
            }
            if(doc is DemoNavigatorVariant2){
                val variant = DemoComplexDocumentVariant2EditorVS()
                variant.uid = doc.uid
                vsEntity.nestedDocuments.values.add(variant)
            }
        }
    }

    override fun write(entity: DemoComplexDocument, vmEntity: DemoComplexDocumentTileSpaceVM, ctx: MutableMap<String, Any?>) {
        entity.stringProperty = vmEntity.simpleFields.stringProperty
        entity.booleanProperty = vmEntity.simpleFields.booleanProperty
        entity.dateProperty = vmEntity.simpleFields.dateProperty
        entity.dateTimeProperty= vmEntity.simpleFields.dateTimeProperty
        entity.entityRefProperty = vmEntity.simpleFields.entityRefProperty
        entity.enumProperty = vmEntity.simpleFields.enumProperty
        entity.floatProperty  =vmEntity.simpleFields.floatProperty
        entity.integerProperty = vmEntity.simpleFields.integerProperty
        val tempDocs = ArrayList(entity.nestedDocuments)
        entity.nestedDocuments.clear()
        vmEntity.nestedDocuments.values.forEach { variantVM ->
            val item = tempDocs.find { it.uid == variantVM.uid }?: run {
                if (variantVM is DemoComplexDocumentVariant1EditorVM) {
                    val res = DemoNavigatorVariant1()
                    res.uid = variantVM.uid
                    res
                } else {
                    val res = DemoNavigatorVariant2()
                    res.uid = variantVM.uid
                    res
                }
            }
            entity.nestedDocuments.add(item)
            if (variantVM is DemoComplexDocumentVariant1EditorVM) {
                val res = item as DemoNavigatorVariant1
                res.intValue = variantVM.intValue
            }
            if (variantVM is DemoComplexDocumentVariant2EditorVM) {
                val res = item as DemoNavigatorVariant2
                res.dateValue = variantVM.dateValue
            }
        }
    }

    override fun validate(vmEntity: DemoComplexDocumentTileSpaceVM, vvEntity: DemoComplexDocumentTileSpaceVV, ctx: MutableMap<String, Any?>) {
        vvEntity.overview = DemoComplexDocumentOverviewEditorVV()
        vvEntity.simpleFields = DemoComplexDocumentSimpleFieldsEditorVV()
        if(TextUtils.isBlank(vmEntity.simpleFields.stringProperty)){
            vvEntity.simpleFields.stringProperty = StandardServerMessagesFactory.EMPTY_FIELD().toString()
        }
        vvEntity.nestedDocuments = DemoComplexDocumentNestedDocumentsEditorVV()
        vmEntity.nestedDocuments.values.forEach { doc ->
            if(doc is DemoComplexDocumentVariant1EditorVM){
                val variant = DemoComplexDocumentVariant1EditorVV()
                variant.uid = doc.uid
                vvEntity.nestedDocuments.values.add(variant)
            }
            if(doc is DemoComplexDocumentVariant2EditorVM){
                val variant = DemoComplexDocumentVariant2EditorVV()
                variant.uid = doc.uid
                vvEntity.nestedDocuments.values.add(variant)
            }
        }
    }

    override fun getTitle(entity: DemoComplexDocument, vmEntity: DemoComplexDocumentTileSpaceVM, vsEntity: DemoComplexDocumentTileSpaceVS, ctx: MutableMap<String, Any?>): String? {
        return entity.stringProperty
    }
}