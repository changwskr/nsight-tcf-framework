package nhnis.mg.by.u.application.service;

import nhnis.fw.exception.BizException;
import nhnis.mg.by.u.application.rule.ProfileValidationRule;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOin;
import nhnis.mg.by.u.persistence.dao.mgbyu1000DAO;
import nhnis.mg.by.u.support.AuthenticatedUserProvider;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class mgbyu1000ServiceTest {

    @Test
    void createUsesTrustedUserAndRejectsDuplicate() throws Exception {
        mgbyu1000DAO dao = mock(mgbyu1000DAO.class);
        ProfileValidationRule rule = new ProfileValidationRule();
        AuthenticatedUserProvider userProvider = () -> "verified-user";
        mgbyu1000Service service = new mgbyu1000Service(dao, rule, userProvider);

        when(dao.mgbyu1000C0_exists(any())).thenReturn(1);

        mgbyu1000C0DTOin input = validInput();
        assertThrows(BizException.class, () -> service.mgbyu1000C0(input));
        verify(dao, never()).mgbyu1000C0_C0(any());
    }

    @Test
    void createReturnsOneProcessedRow() throws Exception {
        mgbyu1000DAO dao = mock(mgbyu1000DAO.class);
        ProfileValidationRule rule = new ProfileValidationRule();
        AuthenticatedUserProvider userProvider = () -> "verified-user";
        mgbyu1000Service service = new mgbyu1000Service(dao, rule, userProvider);

        when(dao.mgbyu1000C0_exists(any())).thenReturn(0);
        when(dao.mgbyu1000C0_C0(any())).thenReturn(1);

        assertEquals(1, service.mgbyu1000C0(validInput()).getProcessedCount());
    }

    private mgbyu1000C0DTOin validInput() {
        mgbyu1000C0DTOin input = new mgbyu1000C0DTOin();
        input.setTrainingExperienceCode("EXPERIENCE_CODE");
        input.setActivityLevelCode("ACTIVITY_CODE");
        return input;
    }
}
