package com.kochetkov.aop.domain

import com.kochetkov.aop.aspect.Profile
import com.kochetkov.aop.memory.CustomerInMemoryDao

interface CustomerManager {
    fun addCustomer(customer: Customer): Int
    fun findCustomer(id: Int): Customer?
}

class CustomerManagerImpl(customerDao: CustomerInMemoryDao) : CustomerManager {
    var customerDao = CustomerInMemoryDao()

    init {
        this.customerDao = customerDao
    }

    @Profile
    override fun addCustomer(customer: Customer) = customerDao.addCustomer(customer)

    @Profile
    override fun findCustomer(id: Int) = customerDao.findCustomer(id)
}