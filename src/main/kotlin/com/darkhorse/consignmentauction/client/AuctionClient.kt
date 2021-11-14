package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.config.ApplicationProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.lang.RuntimeException

@Component
class AuctionClient(
  private val restTemplate: RestTemplate,
  private val applicationProperties: ApplicationProperties
) {
  fun queryById(auctionId: String): Auction? {
    return try {
      restTemplate.getForObject(
        applicationProperties.auctionServer.host + "/auction/{id}",
        Auction::class.java,
        mapOf("id" to auctionId)
      )
    } catch (e: HttpClientErrorException) {
      if (e.statusCode == HttpStatus.NOT_FOUND) {
        return null
      }
      throw RuntimeException()
    }

  }

}
