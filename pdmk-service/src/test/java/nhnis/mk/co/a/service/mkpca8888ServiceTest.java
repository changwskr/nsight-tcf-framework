package nhnis.mk.co.a.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import nhnis.fw.exception.BizException;
import nhnis.mk.co.a.dao.mkpca8888Dao;
import nhnis.mk.co.a.dto.mkpca8888DtoIn;
import nhnis.mk.co.a.dto.mkpca8888DtoOut;
import nhnis.mk.co.a.dto.mkpca8888ListResponseDto;

@ExtendWith(MockitoExtension.class)
class mkpca8888ServiceTest {

    @Mock
    private mkpca8888Dao dao;

    @InjectMocks
    private mkpca8888Service service;

    @Test
    void listAppliesDefaultsMaximumPageSizeAndTrimmedFilter() {
        mkpca8888DtoIn in = new mkpca8888DtoIn();
        in.setSalzTipKdc(" 001 ");
        in.setPageNo(0);
        in.setPageSize(500);
        when(dao.mkpca8888S0_S0_count(any())).thenReturn(101);
        when(dao.mkpca8888S0_S0(any())).thenReturn(List.of(new mkpca8888DtoOut()));

        mkpca8888ListResponseDto result = service.mkpca8888S0(in);

        assertThat(result.getPageNo()).isEqualTo(1);
        assertThat(result.getPageSize()).isEqualTo(100);
        assertThat(result.getTotalCount()).isEqualTo(101);
        assertThat(result.getTotalPages()).isEqualTo(2);
        verify(dao).mkpca8888S0_S0(argThat(param -> param.getOffset() == 0
                && param.getPageSize() == 100
                && "001".equals(param.getSalzTipKdc())));
    }

    @Test
    void detailRejectsMissingRow() {
        mkpca8888DtoIn in = completeInput();
        when(dao.mkpca8888S0_S1(any())).thenReturn(null);

        BizException error = assertThrows(BizException.class, () -> service.mkpca8888S1(in));

        assertThat(error.getCode()).isEqualTo("MP0404");
    }

    @Test
    void detailRequiresEveryPrimaryKeyField() {
        mkpca8888DtoIn in = completeInput();
        in.setBasDt(" ");

        BizException error = assertThrows(BizException.class, () -> service.mkpca8888S1(in));

        assertThat(error.getCode()).isEqualTo("FW0001");
        verify(dao, never()).mkpca8888S0_S1(any());
    }

    @Test
    void createRejectsDuplicateKey() {
        mkpca8888DtoIn in = completeInput();
        when(dao.mkpca8888S0_S1(any())).thenReturn(new mkpca8888DtoOut());

        BizException error = assertThrows(BizException.class, () -> service.mkpca8888I0(in));

        assertThat(error.getCode()).isEqualTo("MP0409");
        verify(dao, never()).mkpca8888I0_I0(any());
    }

    @Test
    void createNormalizesAndInsertsAllValues() {
        mkpca8888DtoIn in = completeInput();
        in.setPrtoCn("  등록 내용  ");
        when(dao.mkpca8888S0_S1(any())).thenReturn(null);
        when(dao.mkpca8888I0_I0(any())).thenReturn(1);

        service.mkpca8888I0(in);

        verify(dao).mkpca8888I0_I0(argThat(param -> "10001".equals(param.getTrtBrc())
                && "등록 내용".equals(param.getPrtoCn())));
    }

    @Test
    void updateAndDeleteRejectMissingRows() {
        mkpca8888DtoIn in = completeInput();
        when(dao.mkpca8888U0_U0(any())).thenReturn(0);
        assertThat(assertThrows(BizException.class, () -> service.mkpca8888U0(in)).getCode())
                .isEqualTo("MP0404");

        when(dao.mkpca8888D0_D0(any())).thenReturn(0);
        assertThat(assertThrows(BizException.class, () -> service.mkpca8888D0(in)).getCode())
                .isEqualTo("MP0404");
    }

    @Test
    void serviceJoinsControllerTransactionWithMandatory() {
        Transactional classTransaction = mkpca8888Service.class.getAnnotation(Transactional.class);
        assertThat(classTransaction).isNotNull();
        assertThat(classTransaction.propagation()).isEqualTo(
                org.springframework.transaction.annotation.Propagation.MANDATORY);
        assertThat(mkpca8888Service.class.getMethods())
                .filteredOn(method -> method.getName().matches("mkpca8888[IUD]0"))
                .allSatisfy(method -> assertThat(method.getAnnotation(Transactional.class)).isNull());
    }

    private mkpca8888DtoIn completeInput() {
        mkpca8888DtoIn in = new mkpca8888DtoIn();
        in.setTrtBrc(" 10001 ");
        in.setTrtmnEno(" E0000001 ");
        in.setSalzTipKdc(" 001 ");
        in.setBasDt(" 20260801 ");
        in.setPrtoCn("등록");
        in.setInqCn("조회");
        in.setInpCn("입력");
        return in;
    }
}
