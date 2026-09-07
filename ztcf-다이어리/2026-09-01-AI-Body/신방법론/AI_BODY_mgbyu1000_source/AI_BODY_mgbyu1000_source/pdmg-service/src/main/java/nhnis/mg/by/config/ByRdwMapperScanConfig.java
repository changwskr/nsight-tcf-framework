package nhnis.mg.by.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * BY 사용자 업무 DAO를 기존 RDW SqlSessionTemplate에 연결한다.
 *
 * <p>현재 PDMG AS-IS MapperScan이 co.a.persistence.dao에 한정되어 있어
 * 신규 BY 축에 별도 스캔 경계를 둔다.</p>
 */
@Configuration
@MapperScan(
        basePackages = "nhnis.mg.by.u.persistence.dao",
        annotationClass = RDWMapper.class,
        sqlSessionTemplateRef = "rdwSqlSessionTemplate"
)
public class ByRdwMapperScanConfig {
}
