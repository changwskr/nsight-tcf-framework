package nhnis.mg.by.u.application.rule;

import nhnis.fw.exception.BizException;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOin;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProfileValidationRuleTest {

    private final ProfileValidationRule rule = new ProfileValidationRule();

    @Test
    void createRequiresTrainingExperience() {
        mgbyu1000C0DTOin input = validInput();
        input.setTrainingExperienceCode(" ");
        assertThrows(BizException.class, () -> rule.validateCreate(input));
    }

    @Test
    void createRequiresActivityLevel() {
        mgbyu1000C0DTOin input = validInput();
        input.setActivityLevelCode(null);
        assertThrows(BizException.class, () -> rule.validateCreate(input));
    }

    @Test
    void createRejectsFutureBirthDate() {
        mgbyu1000C0DTOin input = validInput();
        input.setBirthDate(LocalDate.now().plusDays(1).toString());
        assertThrows(BizException.class, () -> rule.validateCreate(input));
    }

    @Test
    void createAcceptsMinimalValidProfile() {
        assertDoesNotThrow(() -> rule.validateCreate(validInput()));
    }

    private mgbyu1000C0DTOin validInput() {
        mgbyu1000C0DTOin input = new mgbyu1000C0DTOin();
        input.setTrainingExperienceCode("EXPERIENCE_CODE");
        input.setActivityLevelCode("ACTIVITY_CODE");
        return input;
    }
}
