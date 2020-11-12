/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.server.core.utils.TextUtils
import com.gridnine.jasmine.server.demo.model.domain.DemoComplexDocument
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
        vmEntity.overview.stringProperty = entity.stringProperty
        vmEntity.simpleFields = DemoComplexDocumentSimpleFieldsEditorVM()
        vmEntity.simpleFields.stringProperty = entity.stringProperty
    }

    override fun fillSettings(entity: DemoComplexDocument, vsEntity: DemoComplexDocumentTileSpaceVS, vmEntity: DemoComplexDocumentTileSpaceVM, ctx: MutableMap<String, Any?>) {
        vsEntity.overview = DemoComplexDocumentOverviewEditorVS()
        vsEntity.simpleFields = DemoComplexDocumentSimpleFieldsEditorVS()
    }

    override fun write(entity: DemoComplexDocument, vmEntity: DemoComplexDocumentTileSpaceVM, ctx: MutableMap<String, Any?>) {
        //noops
    }

    override fun validate(vmEntity: DemoComplexDocumentTileSpaceVM, vvEntity: DemoComplexDocumentTileSpaceVV, ctx: MutableMap<String, Any?>) {
        vvEntity.overview = DemoComplexDocumentOverviewEditorVV()
        vvEntity.simpleFields = DemoComplexDocumentSimpleFieldsEditorVV()
        if(TextUtils.isBlank(vmEntity.simpleFields.stringProperty)){
            vvEntity.simpleFields.stringProperty = StandardServerMessagesFactory.EMPTY_FIELD().toString()
        }
    }

}