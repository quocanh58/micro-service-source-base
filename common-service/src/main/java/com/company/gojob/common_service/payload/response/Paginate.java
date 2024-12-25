package com.company.gojob.common_service.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Paginate {
    private long totalRecords;
    private int pageIndex;
    private int pageSize;
    private int totalPages;

    public Paginate(long totalRecords, int page, int pageSize, int totalPages) {
        this.totalRecords = totalRecords;
        this.pageIndex = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }
}
