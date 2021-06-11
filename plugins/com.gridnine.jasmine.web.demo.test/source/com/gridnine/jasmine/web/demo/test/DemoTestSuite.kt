package com.gridnine.jasmine.web.demo.test

class DemoTestSuite:WebDemoTestBase(){
    fun describeSuite() {
        buildBefore()
        DemoCreateNewAccountTest().testCreateNewAccount()
    }
}