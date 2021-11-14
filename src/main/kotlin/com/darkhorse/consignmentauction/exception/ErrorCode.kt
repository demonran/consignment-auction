package com.darkhorse.consignmentauction.exception

enum class ErrorCode(val statusCode: Int, val message: String) {
  AUCTION_NOT_COMPLETE(400, "拍品未成交")
}
