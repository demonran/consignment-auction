package com.darkhorse.consignmentauction.service

import com.darkhorse.consignmentauction.client.Auction
import com.darkhorse.consignmentauction.client.AuctionClient
import com.darkhorse.consignmentauction.repository.ConsignmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class SettleAccountService(
  private val consignmentRepository: ConsignmentRepository,
  private val auctionClient: AuctionClient
) {
  fun payAuctionAccount(id: String) {
    val consignment = consignmentRepository.findByIdOrNull(id)
    val auctionId = consignment?.auctionId ?: throw RuntimeException()

    val auction = auctionClient.queryById(auctionId)

    validate(auction)

  }

  private fun validate(auction: Auction?) {
    if(auction?.status != Auction.Status.COMPLETE) throw RuntimeException()




  }

}


