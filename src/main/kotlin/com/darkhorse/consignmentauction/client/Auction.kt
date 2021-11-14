package com.darkhorse.consignmentauction.client

data class Auction(
  val id: String,
  val status: Status
) {
  enum class Status {
    COMPLETE
  }
}




