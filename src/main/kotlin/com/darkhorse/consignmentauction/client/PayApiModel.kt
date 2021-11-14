package com.darkhorse.consignmentauction.client

import java.math.BigDecimal

data class PayApiModel(
  val account: String,
  val price: BigDecimal)
