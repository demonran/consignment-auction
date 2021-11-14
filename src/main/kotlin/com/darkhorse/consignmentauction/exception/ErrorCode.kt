package com.darkhorse.consignmentauction.exception

enum class ErrorCode(val statusCode: Int, val message: String) {
  AUCTION_NOT_COMPLETE(400, "拍品未成交,无法进行拍卖款支付"),
  CONSIGNMENT_NOT_FOUND(404, "拍卖委托信息不存在,请确认请求参数是否正确"),

  SYSTEM_ERROR(500, "系统错误，请稍后再试")
}
