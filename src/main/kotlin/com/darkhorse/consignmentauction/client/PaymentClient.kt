package com.darkhorse.consignmentauction.client

import com.darkhorse.consignmentauction.config.ApplicationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

@Component
class PaymentClient(private val restTemplate: RestTemplate,
                    private val applicationProperties: ApplicationProperties) {
  fun pay(account: String, price: BigDecimal) {
    restTemplate.postForEntity(
      applicationProperties.paymentServer.host + "/payment/pay",
      PayApiModel(account, price),
      Unit.javaClass
    )
  }

}
