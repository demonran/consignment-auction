package com.darkhorse.consignmentauction.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class ApplicationProperties(val auctionServer: AuctionServer,
                                 val paymentServer: PaymentServer) {

  data class AuctionServer(val host: String)

  data class PaymentServer(val host: String)
}
