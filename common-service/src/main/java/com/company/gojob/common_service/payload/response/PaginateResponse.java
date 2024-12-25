package com.company.gojob.common_service.payload.response;


import lombok.*;

@Data
@ToString
@Builder
public class PaginateResponse {
    public boolean success ;
    public Object data;
    public Object message;
    public String devMessage;
    public Object extraData;
    private Paginate paginate;
}

