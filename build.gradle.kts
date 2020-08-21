import com.gridnine.jasmine.gradle.plugin.jasmine

buildscript {
    dependencies{
        "classpath"(files("submodules/jasmine/build-scripts/build/classes/kotlin/main"))
        "classpath"(files("submodules/jasmine/lib/spf-1.0.jar"))
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasmineConfigPlugin>()

jasmine {
    kotlinVersion = "1.3.71"
    libRelativePath = "submodules/jasmine/lib"
    plugins("submodules/jasmine/plugins") {
        plugin("com.gridnine.jasmine.server.core")
        plugin("com.gridnine.jasmine.server.spf")
        plugin("com.gridnine.jasmine.server.core.test")
        plugin("com.gridnine.jasmine.server.db.h2")
        plugin("com.gridnine.jasmine.server.standard")
        plugin("com.gridnine.jasmine.web.core")
        plugin("com.gridnine.jasmine.web.easyui")

    }
    plugins("plugins"){
        plugin("com.gridnine.jasmine.server.demo")
        plugin("com.gridnine.jasmine.web.demo")
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasminePlugin>()

repositories{
    mavenCentral()
}

project.configurations.create("compile")
dependencies{
    "compile"(files("submodules/jasmine/lib/spf-1.0.jar"))
}