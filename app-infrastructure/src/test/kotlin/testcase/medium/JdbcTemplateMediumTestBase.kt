/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.medium

import com.github.lolstats.core.CoreKopringApplication
import com.github.lolstats.core.CoreKopringApplicationImpl
import com.github.lolstats.core.appconfig.LoggerConfig
import com.github.lolstats.lib.annotation.MediumTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

/**
 * @since 2021-08-10
 */
@JdbcTest
// Overrides configuration declared in yml
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    classes = [
        CoreKopringApplicationImpl::class,
        LoggerConfig::class
    ],
)
@ComponentScan(
    basePackages = [
        CoreKopringApplication.PACKAGE_NAME
    ]
)
@ActiveProfiles("mediumTest")
@MediumTest
class JdbcTemplateMediumTestBase

