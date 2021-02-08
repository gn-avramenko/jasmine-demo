import com.gridnine.jasmine.gradle.plugin.*
import com.gridnine.jasmine.gradle.plugin.tasks.*

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
    indexWar = "jasmine-demo-index.war"
    plugins("submodules/jasmine/plugins") {
        plugin("com.gridnine.jasmine.server.core")
        plugin("com.gridnine.jasmine.server.spf")
        plugin("com.gridnine.jasmine.server.core.test")
        plugin("com.gridnine.jasmine.server.db.h2")
        plugin("com.gridnine.jasmine.server.db.postgres")
        plugin("com.gridnine.jasmine.server.standard")
        plugin("com.gridnine.jasmine.web.core")
        plugin("com.gridnine.jasmine.web.core.test")
        plugin("com.gridnine.jasmine.web.easyui")
        plugin("com.gridnine.jasmine.web.server")
        plugin("com.gridnine.jasmine.web.server.zk")
    }
    plugins("plugins"){
        plugin("com.gridnine.jasmine.server.demo")
        plugin("com.gridnine.jasmine.server.demo.test")
        plugin("com.gridnine.jasmine.web.demo")
        plugin("com.gridnine.jasmine.web.demo.test")
    }
}

repositories{
    mavenCentral()
    jcenter()
}

apply<com.gridnine.jasmine.gradle.plugin.JasminePlugin>()


task("deploy-locally", DeployApplicationTask::class){
    group = "jenkins"
    shouldRunAfter("jenkins-dist")
    host = "localhost"
    port = 21567
}


//tasks.create("deploy-test", Deplo)

//project.configurations.create("compile")
//
//dependencies{
//    "compile"(files("submodules/jasmine/lib/spf-1.0.jar"))
//}

