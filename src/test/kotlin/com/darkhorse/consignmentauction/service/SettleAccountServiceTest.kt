package com.darkhorse.consignmentauction.service

import com.darkhorse.consignmentauction.client.Auction
import com.darkhorse.consignmentauction.client.AuctionClient
import com.darkhorse.consignmentauction.client.PaymentClient
import com.darkhorse.consignmentauction.exception.AuctionNotCompleteException
import com.darkhorse.consignmentauction.exception.ErrorCode
import com.darkhorse.consignmentauction.repository.ConsignmentEntity
import com.darkhorse.consignmentauction.repository.ConsignmentRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.justRun
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatNoException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
internal class SettleAccountServiceTest {

  @MockK
  private lateinit var consignmentRepository: ConsignmentRepository

  @MockK
  private lateinit var auctionClient: AuctionClient

  @MockK
  private lateinit var paymentClient: PaymentClient

  private lateinit var settleAccountService: SettleAccountService

  @BeforeEach
  fun setUp() {
    settleAccountService = SettleAccountService(consignmentRepository, auctionClient, paymentClient)
  }


  @Test
  fun `should pay auction account successful`() {
    val id = "id"
    val auctionId = "auctionId"
    val price = BigDecimal.valueOf(5000)
    val account = "accountNumber"

    every { consignmentRepository.findByIdOrNull(id) } returns ConsignmentEntity(id, auctionId)
    every { auctionClient.queryById(auctionId) } returns Auction(id = auctionId, status = Auction.Status.COMPLETE, price = price)
    justRun { paymentClient.pay(account, price) }

    assertThatNoException().isThrownBy { settleAccountService.payAuctionAccount(id, account) }

    verify { paymentClient.pay(eq(account), eq(price)) }

  }

  @Test
  fun `should pay auction account failed giving an auction's status is PAID`() {
    val id = "id"
    val auctionId = "auctionId"
    val price = BigDecimal.valueOf(5000)
    val account = "accountNumber"

    every { consignmentRepository.findByIdOrNull(id) } returns ConsignmentEntity(id, auctionId)
    every { auctionClient.queryById(auctionId) } returns Auction(id = auctionId, status = Auction.Status.PAID, price = price)
    justRun { paymentClient.pay(account, price) }

    Assertions.assertThatExceptionOfType(AuctionNotCompleteException::class.java)
      .isThrownBy{ settleAccountService.payAuctionAccount(id, account) }
      .withMessage(ErrorCode.AUCTION_NOT_COMPLETE.name)

    verify(exactly = 0) { paymentClient.pay(eq(account), eq(price)) }

  }
}
