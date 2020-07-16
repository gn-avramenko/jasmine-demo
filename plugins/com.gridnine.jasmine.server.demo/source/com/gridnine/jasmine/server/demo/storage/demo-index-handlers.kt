/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: jasmine
 * This file is auto generated, don't modify it manually
 *****************************************************************/

@file:Suppress("unused", "RemoveRedundantQualifierName", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "RemoveEmptyPrimaryConstructor", "FunctionName")

package com.gridnine.jasmine.server.demo.storage

import com.gridnine.jasmine.server.core.storage.IndexHandler
import com.gridnine.jasmine.server.demo.model.domain.*

class DemoUserAccountIndexHandler:IndexHandler<DemoUserAccount, DemoUserAccountIndex>{
    override val documentClass = DemoUserAccount::class
    override val indexClass = DemoUserAccountIndex::class
    override fun createIndexes(doc: DemoUserAccount): List<DemoUserAccountIndex> {
        val idx = DemoUserAccountIndex()
        idx.uid = doc.uid
        idx.login = doc.login
        idx.name = doc.name
        return arrayListOf(idx)
    }
}

class DemoComplexDocumentIndexHandler:IndexHandler<DemoComplexDocument, DemoComplexDocumentIndex>{
    override val documentClass = DemoComplexDocument::class
    override val indexClass = DemoComplexDocumentIndex::class
    override fun createIndexes(doc: DemoComplexDocument): List<DemoComplexDocumentIndex> {
        val idx = DemoComplexDocumentIndex()
        idx.uid = doc.uid
        idx.booleanProperty = doc.booleanProperty
        idx.dateProperty = doc.dateProperty
        idx.dateTimeProperty = doc.dateTimeProperty
        idx.entityRefProperty = doc.entityRefProperty
        idx.enumProperty = doc.enumProperty
        idx.floatProperty = doc.floatProperty
        idx.stringProperty = doc.stringProperty
        idx.integerProperty = doc.integerProperty
        return arrayListOf(idx)
    }
}

class DemoComplexDocumentVariantIndexHandler:IndexHandler<DemoComplexDocument, DemoComplexDocumentVariantIndex>{
    override val documentClass = DemoComplexDocument::class
    override val indexClass = DemoComplexDocumentVariantIndex::class
    override fun createIndexes(doc: DemoComplexDocument): List<DemoComplexDocumentVariantIndex> {
        val result = arrayListOf<DemoComplexDocumentVariantIndex>()
        doc.nestedDocuments.forEach {
            val idx = DemoComplexDocumentVariantIndex()
            idx.title = it.title
            idx.uid = it.uid
            result.add(idx)
        }
        return result;
    }
}