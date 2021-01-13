package com.gridnine.jasmine.server.demo.test.web

import com.gridnine.jasmine.server.core.app.ConfigurationProvider
import com.gridnine.jasmine.server.core.app.Environment
import com.gridnine.jasmine.server.core.app.IApplicationMetadataProvider
import com.gridnine.jasmine.server.core.app.IPluginActivator
import com.gridnine.jasmine.server.core.storage.Database
import com.gridnine.jasmine.server.core.storage.Storage
import com.gridnine.jasmine.server.core.storage.StorageRegistry
import com.gridnine.jasmine.server.core.storage.impl.StorageImpl
import com.gridnine.jasmine.server.core.storage.impl.jdbc.JdbcDatabase
import com.gridnine.jasmine.server.core.storage.impl.jdbc.JdbcDialect
import com.gridnine.jasmine.server.core.web.WebApplication
import com.gridnine.jasmine.server.core.web.WebServerConfig
import com.gridnine.jasmine.server.db.h2.H2DataSource
import com.gridnine.jasmine.server.db.h2.H2dbDialect
import com.gridnine.jasmine.server.demo.storage.DemoComplexDocumentIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoComplexDocumentVariantIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoUserAccountIndexHandler
import com.gridnine.jasmine.server.demo.storage.DemoWorkspaceProvider
import com.gridnine.jasmine.server.spf.SpfApplicationMetadataProvider
import com.gridnine.jasmine.server.standard.rest.WorkspaceProvider
import com.gridnine.spf.app.SpfApplication
import com.gridnine.spf.meta.SpfPluginsRegistry
import java.io.File
import java.util.*
import javax.sql.DataSource

@Suppress("unused")
class DemoWebTest : SpfApplication {


    override fun start(config: Properties) {
        val file = File("test/demo")
        if(file.exists()){
            file.deleteRecursively()
        }
        if (!file.exists()) file.mkdirs()
        Environment.configure(file)
        Environment.publish(ConfigurationProvider::class, object : ConfigurationProvider {
            override fun getProperty(propertyName: String): String? {
                return null
            }
        })
        val clLoader = this::class.java.classLoader
        val urls = clLoader.getResources("plugin.xml").toList()
        val registry = SpfPluginsRegistry()
        registry.initRegistry(urls) {
            "com.gridnine.jasmine.server.core" == it.id
                    || "com.gridnine.jasmine.server.db.h2" == it.id
                    || "com.gridnine.jasmine.server.demo" == it.id
                    || "com.gridnine.jasmine.server.standard" == it.id
        }
        Environment.publish(IApplicationMetadataProvider::class, SpfApplicationMetadataProvider(registry))


        val activators = IApplicationMetadataProvider.get().getExtensions("activator").filter { it.plugin.pluginId != "com.gridnine.jasmine.server.sandbox" }.map { ep -> ep.plugin.classLoader.loadClass(ep.getParameters("class").first()).constructors.first().newInstance() as IPluginActivator }.toList()
        activators.forEach { a -> a.configure(config) }
        Environment.publish(DataSource::class, H2DataSource.createDataSource("jdbc:h2:mem:jasmine"))
        Environment.publish(JdbcDialect::class, H2dbDialect())
        Environment.publish(Database::class, JdbcDatabase())
        Environment.publish(Storage::class, StorageImpl())
        activators.forEach { a -> a.activate() }
    }


    override fun stop() {
        Environment.dispose()
    }
}