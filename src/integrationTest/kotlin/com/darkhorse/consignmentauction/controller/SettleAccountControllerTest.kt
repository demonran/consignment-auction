package com.darkhorse.consignmentauction.controller

import com.darkhorse.consignmentauction.IntegrationTest
import com.darkhorse.consignmentauction.service.SettleAccountService
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.mock.mockito.MockBean

internal class SettleAccountControllerTest : IntegrationTest() {

  @MockBean
  private lateinit var settleAccountService: SettleAccountService

  @Test
  fun `should pay auction account when auction is complete`() {
    val id = "dummyId"
    doNothing().`when`(settleAccountService).payAuctionAccount(id)
    given().post("/consignments/{id}/auction-account-payment/confirmation", mapOf("id" to id))
      .then()
      .statusCode(equalTo(200))
  }
}
