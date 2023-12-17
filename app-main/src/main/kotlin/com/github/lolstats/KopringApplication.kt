/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan

fun main(args: Array<String>) {
    KopringApplication().start(args)
}

/**
 * @since 2021-08-10
 */
@SpringBootApplication
@ComponentScan(
    basePackages = [
        KopringApplication.PACKAGE_NAME,
        com.github.lolstats.core.CoreKopringApplication.PACKAGE_NAME
    ]
)
class KopringApplication {
    fun start(args: Array<String>) {
        // This logic is called only once - therefore, we ignore this warning.
        @Suppress("SpreadOperator")
        SpringApplicationBuilder(KopringApplication::class.java)
            .build()
            .run(*args)
    }

    companion object {
        const val PACKAGE_NAME = "com.github.francescojo"
    }
}
