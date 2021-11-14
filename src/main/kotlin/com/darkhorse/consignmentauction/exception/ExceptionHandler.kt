package com.darkhorse.consignmentauction.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionHandler {

  private val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

  @ExceptionHandler(AppException::class)
  fun handleAccessDeniedException(ex: AppException): ResponseEntity<ErrorEntity> {
    return ResponseEntity(
      ErrorEntity(ex.errorCode.name, ex.errorCode.message), HttpStatus.valueOf(ex.errorCode.statusCode)
    ).also {
      log.warn("Request raised an exception", ex)
    }
  }

  data class ErrorEntity(val errorCode: String, val message: String)
}
