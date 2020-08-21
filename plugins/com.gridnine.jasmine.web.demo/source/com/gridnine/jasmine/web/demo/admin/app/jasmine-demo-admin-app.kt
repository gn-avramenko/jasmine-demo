/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.app

import com.gridnine.jasmine.server.core.model.domain.DomainMetaRegistryJS
import com.gridnine.jasmine.server.standard.model.rest.GetMetadataRequestJS
import com.gridnine.jasmine.server.standard.model.rest.GetWorkspaceRequestJS
import com.gridnine.jasmine.web.core.StandardRestClient
import com.gridnine.jasmine.web.core.activator.CoreActivatorJS
import com.gridnine.jasmine.web.core.remote.StandardRpcManager
import kotlin.browser.window

fun main() {
    if(window.asDynamic().testMode){
        return
    }
    val config = hashMapOf<String,Any?>()
    config[StandardRpcManager.BASE_REST_URL_KEY] = "/ui-rest"
    val coreActivator = CoreActivatorJS()
    coreActivator.configure(config)
    coreActivator.activate().then { StandardRestClient.standard_standard_getWorkspace(GetWorkspaceRequestJS())}.then {
        console.log(it)
    }
}