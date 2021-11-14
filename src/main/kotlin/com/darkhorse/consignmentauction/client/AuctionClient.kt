package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.config.ApplicationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AuctionClient(private val restTemplate: RestTemplate,
                    private val applicationProperties: ApplicationProperties) {
  fun queryById(auctionId: String): Auction? {
    return restTemplate.getForObject(applicationProperties.auctionServer.host + "/auction/{id}", Auction::class.java, mapOf("id" to auctionId))
  }

}
