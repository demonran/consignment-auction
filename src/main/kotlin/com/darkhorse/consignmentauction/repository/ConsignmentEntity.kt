package com.darkhorse.consignmentauction.repository

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "consignment")
data class ConsignmentEntity(
  @Id
  var id: String,

  var auctionId: String
)
