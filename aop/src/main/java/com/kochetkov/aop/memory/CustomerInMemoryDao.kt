package com.kochetkov.aop.memory

import com.kochetkov.aop.domain.Customer
import java.lang.RuntimeException
import java.util.HashMap
import java.util.concurrent.atomic.AtomicInteger

class CustomerInMemoryDao {
    private val customers = HashMap<Int, Customer>()

    fun addCustomer(customer: Customer): Int {
        val id = currentId.getAndIncrement()
        customers[id] = customer
        return id
    }

    fun findCustomer(id: Int): Customer? {
        return if (customers.containsKey(id)) {
            customers[id]
        } else {
            throw EntityNotFoundException("Customer couldn't be found by id: $id")
        }
    }

    companion object {
        private val currentId = AtomicInteger(1)
    }

    class EntityNotFoundException(message: String?) : RuntimeException(message)
}