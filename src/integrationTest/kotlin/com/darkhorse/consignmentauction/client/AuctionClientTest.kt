package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.IntegrationTest
import com.google.common.net.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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

  @Test
  fun `should return an auction when giving an exist auction id`() {


    val auctionId = "auctionId"

    server.`when`(HttpRequest.request().withPath("/auction/$auctionId").withMethod("GET"))
      .respond(HttpResponse.response().withStatusCode(200).withBody(
        """{"id":"$auctionId", "status": "COMPLETE"}""", MediaType.JSON_UTF_8
      ))

    val auction = auctionClient.queryById(auctionId)


    assertThat(auction?.status).isEqualTo(Auction.Status.COMPLETE)
  }
}
