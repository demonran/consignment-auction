package com.darkhorse.consignmentauction.service

import com.darkhorse.consignmentauction.client.Auction
import com.darkhorse.consignmentauction.client.AuctionClient
import com.darkhorse.consignmentauction.repository.ConsignmentEntity
import com.darkhorse.consignmentauction.repository.ConsignmentRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
internal class SettleAccountServiceTest {

  @MockK
  private lateinit var consignmentRepository: ConsignmentRepository

  @MockK
  private lateinit var auctionClient: AuctionClient

  private lateinit var settleAccountService: SettleAccountService

  @BeforeEach
  fun setUp() {
    settleAccountService = SettleAccountService(consignmentRepository, auctionClient)
  }


  @Test
  fun `should pay auction account successful`() {
    val id = "id"
    val auctionId = "auctionId"

    every { consignmentRepository.findByIdOrNull(id) } returns ConsignmentEntity(id, auctionId)
    every { auctionClient.queryById(auctionId) } returns Auction(auctionId, Auction.Status.COMPLETE)

    assertThatNoException().isThrownBy { settleAccountService.payAuctionAccount(id) }

  }
}
