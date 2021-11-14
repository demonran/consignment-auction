package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.IntegrationTest
import com.google.common.net.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired


internal class AuctionClientTest: IntegrationTest() {

  @Autowired
  private lateinit var auctionClient: AuctionClient

  private lateinit var server: ClientAndServer

  @BeforeEach
  fun setUp() {
    server = ClientAndServer(1080)
  }

  @ParameterizedTest
  @ValueSource(strings = ["PAID", "COMPLETE"])
  fun `should return an auction with status with giving status when giving an exist auction id`(status: String) {
    val auctionId = "auctionId"

    server.`when`(HttpRequest.request().withPath("/auction/$auctionId").withMethod("GET"))
      .respond(HttpResponse.response().withStatusCode(200).withBody(
        """{"id":"$auctionId", "status": "$status"}""", MediaType.JSON_UTF_8
      ))

    val auction = auctionClient.queryById(auctionId)

    assertThat(auction).isNotNull
    assertThat(auction?.status).isEqualTo(Auction.Status.valueOf(status))
  }
}
