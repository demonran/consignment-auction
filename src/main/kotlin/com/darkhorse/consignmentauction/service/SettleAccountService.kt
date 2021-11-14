package com.darkhorse.consignmentauction.service

import com.darkhorse.consignmentauction.client.Auction
import com.darkhorse.consignmentauction.client.AuctionClient
import com.darkhorse.consignmentauction.client.PaymentClient
import com.darkhorse.consignmentauction.exception.AuctionNotCompleteException
import com.darkhorse.consignmentauction.repository.ConsignmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.math.BigDecimal

@Service
class SettleAccountService(
  private val consignmentRepository: ConsignmentRepository,
  private val auctionClient: AuctionClient,
  private val paymentClient: PaymentClient
) {
  fun payAuctionAccount(id: String, account: String) {
    val consignment = consignmentRepository.findByIdOrNull(id)
    val auctionId = consignment?.auctionId ?: throw RuntimeException()

    val auction = auctionClient.queryById(auctionId)

    validate(auction)

    paymentClient.pay(account, auction!!.price?: throw RuntimeException())

  }

  private fun validate(auction: Auction?) {
    auction ?: throw RuntimeException()

    if (auction.status != Auction.Status.COMPLETE) throw AuctionNotCompleteException()


  }

}


