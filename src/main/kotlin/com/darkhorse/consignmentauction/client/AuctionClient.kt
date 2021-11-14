package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.config.ApplicationProperties
import com.darkhorse.consignmentauction.exception.SystemException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
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
      return if (e.statusCode == HttpStatus.NOT_FOUND) null else throw SystemException()
    } catch (e: Exception) {
      throw SystemException()
    }

  }

}
