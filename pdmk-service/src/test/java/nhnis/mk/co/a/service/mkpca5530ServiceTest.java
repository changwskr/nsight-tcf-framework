package nhnis.mk.co.a.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nhnis.mk.co.a.dao.mkpca5530Dao;
import nhnis.mk.co.a.dto.mkpca5530DtoIn;
import nhnis.mk.co.a.dto.mkpca5530DtoOut;
import nhnis.mk.co.a.dto.mkpca5530ListResponseDto;

@ExtendWith(MockitoExtension.class)
class mkpca5530ServiceTest {

    @Mock
    private mkpca5530Dao dao;

    @InjectMocks
    private mkpca5530Service service;

    @Test
    void listReturnsTotalThreeLikeOpsLog() {
        mkpca5530DtoOut row = new mkpca5530DtoOut();
        row.setL5101("18");
        row.setL5102("예금만기안내");
        when(dao.mkpca5530S0_S0_count(any())).thenReturn(3);
        when(dao.mkpca5530S0_S0(any())).thenReturn(List.of(row, new mkpca5530DtoOut(), new mkpca5530DtoOut()));

        mkpca5530ListResponseDto result = service.mkpca5530S0(new mkpca5530DtoIn());

        assertThat(result.getTotalCount()).isEqualTo(3);
        assertThat(result.getRecords()).hasSize(3);
        assertThat(result.toString()).contains("L5101 : 18");
    }

    @Test
    void listAppliesDefaultsAndTrimmedFilters() {
        mkpca5530DtoIn in = new mkpca5530DtoIn();
        in.setTrtBrc(" 10001 ");
        in.setBasDt(" 20260801 ");
        in.setPageNo(0);
        in.setPageSize(500);
        when(dao.mkpca5530S0_S0_count(any())).thenReturn(3);
        when(dao.mkpca5530S0_S0(any())).thenReturn(List.of());

        mkpca5530ListResponseDto result = service.mkpca5530S0(in);

        assertThat(result.getPageNo()).isEqualTo(1);
        assertThat(result.getPageSize()).isEqualTo(100);
        assertThat(result.getTotalCount()).isEqualTo(3);
        verify(dao).mkpca5530S0_S0(argThat(param -> param.getOffset() == 0
                && param.getPageSize() == 100
                && "10001".equals(param.getTrtBrc())
                && "20260801".equals(param.getBasDt())));
    }
}
