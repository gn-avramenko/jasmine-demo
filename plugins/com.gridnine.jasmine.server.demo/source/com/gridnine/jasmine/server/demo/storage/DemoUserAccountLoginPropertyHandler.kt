/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.storage

import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndex
import com.gridnine.jasmine.server.core.storage.cache.CacheConfiguration
import kotlin.reflect.KClass

class DemoUserAccountLoginPropertyHandler : CacheConfiguration.CachedPropertyHandler<DemoUserAccount>{
    override fun getIndexClass(): KClass<*> {
        return DemoUserAccountIndex::class
    }

    override fun getPropertyName(): String {
        return DemoUserAccountIndex.loginProperty.name
    }

    override fun getIdentityClass(): KClass<DemoUserAccount> {
        return DemoUserAccount::class
    }

    override fun getValue(obj: DemoUserAccount): Any? {
        return obj.login
    }

}