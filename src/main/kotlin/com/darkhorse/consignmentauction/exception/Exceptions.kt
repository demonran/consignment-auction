package com.darkhorse.consignmentauction.exception

abstract class AppException(val errorCode: ErrorCode): RuntimeException(errorCode.name)

class AuctionNotCompleteException : AppException(ErrorCode.AUCTION_NOT_COMPLETE)

class ConsignmentNotFoundException : AppException(ErrorCode.CONSIGNMENT_NOT_FOUND)
