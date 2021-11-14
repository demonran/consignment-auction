package com.darkhorse.consignmentauction.repository

import javax.persistence.Id

data class ConsignmentEntity(
  @Id
  var id: String,

  var auctionId: String
)
