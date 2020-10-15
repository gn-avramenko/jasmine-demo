/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/
@file:Suppress("unused")

package com.gridnine.jasmine.server.demo.activator

import com.gridnine.jasmine.server.core.app.Environment
import com.gridnine.jasmine.server.core.app.IPluginActivator
import com.gridnine.jasmine.server.core.rest.KotlinFileDevFilter
import com.gridnine.jasmine.server.core.rest.NoCacheFilter
import com.gridnine.jasmine.server.core.storage.Storage
import com.gridnine.jasmine.server.core.storage.StorageRegistry
import com.gridnine.jasmine.server.core.storage.search.SearchQuery
import com.gridnine.jasmine.server.core.utils.AuthUtils
import com.gridnine.jasmine.server.core.web.WebAppFilter
import com.gridnine.jasmine.server.core.web.WebApplication
import com.gridnine.jasmine.server.core.web.WebServerConfig
import com.gridnine.jasmine.server.demo.model.domain.*
import com.gridnine.jasmine.server.demo.storage.DemoComplexDocumentIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoComplexDocumentVariantIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoUserAccountIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoWorkspaceProvider
import com.gridnine.jasmine.server.demo.web.DemoAuthFilter
import com.gridnine.jasmine.server.standard.model.domain.*
import com.gridnine.jasmine.server.standard.rest.WorkspaceProvider
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt

class DemoActivator : IPluginActivator {
    override fun configure(config: Properties) {
        StorageRegistry.get().register(DemoComplexDocumentIndexHandler())
        StorageRegistry.get().register(DemoComplexDocumentVariantIndexHandler())
        StorageRegistry.get().register(DemoUserAccountIndexHandler())

        val jasmineCoreWebapp = WebApplication("/jasmine-core", javaClass.classLoader.getResource("jasmine-core")
                ?:  File("lib/jasmine-core.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(jasmineCoreWebapp)
        val demoApp = WebApplication("", javaClass.classLoader.getResource("jasmine-demo-index")
                ?: File("lib/jasmine-demo-index.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(demoApp)

        val easyuiLibWebapp = WebApplication("/easyui-lib", javaClass.classLoader.getResource("easyui-lib")
                ?:  File("lib/easyui-lib.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(easyuiLibWebapp)

        val selectLibWebapp = WebApplication("/select2-lib", javaClass.classLoader.getResource("select2-lib")
                ?:  File("lib/select2-lib.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(selectLibWebapp)

        val easyuiLoaderWebapp = WebApplication("/easyui-loader", javaClass.classLoader.getResource("easyui-loader")
                ?:  File("lib/easyui-loader.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(easyuiLoaderWebapp)

        val easyuiAdapterWebapp = WebApplication("/easyui-adapter", javaClass.classLoader.getResource("easyui-adapter")
                ?:  File("lib/easyui-adapter.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(easyuiAdapterWebapp)

        val jqueryLibWebapp = WebApplication("/jquery-lib", javaClass.classLoader.getResource("jquery-lib")
                ?:  File("lib/jquery-lib.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(jqueryLibWebapp)

        val easyUiWebapp = WebApplication("/jasmine-easyui", javaClass.classLoader.getResource("jasmine-easyui")
                ?:  File("lib/jasmine-easyui.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(easyUiWebapp)

        val jasmineDemoAdminWebapp = WebApplication("/jasmine-demo", javaClass.classLoader.getResource("jasmine-demo")
                ?:  File("lib/jasmine-demo.war").toURI().toURL(),
                javaClass.classLoader)
        WebServerConfig.get().addApplication(jasmineDemoAdminWebapp)

        WebServerConfig.get().globalFilters.add(WebAppFilter("nocache", NoCacheFilter::class))
        WebServerConfig.get().globalFilters.add(WebAppFilter("dev-kt-files", KotlinFileDevFilter::class))
        WebServerConfig.get().globalFilters.add(WebAppFilter("demo-auth-filter", DemoAuthFilter::class))
        Environment.publish(WorkspaceProvider::class, DemoWorkspaceProvider())
    }

    override fun activate() {
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
        nestedObject.uid = UUID.randomUUID().toString()
        nestedObject.title = "string_${randomInt(10)}"
        nestedObject.intValue = randomInt(100)
        return nestedObject
    }
    private fun createVariant2(): DemoNavigatorVariant2 {
        val nestedObject = DemoNavigatorVariant2()
        nestedObject.uid = UUID.randomUUID().toString()
        nestedObject.title = "string_${randomInt(10)}"
        nestedObject.dateValue = LocalDate.of(2000 + randomInt(20), randomInt(11) + 1, randomInt(27) + 1)
        return nestedObject
    }

    private fun randomInt(max: Int): Int {
        return (max * Math.random()).roundToInt()
    }


}