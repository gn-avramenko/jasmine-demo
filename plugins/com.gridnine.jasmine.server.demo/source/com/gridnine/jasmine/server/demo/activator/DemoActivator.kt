/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.activator

import com.gridnine.jasmine.common.core.app.Environment
import com.gridnine.jasmine.common.core.app.IPluginActivator
import com.gridnine.jasmine.common.core.app.Registry
import com.gridnine.jasmine.common.core.storage.SearchQuery
import com.gridnine.jasmine.common.core.storage.Storage
import com.gridnine.jasmine.common.core.utils.AuthUtils
import com.gridnine.jasmine.common.demo.model.domain.*
import com.gridnine.jasmine.server.core.storage.StorageRegistry
import com.gridnine.jasmine.server.core.ui.common.ObjectEditorsRegistry
import com.gridnine.jasmine.server.core.ui.common.ViewEditorInterceptorsRegistry
import com.gridnine.jasmine.server.core.ui.widgets.restAutocompleteUrl
import com.gridnine.jasmine.server.core.web.WebAppFilter
import com.gridnine.jasmine.server.core.web.WebApplication
import com.gridnine.jasmine.server.core.web.WebServerConfig
import com.gridnine.jasmine.server.demo.storage.DemoComplexDocumentIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoComplexDocumentVariantIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoUserAccountIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoWorkspaceProvider
import com.gridnine.jasmine.server.demo.ui.*
import com.gridnine.jasmine.server.demo.web.DemoAuthFilter
import com.gridnine.jasmine.server.standard.model.WorkspaceProvider
import org.slf4j.LoggerFactory
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt

class DemoActivator : IPluginActivator {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun configure(config: Properties) {
        log.info("configuring from demo activator")
        StorageRegistry.get().register(DemoComplexDocumentIndexHandler())
        StorageRegistry.get().register(DemoComplexDocumentVariantIndexHandler())
        StorageRegistry.get().register(DemoUserAccountIndexHandler())
        addApp("","jasmine-demo-zk-index","lib/jasmine-demo-zk-index.war")
        addApp("/zk-adapter","zk-adapter","lib/zk-adapter.war")
        restAutocompleteUrl = "/ui-rest/standard_standard_autocompleteSelect2"
        WebServerConfig.get().globalFilters.add(WebAppFilter("demo-auth-filter", DemoAuthFilter::class))
        Environment.publish(WorkspaceProvider::class, DemoWorkspaceProvider())
        ObjectEditorsRegistry.get().register(DemoUserAccountEditorHandler())
        ObjectEditorsRegistry.get().register(DemoComplexDocumentEditorHandler())
        Registry.get().register(DemoUserAccountUiHandler())
        Registry.get().register(DemoComplexDocumentUiHandler())
        ViewEditorInterceptorsRegistry.get().register(DemoComplexDocumentNestedDocumentTileSpaceEditorInterceptor())
    }

    private fun addApp(context: String, res: String, file: String) {
        val resource = javaClass.classLoader.getResource(res)
        if(resource != null){
            WebServerConfig.get().addApplication(WebApplication(context, resource, javaClass.classLoader))
            return
        }
        val resourceFile = File(file)
        if(resourceFile.exists()){
            WebServerConfig.get().addApplication(WebApplication(context, resourceFile.toURI().toURL(), javaClass.classLoader))
        }
    }

    override fun activate() {
        log.info("activating from demo activator")
        AuthUtils.setCurrentUser("system")
        val size = Storage.get().searchDocuments(DemoUserAccountIndex::class, SearchQuery()).size
        if (size == 0) {
            val adminAccount = DemoUserAccount()
            adminAccount.name = "Jasmine Admin"
            adminAccount.login = "admin"
            adminAccount.password = "admin"
            Storage.get().saveDocument(adminAccount)
            for (n in 1..9) {
                val userAccount = DemoUserAccount()
                userAccount.name = "User $n"
                userAccount.login = "user$n"
                userAccount.password = "user$n"
                Storage.get().saveDocument(userAccount)
            }

            for (n in 0..9) {
                val complexObject = DemoComplexDocument()
                complexObject.booleanProperty = randomInt(1) == 1
                complexObject.dateProperty = LocalDate.of(2000 + randomInt(20), randomInt(11) + 1, randomInt(27) + 1)
                complexObject.dateTimeProperty = LocalDateTime.of(2000 + randomInt(20), randomInt(11) + 1, randomInt(27) + 1, randomInt(23), randomInt(59), randomInt(59))
                complexObject.entityRefProperty = Storage.get().findUniqueDocumentReference(DemoUserAccountIndex::class, DemoUserAccountIndex.loginProperty, "user${randomInt(10)}")
                complexObject.enumProperty = DemoEnum.valueOf("ELEMENT_${randomInt(1) + 1}")
                complexObject.floatProperty = (randomInt(100).toDouble() / 10.toDouble()).toBigDecimal()
                complexObject.integerProperty = randomInt(100)
                complexObject.stringProperty = "string_${randomInt(10)}"
                for(m in 0..5){
                    val nestedObject = DemoNestedDocument()
                    nestedObject.textColumn = "string_${randomInt(10)}"
                    nestedObject.entityRefColumn = Storage.get().findUniqueDocumentReference(DemoUserAccountIndex::class, DemoUserAccountIndex.loginProperty, "user${randomInt(10)}")
                    nestedObject.enumColumn = DemoEnum.valueOf("ELEMENT_${randomInt(1) + 1}")
                    nestedObject.floatColumn = (randomInt(100).toDouble() / 10.toDouble()).toBigDecimal()
                    nestedObject.integerColumn =randomInt(100)
                    complexObject.entityCollection.add(nestedObject)
                }
                complexObject.nestedDocuments.add(createVariant1())
                complexObject.nestedDocuments.add(createVariant2())
                complexObject.nestedDocuments.add(createVariant1())
                complexObject.nestedDocuments.add(createVariant2())

                Storage.get().saveDocument(complexObject)
            }
        }
    }

    private fun createVariant1(): DemoNavigatorVariant1 {
        val nestedObject = DemoNavigatorVariant1()
        nestedObject.title = "string_${randomInt(10)}"
        nestedObject.intValue = randomInt(100)
        return nestedObject
    }
    private fun createVariant2(): DemoNavigatorVariant2 {
        val nestedObject = DemoNavigatorVariant2()
        nestedObject.title = "string_${randomInt(10)}"
        nestedObject.dateValue = LocalDate.of(2000 + randomInt(20), randomInt(11) + 1, randomInt(27) + 1)
        return nestedObject
    }

    private fun randomInt(max: Int): Int {
        return (max * Math.random()).roundToInt()
    }


}