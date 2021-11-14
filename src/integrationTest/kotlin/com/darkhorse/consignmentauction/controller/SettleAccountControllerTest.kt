package com.darkhorse.consignmentauction.controller

import com.darkhorse.consignmentauction.ApiTest
import com.darkhorse.consignmentauction.exception.AuctionNotCompleteException
import com.darkhorse.consignmentauction.exception.ConsignmentNotFoundException
import com.darkhorse.consignmentauction.service.SettleAccountService
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.mock.mockito.MockBean

internal class SettleAccountControllerTest : ApiTest() {

  @MockBean
  private lateinit var settleAccountService: SettleAccountService

  @Test
  fun `should pay auction account successful when auction's status is complete`() {
    val id = "dummyId"
    val account = "account"
    doNothing().`when`(settleAccountService).payAuctionAccount(id, account)

    given().queryParams(mapOf("account" to account))
      .post("/consignments/{id}/auction-account-payment/confirmation", mapOf("id" to id))
      .then()
      .statusCode(equalTo(200))
  }

  @Test
  fun `should pay auction account failed and with status code is 400 when settle account service raise an AuctionNotCompleteException`() {
    val id = "dummyId"
    val account = "account"
    `when`(settleAccountService.payAuctionAccount(id, account)).thenThrow(AuctionNotCompleteException())

    given().queryParams(mapOf("account" to account))
      .post("/consignments/{id}/auction-account-payment/confirmation", mapOf("id" to id))
      .then()
      .statusCode(equalTo(400))
      .body("errorCode", equalTo("AUCTION_NOT_COMPLETE"))
      .body("message", equalTo("拍品未成交,无法进行拍卖款支付"))
  }

  @Test
  fun `should pay auction account failed and with status code is 404 when settle account service raise an ConsignmentNotFoundException`() {
    val id = "dummyId"
    val account = "account"
    `when`(settleAccountService.payAuctionAccount(id, account)).thenThrow(ConsignmentNotFoundException())

    given().queryParams(mapOf("account" to account))
      .post("/consignments/{id}/auction-account-payment/confirmation", mapOf("id" to id))
      .then()
      .statusCode(equalTo(404))
      .body("errorCode", equalTo("CONSIGNMENT_NOT_FOUND"))
      .body("message", equalTo("拍卖委托信息不存在,请确认请求参数是否正确"))
  }
}
