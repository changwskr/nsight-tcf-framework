package nhnis.mk.co.a.dto;

/**
 * mkpca5530 전문 입력 DTO.
 *
 * <p>목록 조회({@code mkpca5530S0})는 취급점·기준일자 선택 필터와 페이징을 사용한다.
 */
public class mkpca5530DtoIn {

    /** 취급점 코드 (Argument: BRC) */
    private String trtBrc;

    /** 기준일자 (yyyyMMdd) */
    private String basDt;

    private Integer pageNo;
    private Integer pageSize;
    private Integer offset;

    public String getTrtBrc() {
        return trtBrc;
    }

    public void setTrtBrc(String trtBrc) {
        this.trtBrc = trtBrc;
    }

    public String getBasDt() {
        return basDt;
    }

    public void setBasDt(String basDt) {
        this.basDt = basDt;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
