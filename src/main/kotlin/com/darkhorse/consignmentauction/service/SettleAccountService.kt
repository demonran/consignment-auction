package com.darkhorse.consignmentauction.service

import com.darkhorse.consignmentauction.repository.ConsignmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class SettleAccountService(
  private val consignmentRepository: ConsignmentRepository
) {
  fun payAuctionAccount(id: String) {
    val consignment = consignmentRepository.findByIdOrNull(id)
    val id = consignment?.auctionId ?: throw RuntimeException()
  }

}


