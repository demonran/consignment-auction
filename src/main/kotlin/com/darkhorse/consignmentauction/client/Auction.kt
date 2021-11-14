package com.darkhorse.consignmentauction.client

import java.math.BigDecimal

data class Auction(
  val id: String,
  val status: Status,
  val price: BigDecimal?
) {
  enum class Status {
    IN_PROGRESS, ABORTED, PAID, COMPLETE
  }
}




