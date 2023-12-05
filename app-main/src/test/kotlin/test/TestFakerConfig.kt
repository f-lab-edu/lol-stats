package test

import com.github.javafaker.Faker

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean


@TestConfiguration
class TestFakerConfig {
    @Bean
    fun faker(): Faker {
        return Faker()
    }
}