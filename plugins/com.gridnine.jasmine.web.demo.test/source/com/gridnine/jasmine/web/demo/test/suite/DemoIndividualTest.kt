package com.gridnine.jasmine.web.demo.test.suite

//import com.gridnine.jasmine.web.core.activator.await
//import com.gridnine.jasmine.web.core.activator.launch
import com.gridnine.jasmine.web.core.test.ext.before
import com.gridnine.jasmine.web.core.test.ext.describe
import com.gridnine.jasmine.web.core.test.ext.it
import kotlinx.browser.window
import kotlin.js.Promise

class DemoIndividualTest{
//    fun describeSuite(){
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
//    }
}