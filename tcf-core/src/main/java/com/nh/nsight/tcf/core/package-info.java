/**
 * TCF 프레임워크 코어 — 표준 전문·거래 파이프라인·공통 인프라.
 *
 * <p>업무 WAR({@code *-service})의 {@code entry/application/persistence}와 달리,
 * 본 모듈은 <b>프레임워크 JAR</b>로 패키지 이동 없이 역할별로 분리되어 있습니다.
 *
 * <ul>
 *   <li>진입·파이프라인: {@code processor}, {@code dispatch}, {@code transaction}</li>
 *   <li>프레임워크 서비스: {@code control}, {@code timeout}, {@code validation}, {@code security}, …</li>
 *   <li>전문·컨텍스트: {@code message}, {@code context}, {@code error}</li>
 *   <li>설정·유틸: {@code config}, {@code support}</li>
 * </ul>
 */
package com.nh.nsight.tcf.core;
