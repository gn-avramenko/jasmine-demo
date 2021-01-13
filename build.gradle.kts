import com.gridnine.jasmine.gradle.plugin.*

buildscript {
    dependencies{
        "classpath"(files("submodules/jasmine/build-scripts/build/classes/kotlin/main"))
        "classpath"(files("submodules/jasmine/lib/spf-1.0.jar"))
    }
}
plugins {
    id("com.github.node-gradle.node") version("2.2.3")
}
apply<com.gridnine.jasmine.gradle.plugin.JasmineConfigPlugin>()

jasmine {
    kotlinVersion = "1.4.10"
    libRelativePath = "submodules/jasmine/lib"
    plugins("submodules/jasmine/plugins") {
        plugin("com.gridnine.jasmine.server.core")
        plugin("com.gridnine.jasmine.server.spf")
        plugin("com.gridnine.jasmine.server.core.test")
        plugin("com.gridnine.jasmine.server.db.h2")
        plugin("com.gridnine.jasmine.server.db.postgres")
        plugin("com.gridnine.jasmine.server.standard")
        plugin("com.gridnine.jasmine.web.core")
        plugin("com.gridnine.jasmine.web.easyui")

    }
    plugins("plugins"){
        plugin("com.gridnine.jasmine.server.demo")
        plugin("com.gridnine.jasmine.server.demo.test")
        plugin("com.gridnine.jasmine.web.demo")
        plugin("com.gridnine.jasmine.web.demo.test")
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasminePlugin>()

repositories{
    mavenCentral()
    jcenter()
}

//project.configurations.create("compile")
//
//dependencies{
//    "compile"(files("submodules/jasmine/lib/spf-1.0.jar"))
//}

