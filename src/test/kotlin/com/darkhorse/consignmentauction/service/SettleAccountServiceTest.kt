package com.darkhorse.consignmentauction.service

import com.darkhorse.consignmentauction.client.Auction
import com.darkhorse.consignmentauction.client.AuctionClient
import com.darkhorse.consignmentauction.client.PaymentClient
import com.darkhorse.consignmentauction.exception.AuctionNotCompleteException
import com.darkhorse.consignmentauction.exception.ConsignmentNotFoundException
import com.darkhorse.consignmentauction.exception.ErrorCode
import com.darkhorse.consignmentauction.repository.ConsignmentEntity
import com.darkhorse.consignmentauction.repository.ConsignmentRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
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
    every { auctionClient.queryById(auctionId) } returns Auction(
      id = auctionId,
      status = Auction.Status.COMPLETE,
      price = price
    )
    justRun { paymentClient.pay(account, price) }

    assertThatNoException().isThrownBy { settleAccountService.payAuctionAccount(id, account) }

    verify { paymentClient.pay(eq(account), eq(price)) }

  }

  @ParameterizedTest
  @ValueSource(strings = ["CREATED", "IN_PROGRESS", "ABORTED", "PAID"])
  fun `should pay auction account failed giving an auction's status is the parameter`(status: String) {
    val id = "id"
    val auctionId = "auctionId"
    val price = BigDecimal.valueOf(5000)
    val account = "accountNumber"

    every { consignmentRepository.findByIdOrNull(id) } returns ConsignmentEntity(id, auctionId)
    every { auctionClient.queryById(auctionId) } returns Auction(
      id = auctionId,
      status = Auction.Status.valueOf(status),
      price = price
    )

    Assertions.assertThatExceptionOfType(AuctionNotCompleteException::class.java)
      .isThrownBy { settleAccountService.payAuctionAccount(id, account) }
      .withMessage(ErrorCode.AUCTION_NOT_COMPLETE.name)

    verify(exactly = 0) { paymentClient.pay(any(), any()) }

  }

  @Test
  fun `should raise an exception when call payAuctionAccount giving a null auction when query from auctionClient`() {
    val id = "id"
    val auctionId = "auctionId"
    val account = "accountNumber"

    every { consignmentRepository.findByIdOrNull(id) } returns ConsignmentEntity(id, auctionId)
    every { auctionClient.queryById(auctionId) } returns null

    Assertions.assertThatExceptionOfType(AuctionNotCompleteException::class.java)
      .isThrownBy { settleAccountService.payAuctionAccount(id, account) }
      .withMessage(ErrorCode.AUCTION_NOT_COMPLETE.name)

    verify(exactly = 0) { paymentClient.pay(any(), any()) }

  }

  @Test
  fun `should raise an exception when query consignment return null object`() {
    val id = "id"
    val account = "accountNumber"

    every { consignmentRepository.findByIdOrNull(id) } returns null

    Assertions.assertThatExceptionOfType(ConsignmentNotFoundException::class.java)
      .isThrownBy { settleAccountService.payAuctionAccount(id, account) }
      .withMessage(ErrorCode.CONSIGNMENT_NOT_FOUND.name)

    verify(exactly = 0) { auctionClient.queryById(any()) }
    verify(exactly = 0) { paymentClient.pay(any(), any()) }

  }
}
