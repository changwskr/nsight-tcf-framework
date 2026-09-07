package nhnis.mg.by.config;

import org.apache.ibatis.annotations.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BY 업무축 RDW MyBatis Mapper 표식.
 *
 * <p>기존 PDMG의 co.a 전용 RDWMapper에 업무축 의존하지 않도록
 * BY에서 동일한 meta-annotation 계약을 제공한다.</p>
 */
@Inherited
@Mapper
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RDWMapper {
}
