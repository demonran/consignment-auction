package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.IntegrationTest
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PaymentClientTest: IntegrationTest() {

  @Autowired
  private lateinit var paymentClient: PaymentClient

  private lateinit var server: ClientAndServer

  @BeforeAll
  fun setUp() {
    server = ClientAndServer(1081)
  }

  @BeforeEach
  fun beforeEach() {
    server.reset()
  }

  @Test
  fun `should pay success giving account and price` () {
    val account = "account"
    val price = BigDecimal.valueOf(5000)

    server.`when`(HttpRequest.request().withPath("/payment/pay").withMethod("POST"))
      .respond(HttpResponse.response().withStatusCode(200))

    assertThatNoException().isThrownBy { paymentClient.pay(account, price) }

  }

  @AfterAll
  fun close() {
    server.close()
  }
}
