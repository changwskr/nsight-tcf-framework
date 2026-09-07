package nhnis.mg.by.u.support;

/**
 * 업무 코드가 신뢰할 수 있는 로그인 사용자 식별자를 얻는 포트.
 */
public interface AuthenticatedUserProvider {

    /**
     * 검증된 사용자 식별자를 반환한다.
     *
     * @return 인증된 사용자 식별자
     */
    String requireUserId();
}
