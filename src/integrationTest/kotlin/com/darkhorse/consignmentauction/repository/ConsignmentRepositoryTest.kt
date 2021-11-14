package com.darkhorse.consignmentauction.repository

import com.darkhorse.consignmentauction.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.jdbc.Sql

class ConsignmentRepositoryTest: IntegrationTest(){

  @Autowired
  private lateinit var consignmentRepository: ConsignmentRepository

  @Test
  @Sql("/sql/insert_a_consignment_sql.sql")
  fun `should find a consignment when giving an exist id`() {
    val id = "id"
    val auctionId = "auctionId"
    val consignment = consignmentRepository.findByIdOrNull(id)

    assertThat(consignment).isNotNull
    assertThat(consignment?.id).isEqualTo(id)
    assertThat(consignment?.auctionId).isEqualTo(auctionId)
  }
}
