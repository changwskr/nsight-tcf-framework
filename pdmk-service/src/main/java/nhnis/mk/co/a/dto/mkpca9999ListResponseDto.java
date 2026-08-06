package nhnis.mk.co.a.dto;

import java.util.List;

/**
 * mkpca9999 목록 조회 페이징 응답 DTO.
 */
public class mkpca9999ListResponseDto {

    private List<mkpca9999DtoOut> records;
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private int totalPages;

    public List<mkpca9999DtoOut> getRecords() {
        return records;
    }

    public void setRecords(List<mkpca9999DtoOut> records) {
        this.records = records;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return String.valueOf(records);
    }
}
