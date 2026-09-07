package nhnis.mg.by.u.application.rule;

import nhnis.fw.exception.BizException;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000U0DTOin;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Profile 등록/수정 공통 입력 검증.
 *
 * <p>실제 코드사전 값은 아직 확정되지 않아 길이/필수 검증까지만 수행한다.</p>
 */
@Component
public class ProfileValidationRule {

    public void validateCreate(mgbyu1000C0DTOin input) {
        if (input == null) {
            throw invalid("Profile 입력");
        }
        normalize(input);
        validate(
                input.getDisplayName(),
                input.getBirthDate(),
                input.getGenderCode(),
                input.getTrainingExperienceCode(),
                input.getActivityLevelCode()
        );
    }

    public void validateUpdate(mgbyu1000U0DTOin input) {
        if (input == null) {
            throw invalid("Profile 입력");
        }
        normalize(input);
        validate(
                input.getDisplayName(),
                input.getBirthDate(),
                input.getGenderCode(),
                input.getTrainingExperienceCode(),
                input.getActivityLevelCode()
        );
    }

    private void validate(
            String displayName,
            String birthDate,
            String genderCode,
            String trainingExperienceCode,
            String activityLevelCode) {

        if (trainingExperienceCode == null) {
            throw invalid("운동 경력");
        }
        if (activityLevelCode == null) {
            throw invalid("활동 수준");
        }

        maxLength(displayName, 100, "표시 이름");
        maxLength(genderCode, 20, "성별 코드");
        maxLength(trainingExperienceCode, 20, "운동 경력 코드");
        maxLength(activityLevelCode, 20, "활동 수준 코드");

        if (birthDate != null) {
            try {
                LocalDate date = LocalDate.parse(birthDate);
                if (date.isAfter(LocalDate.now())) {
                    throw invalid("생년월일");
                }
            } catch (DateTimeParseException e) {
                throw invalid("생년월일");
            }
        }
    }

    private void maxLength(String value, int max, String fieldName) {
        if (value != null && value.length() > max) {
            throw invalid(fieldName);
        }
    }

    private BizException invalid(String fieldName) {
        // TODO 승인된 BY 업무 오류코드로 교체한다.
        return new BizException("FW0001", fieldName);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String text = value.trim();
        return text.isEmpty() ? null : text;
    }

    private void normalize(mgbyu1000C0DTOin input) {
        input.setDisplayName(trimToNull(input.getDisplayName()));
        input.setBirthDate(trimToNull(input.getBirthDate()));
        input.setGenderCode(trimToNull(input.getGenderCode()));
        input.setTrainingExperienceCode(trimToNull(input.getTrainingExperienceCode()));
        input.setActivityLevelCode(trimToNull(input.getActivityLevelCode()));
    }

    private void normalize(mgbyu1000U0DTOin input) {
        input.setDisplayName(trimToNull(input.getDisplayName()));
        input.setBirthDate(trimToNull(input.getBirthDate()));
        input.setGenderCode(trimToNull(input.getGenderCode()));
        input.setTrainingExperienceCode(trimToNull(input.getTrainingExperienceCode()));
        input.setActivityLevelCode(trimToNull(input.getActivityLevelCode()));
    }
}
