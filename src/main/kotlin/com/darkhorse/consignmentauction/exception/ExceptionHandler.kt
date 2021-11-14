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
  fun handleAppException(ex: AppException): ResponseEntity<ErrorEntity> {
    return ResponseEntity(
      ErrorEntity(ex.errorCode.name, ex.errorCode.message), HttpStatus.valueOf(ex.errorCode.statusCode)
    ).also {
      log.warn("Request raised an app exception", ex)
    }
  }

  @ExceptionHandler(SystemException::class)
  fun handleSystemException(ex: SystemException): ResponseEntity<ErrorEntity> {
    return ResponseEntity(
      ErrorEntity(ErrorCode.SYSTEM_ERROR.name, ErrorCode.SYSTEM_ERROR.message),
      HttpStatus.valueOf(ErrorCode.SYSTEM_ERROR.statusCode)
    ).also {
      log.error("Request raised a system exception", ex)
    }
  }

  data class ErrorEntity(val errorCode: String, val message: String)
}
