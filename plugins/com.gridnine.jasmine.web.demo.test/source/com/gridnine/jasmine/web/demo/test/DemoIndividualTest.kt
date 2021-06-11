package com.gridnine.jasmine.web.demo.test

class DemoIndividualTest:WebDemoTestBase(){
    fun describeSuite() {
        buildBefore()
        DemoCreateNewAccountTest().testCreateNewAccount()
    }
}