package com.darkhorse.consignmentauction.controller

import com.darkhorse.consignmentauction.service.SettleAccountService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("consignments")
class SettleAccountController(private val settleAccountService: SettleAccountService) {


  @PostMapping("{id}/auction-account-payment/confirmation")
  fun payAuctionAccount(@PathVariable id:String, account: String) {
    settleAccountService.payAuctionAccount(id, account)
  }
}
