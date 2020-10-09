package com.mahjong.server.ErrorResponse;

import lombok.Data;

@Data
public class ErrorResponse {

    private int status;

    private String message;

    private long timeStamp;



//    public StockOutErrorResponse(int status, String message, long timeStamp) {
//        this.status = status;
//        this.message = message;
//        this.timeStamp = timeStamp;
//    }
}
