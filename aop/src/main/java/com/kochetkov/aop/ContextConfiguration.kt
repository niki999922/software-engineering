package com.kochetkov.aop

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import com.kochetkov.aop.memory.CustomerInMemoryDao
import com.kochetkov.aop.aspect.LoggingExecutionTimeAspect
import com.kochetkov.aop.domain.CustomerManager
import com.kochetkov.aop.domain.CustomerManagerImpl
import com.kochetkov.aop.domain.PersonManager
import com.kochetkov.aop.domain.PersonManagerImpl

@Configuration
@EnableAspectJAutoProxy
open class ContextConfiguration {
    @Bean
    open fun customerManager(): CustomerManager {
        return CustomerManagerImpl(CustomerInMemoryDao())
    }

    @Bean
    open fun aspect(): LoggingExecutionTimeAspect {
        return LoggingExecutionTimeAspect()
    }

    @Bean
    open fun person(): PersonManager {
        return PersonManagerImpl()
    }
}