package com.darkhorse.consignmentauction.controller

import com.darkhorse.consignmentauction.service.EvaluateDto
import com.darkhorse.consignmentauction.service.EvaluateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("evaluate")
class EvaluateController(private val evaluateService: EvaluateService) {

  @GetMapping
  fun queryEvaluate(id: String): EvaluateDto {
    return evaluateService.findEvaluate(id);
  }
}
