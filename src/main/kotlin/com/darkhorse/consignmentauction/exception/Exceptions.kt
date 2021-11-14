package com.darkhorse.consignmentauction.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class AppException(val errorCode: ErrorCode): RuntimeException(errorCode.name)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AuctionNotCompleteException : AppException(ErrorCode.AUCTION_NOT_COMPLETE)
