package com.gridnine.jasmine.common.demo.activator

import com.gridnine.jasmine.common.core.app.IPluginActivator
import com.gridnine.jasmine.common.core.meta.DomainMetaRegistry
import com.gridnine.jasmine.common.core.meta.UiMetaRegistry
import com.gridnine.jasmine.common.core.parser.DomainMetadataParser
import com.gridnine.jasmine.common.core.parser.UiMetadataParser
import com.gridnine.jasmine.common.demo.WebPluginsAssociations
import java.util.*

/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

class DemoCommonActivator:IPluginActivator{
    override fun configure(config: Properties) {
        DomainMetadataParser.updateDomainMetaRegistry(DomainMetaRegistry.get(), "com/gridnine/jasmine/common/demo/model/demo-model-domain.xml", javaClass.classLoader)
        UiMetadataParser.updateUiMetaRegistry(UiMetaRegistry.get(), "com/gridnine/jasmine/common/demo/model/demo-ui.xml", javaClass.classLoader)
        WebPluginsAssociations.registerAssociations()
    }
}