package com.darkhorse.consignmentauction.controller

import com.darkhorse.consignmentauction.ApiTest
import com.darkhorse.consignmentauction.IntegrationTest
import com.darkhorse.consignmentauction.service.EvaluateDto
import com.darkhorse.consignmentauction.service.EvaluateService
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean

class EvaluationControllerTest: ApiTest() {

  @MockBean
  private lateinit var evaluateService: EvaluateService

  @Test
  fun `should get evaluate result is passed when service return passed`() {
    val id = "123"
    val result = "PASSED"
    `when`(evaluateService.findEvaluate(id)).thenReturn(EvaluateDto(id, result))
    given().queryParams(mapOf("id" to id)).get("/evaluate")
      .then()
      .body("result", equalTo(result))
      .and()
      .body("id", equalTo(id))
  }
}
