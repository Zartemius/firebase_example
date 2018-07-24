package com.example.artem.firstappwithnotification.model

import com.example.artem.firstappwithnotification.model.AddressOfOrder

class Order {
    var orderId: String? = null
    var userId: String? = null
    var statusOfOrder:String? = null
    var isOrderIsCompleted: Boolean = false
    var date: String? = null
    var whatTimeTheMasterIsExpected: String? = null
    var phoneNumber: String? = null
    var description: String? = null
    var numberOfOrder: String? = null
    var contactPerson: String? = null
    var addressOfOrder: AddressOfOrder? = null
}
