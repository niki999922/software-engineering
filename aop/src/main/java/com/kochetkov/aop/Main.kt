package com.kochetkov.aop

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.kochetkov.aop.aspect.LoggingExecutionTimeAspect
import com.kochetkov.aop.domain.Customer
import com.kochetkov.aop.domain.CustomerManager
import com.kochetkov.aop.domain.Person
import com.kochetkov.aop.domain.PersonManager


fun main() {
    val ctx: ApplicationContext = AnnotationConfigApplicationContext(ContextConfiguration::class.java)
    val customerManager = ctx.getBean(CustomerManager::class.java)
    val id = customerManager.addCustomer(Customer("Petr"))
    val customer = customerManager.findCustomer(id)
    generateCustomerActions(10_000, customerManager)

    val personManager = ctx.getBean(PersonManager::class.java)
    personManager.addPerson(Person("Jack Richard"))
    val mainPerson = personManager.getPerson()
    generateActions(100_000, personManager)
    println(personManager.printStats())

    val profilingAspect = ctx.getBean(LoggingExecutionTimeAspect::class.java)
    profilingAspect.printResults()
}

fun generateActions(total: Int, personManager: PersonManager) {
    for (i in 0..total) {
        when ((1..4).random()) {
            1 -> personManager.attack((0..10).random())
            2 -> personManager.health((0..10).random())
            3 -> personManager.fireball((0..10).random())
            4 -> personManager.printStats()
        }
    }
}

fun generateCustomerActions(total: Int, customerManager: CustomerManager) {
    for (i in 0..total) {
        runCatching {
            when ((1..3).random()) {
                1 -> customerManager.addCustomer(Customer((0..10).random().toString().repeat(10)))
                2 -> customerManager.findCustomer((0..(total/10)).random())
                else -> {}
            }
        }
    }
}