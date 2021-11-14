package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.IntegrationTest
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

internal class PaymentClientTest: IntegrationTest() {

  @Autowired
  private lateinit var paymentClient: PaymentClient

  private lateinit var server: ClientAndServer

  @BeforeEach
  fun setUp() {
    server = ClientAndServer(1080)
  }

  @Test
  fun `should pay success giving account and price` () {
    val account = "account"
    val price = BigDecimal.valueOf(5000)

    server.`when`(HttpRequest.request().withPath("/payment/pay").withMethod("POST"))
      .respond(HttpResponse.response().withStatusCode(200))

    assertThatNoException().isThrownBy { paymentClient.pay(account, price) }

  }
}
