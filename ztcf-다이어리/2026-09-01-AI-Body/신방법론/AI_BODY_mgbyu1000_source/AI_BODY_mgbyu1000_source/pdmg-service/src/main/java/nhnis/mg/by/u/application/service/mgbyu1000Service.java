package nhnis.mg.by.u.application.service;

import nhnis.fw.exception.BizException;
import nhnis.mg.by.u.application.rule.ProfileValidationRule;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOout;
import nhnis.mg.by.u.dto.mgbyu1000S0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000S0DTOout;
import nhnis.mg.by.u.dto.mgbyu1000U0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000U0DTOout;
import nhnis.mg.by.u.persistence.dao.mgbyu1000DAO;
import nhnis.mg.by.u.support.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * mgbyu1000 Profile 업무 Service.
 *
 * <p>Transaction은 Facade/TCF Worker 경계에서 관리하고
 * Service는 업무 규칙과 DAO 호출 순서만 담당한다.</p>
 */
@Service
public class mgbyu1000Service {

    private final mgbyu1000DAO dao;
    private final ProfileValidationRule validationRule;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public mgbyu1000Service(
            mgbyu1000DAO dao,
            ProfileValidationRule validationRule,
            AuthenticatedUserProvider authenticatedUserProvider) {
        this.dao = dao;
        this.validationRule = validationRule;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    /** 본인 Profile 조회. */
    public mgbyu1000S0DTOout mgbyu1000S0(mgbyu1000S0DTOin input) throws Exception {
        String userId = authenticatedUserProvider.requireUserId();
        Map<String, Object> row = dao.mgbyu1000S0_S0(Map.of("userId", userId));

        // TEMPORARY POLICY: 미존재는 빈 DTO. TASK03/오류정책 확정 시 재검토.
        if (row == null || row.isEmpty()) {
            return new mgbyu1000S0DTOout();
        }

        mgbyu1000S0DTOout output = new mgbyu1000S0DTOout();
        output.setDisplayName(text(value(row, "DISPLAY_NAME", "displayName")));
        output.setBirthDate(dateText(value(row, "BIRTH_DATE", "birthDate")));
        output.setGenderCode(text(value(row, "GENDER_CD", "genderCode")));
        output.setTrainingExperienceCode(text(value(row, "TRAIN_EXP_CD", "trainingExperienceCode")));
        output.setActivityLevelCode(text(value(row, "ACTIVITY_LEVEL_CD", "activityLevelCode")));
        return output;
    }

    /** 최초 Profile 등록. */
    public mgbyu1000C0DTOout mgbyu1000C0(mgbyu1000C0DTOin input) throws Exception {
        validationRule.validateCreate(input);
        String userId = authenticatedUserProvider.requireUserId();

        Map<String, Object> param = param(
                userId,
                input.getDisplayName(),
                input.getBirthDate(),
                input.getGenderCode(),
                input.getTrainingExperienceCode(),
                input.getActivityLevelCode()
        );

        if (dao.mgbyu1000C0_exists(param) > 0) {
            // TODO 승인된 BY 중복 Profile 오류코드로 교체한다.
            throw new BizException("FW0001", "이미 등록된 Profile");
        }

        int affected = dao.mgbyu1000C0_C0(param);
        if (affected != 1) {
            throw new BizException("FW9999");
        }

        mgbyu1000C0DTOout output = new mgbyu1000C0DTOout();
        output.setProcessedCount(affected);
        return output;
    }

    /** 본인 Profile 전체 수정. */
    public mgbyu1000U0DTOout mgbyu1000U0(mgbyu1000U0DTOin input) throws Exception {
        validationRule.validateUpdate(input);
        String userId = authenticatedUserProvider.requireUserId();

        Map<String, Object> param = param(
                userId,
                input.getDisplayName(),
                input.getBirthDate(),
                input.getGenderCode(),
                input.getTrainingExperienceCode(),
                input.getActivityLevelCode()
        );

        int affected = dao.mgbyu1000U0_U0(param);
        if (affected == 0) {
            // TODO 승인된 BY Profile 미존재 오류코드로 교체한다.
            throw new BizException("FW0001", "Profile 미존재");
        }
        if (affected != 1) {
            throw new BizException("FW9999");
        }

        mgbyu1000U0DTOout output = new mgbyu1000U0DTOout();
        output.setProcessedCount(affected);
        return output;
    }

    private Map<String, Object> param(
            String userId,
            String displayName,
            String birthDate,
            String genderCode,
            String trainingExperienceCode,
            String activityLevelCode) {

        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("displayName", displayName);
        param.put("birthDate", birthDate);
        param.put("genderCode", genderCode);
        param.put("trainingExperienceCode", trainingExperienceCode);
        param.put("activityLevelCode", activityLevelCode);
        return param;
    }

    private Object value(Map<String, Object> row, String upperKey, String camelKey) {
        return row.containsKey(upperKey) ? row.get(upperKey) : row.get(camelKey);
    }

    private String text(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private String dateText(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate localDate) {
            return localDate.toString();
        }
        if (value instanceof Date sqlDate) {
            return sqlDate.toLocalDate().toString();
        }
        String text = String.valueOf(value);
        return text.length() >= 10 ? text.substring(0, 10) : text;
    }
}
