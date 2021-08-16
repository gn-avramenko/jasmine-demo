package com.gridnine.jasmine.web.demo.test

import com.gridnine.jasmine.web.antd.activator.AntdWebActivator
import com.gridnine.jasmine.web.core.common.ActivatorJS
import com.gridnine.jasmine.web.core.test.WebCoreTestBase
import com.gridnine.jasmine.web.demo.activator.WebDemoActivator
import com.gridnine.jasmine.web.standard.activator.WebStandardActivator

open class WebDemoTestBase:WebCoreTestBase(){

    override fun getActivators(): MutableList<ActivatorJS> {
        return super.getActivators().also {
            it.add(WebStandardActivator())
            it.add(AntdWebActivator())
            it.add(WebDemoActivator())
            it.add(WebDemoTestActivator())
        }
    }


//        describe("demo-individual-test") {
//            before {
//                console.log("before test")
//                Promise<Unit>{resolve, _ ->
//                    launch {
//                        console.log("inside launch")
//                        Promise<Unit> { resolve2, _ ->
//                            window.setTimeout({
//                                console.log("inside launch promise")
//                                resolve2(Unit)
//                            }, 1000)
//                        }.await()
//                        console.log("after inside launch promise")
//                        resolve(Unit)
//                    }
//                }
//            }
//            it("test-create-new-account") {
//                Promise<Unit>{resolve, reject ->
//                    launch {
//                        console.log("inside test")
//                        Promise<Unit> { resolve2, _ ->
//                            window.setTimeout({
//                                console.log("inside test promise")
//                                resolve2(Unit)
//                            }, 2000)
//                        }.await()
//                        console.log("after test promise")
//                        //reject(Error("inside test"))
//                        resolve(Unit)
//                    }
//                }
//            }
//            it("test-create-new-account2") {
//                Promise<Unit>{resolve, reject ->
//                    launch {
//                        console.log("inside test 2 ")
//                        Promise<Unit> { resolve2, _ ->
//                            window.setTimeout({
//                                console.log("inside test promise 2")
//                                resolve2(Unit)
//                            }, 2000)
//                        }.await()
//                        console.log("after test promise 2")
//                        //reject(Error("inside test"))
//                        resolve(Unit)
//                    }
//                }
//            }
//        }
    }
