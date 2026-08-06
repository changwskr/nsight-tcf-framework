package nhnis.mk.co.a.dto;

import java.util.List;

/** mkpca8888 목록 조회 페이징 응답 DTO. */
public class mkpca8888ListResponseDto {

    private List<mkpca8888DtoOut> records;
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private int totalPages;

    public List<mkpca8888DtoOut> getRecords() { return records; }
    public void setRecords(List<mkpca8888DtoOut> records) { this.records = records; }
    public int getPageNo() { return pageNo; }
    public void setPageNo(int pageNo) { this.pageNo = pageNo; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    @Override
    public String toString() {
        return "mkpca8888ListResponseDto{totalCount=" + totalCount
                + ", pageNo=" + pageNo
                + ", pageSize=" + pageSize
                + ", records=" + records
                + '}';
    }
}
