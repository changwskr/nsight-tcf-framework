# -*- coding: utf-8 -*-
"""Build inventory xlsx from the transcribed markdown (FACT)."""
from pathlib import Path
import xlsxwriter

OUT = Path(__file__).with_name("하나은행_시스템_테크니컬_솔루션_자원_인벤토리.xlsx")

COLS = [
    "Level1",
    "Level2",
    "Level3",
    "Level4",
    "제품명",
    "개발/공급자",
    "OSS/상용",
    "라이선스",
    "기술지원 여부",
    "컨테이너화",
    "CSP 서비스",
    "Cloud 사용",
    "비고",
]


def r(l1, l2, l3, l4, name, vendor="", oss="", lic="", support="", cont="", csp="", cloud="", note=""):
    return {
        "Level1": l1,
        "Level2": l2,
        "Level3": l3,
        "Level4": l4,
        "제품명": name,
        "개발/공급자": vendor,
        "OSS/상용": oss,
        "라이선스": lic,
        "기술지원 여부": support,
        "컨테이너화": cont,
        "CSP 서비스": csp,
        "Cloud 사용": cloud,
        "비고": note,
    }


ROWS = [
    # 1. 개발환경 — 프로그래밍
    r("개발환경", "프로그래밍", "서버개발언어", "서버개발언어", "JAVA", "openjdk", "OSS", "", "", "○", "", "○",
      "Java 8 이상 권장 (LTS 8, 11, 17). EOA(End of Availability): 8은 최소 2026년 5월, 11은 최소 2024년 10월, 17은 미정. 3rd party가 지원하는 java version을 참고해서 사용. 17은 2021년 9월 출시되어 많이 사용되고 있지 않음"),
    r("개발환경", "프로그래밍", "서버개발언어", "서버개발언어", "python", "", "OSS", "", "", "○", "", "○"),
    r("개발환경", "프로그래밍", "화면개발언어", "Frontend 개발 언어", "javascript", "", "OSS", "", "", "○", "", "○",
      "Frontend 개발이나 Serverless 개발할 때 주로 사용"),
    r("개발환경", "프로그래밍", "화면개발언어", "HTML", "HTML5", "", "OSS", "", "", "○", "", "○"),
    # 2. 개발지원
    r("개발환경", "개발지원", "형상관리", "형상관리 솔루션", "Bitbucket", "Atlassian", "상용", "", "상용", "○", "", "○",
      "라이선스를 등록하지 않을 경우 기능은 제한되지만 유료로 사용 가능"),
    r("개발환경", "개발지원", "형상관리", "형상관리 솔루션", "Gitlab EE", "Gitlab", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "개발지원", "CI/CD 자동화 툴", "CI 툴", "Jenkins", "Jenkins", "OSS", "MIT License", "community", "○", "", "○"),
    r("개발환경", "개발지원", "CI/CD 자동화 툴", "CI 툴", "Bamboo", "Atlassian", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "개발지원", "CI/CD 자동화 툴", "CI 툴", "Gitlab Runner", "Gitlab", "상용", "", "상용", "○", "", "○", "K8S에 구성"),
    r("개발환경", "개발지원", "CI/CD 자동화 툴", "CD 툴", "Spinnaker", "The Linux Foundation", "OSS", "Apache 2.0", "community", "○", "", "○", "K8S에 구성"),
    r("개발환경", "개발지원", "CI/CD 자동화 툴", "CD 툴", "ArgoCD", "Argo Project Authors", "OSS", "Apache 2.0", "community", "○", "", "○", "K8S에 구성"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Library 저장소", "Nexus Repository Management OSS 3", "Sonatype Inc", "OSS", "Eclipse Public License 1.0", "community", "○", "", "○"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Library 저장소", "Nexus Repository Management Pro 3", "Sonatype Inc", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Harbor", "The Linux Foundation", "OSS", "Apache 2.0", "community", "○", "", "○"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Docker Registry", "Docker", "OSS", "Apache", "community", "○", "", "○"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Nexus Repository Management OSS 3", "Sonatype Inc", "OSS", "Eclipse Public License 1.0", "community", "○", "", "○"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Nexus Repository Management Pro 3", "Sonatype Inc", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Amazon ECR", "AWS", "상용", "", "상용", "", "AWS", "public"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Azure Container Registry", "Azure", "상용", "", "상용", "", "AZURE", "public"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Google Container Registry", "GCP", "상용", "", "상용", "", "GCP", "public"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Oracle Cloud Infrastructure Container Registry", "Oracle Cloud Infrastructure", "상용", "", "상용", "", "OCI", "public"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "Container Registry", "Naver Cloud Platform", "상용", "", "상용", "", "Naver Cloud Platform", "public"),
    r("개발환경", "개발지원", "Library 및 Container Image 저장", "Container Registry", "KT Cloud Docker Registry", "KT Cloud", "상용", "", "상용", "", "KT Cloud", "public"),
    # 3. 품질관리
    r("개발환경", "품질관리", "테스트도구", "성능 테스트 솔루션", "LoadRunner", "HP", "상용", "", "상용", "", "", "○", "Blazemeter와 연동하여 사용 가능"),
    r("개발환경", "품질관리", "테스트도구", "성능 테스트 솔루션", "Jmeter", "Apache", "OSS", "Apache 2.0", "community", "○", "", "○"),
    r("개발환경", "품질관리", "테스트도구", "성능 테스트 솔루션", "nGrinder", "Naver", "OSS", "Apache 2.0", "community", "○", "", "○"),
    r("개발환경", "품질관리", "테스트도구", "Test Case 관리", "Zephyr", "SmartBear", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "품질관리", "테스트도구", "Test Case 관리", "TestRail", "Gurock", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "JUnit", "JUNIT Team", "OSS", "Eclipse Public License 1.0", "community", "", "", "",
      "Java 테스트 프레임워크. Spring Boot에서 기본적으로 사용할 수 있도록 되어 있는 Test Framework. 소스 코드에 포함"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "TestNG", "the TestNG team", "OSS", "Apache 2.0", "community", "", "", "", "Java 테스트 프레임워크, 소스코드에 포함"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "Jest", "Facebook", "OSS", "MIT License", "community", "", "", "", "Javascript 테스트 프레임워크"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "Jasmine", "Pivotal Labs", "OSS", "MIT License", "community", "", "", "", "Javascript 테스트 프레임워크"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "Cypress", "Cypress", "상용", "", "상용", "", "", "", "Javascript end-to-end 테스트 프레임워크"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "Postman", "Postman, Inc.", "상용", "", "상용", "", "", "", "API Test"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "Insomnia", "Kong, Inc.", "상용", "", "상용", "", "", "", "API Test"),
    r("개발환경", "품질관리", "테스트도구", "테스트 툴", "DevOn Tester", "LG CNS", "상용", "", "상용", "", "", "",
      "Java 기반 프로그램의 Method(Operation) 단위 테스트. 테스트 케이스 생성/수정/삭제, 입력·예상 결과 편집, 수행, 결과 분석, Code Coverage 측정"),
    r("개발환경", "품질관리", "프로젝트 관리 도구", "SSR 접수", "Jira ServiceDesk", "Atlassian", "상용", "", "상용", "○", "", "○"),
    r("개발환경", "품질관리", "프로젝트 관리 도구", "SSR 접수", "ServiceNow", "ServiceNow", "상용", "", "상용", "", "", "○"),
    r("개발환경", "품질관리", "프로젝트 관리 도구", "프로젝트 관리 툴", "JIRA", "Atlassian", "상용", "", "상용", "", "", "○"),
    r("개발환경", "품질관리", "협업 관리", "협업 관리 툴", "Redmine", "Redmine", "OSS", "GPL v2", "community", "", "", "○"),
    r("개발환경", "품질관리", "협업 관리", "협업 관리 툴", "Confluence", "Atlassian", "상용", "", "상용", "", "", "○"),
    # 4. 개발관리 — 점검
    r("개발관리", "Container Image 취약점 점검", "Container Image 취약점 점검", "Container Image 취약점 점검 툴", "Trivy", "Aqua Security", "OSS", "Apache 2.0", "community", "○", "", "○", "Harbor와 같이 설치해서 사용 가능"),
    r("개발관리", "Container Image 취약점 점검", "Container Image 취약점 점검", "Container Image 취약점 점검 툴", "Clair", "", "OSS", "Apache 2.0", "community", "○", "", "○"),
    r("개발관리", "소스코드검사", "소스코드검사", "소스코드 정적 분석", "SonarQube", "SonarSource S.A.", "OSS", "LGPL v3", "community", "○", "", "○", "소스 정적 분석. CI/CD 단계에 포함하여 지속적으로 정적 분석 가능"),
    r("개발관리", "소스코드검사", "소스코드검사", "소스코드 검증 솔루션", "Code Insure", "아카이브 테크놀로지", "상용", "", "상용"),
    r("개발관리", "소스코드검사", "소스코드검사", "소스코드 검증 솔루션", "Sparrow", "파수닷컴", "상용", "", "상용"),
    r("개발관리", "소스코드검사", "소스코드검사", "소스코드 분석 솔루션", "Klocwork Insight", "klocwork", "상용", "", "상용"),
    r("개발관리", "소스코드검사", "소스코드검사", "소스코드 분석 솔루션", "Fortify SCA", "HP", "상용", "", "상용", "", "", "", "3개 솔루션 구분이 애매함 / 기존 TRM에 있는 솔루션"),
    r("개발관리", "소스코드검사", "소스코드검사", "소스코드 점검 솔루션", "Security Prism", "GTONE", "상용", "", "상용"),
    r("개발관리", "영향도 분석", "영향도 분석", "코드검증 솔루션 (profiler)", "ChangeMiner", "GTONE", "상용", "", "상용", "", "", "", "확인 필요"),
    r("개발관리", "어플리케이션 통합 관리", "어플리케이션 통합 관리", "", "ASTA", "아카이브 테크놀로지", "상용", "", "상용", "", "", "", "Subscription 지원"),
    # 5. 실행환경 — 미들웨어
    r("실행환경", "미들웨어", "웹애플리케이션서버", "WAS", "Jboss", "Redhat", "상용", "", "상용"),
    r("실행환경", "미들웨어", "웹애플리케이션서버", "WAS", "weblogic", "Oracle", "상용", "", "상용"),
    r("실행환경", "미들웨어", "웹애플리케이션서버", "WAS", "Websphere", "ibm", "상용", "", "상용"),
    r("실행환경", "미들웨어", "웹애플리케이션서버", "WAS", "JEUS", "Tmax", "상용", "", "상용", "", "", "", "JEUS 8.5부터 Container 환경 지원한다고 하나 사용 권장하지 않음"),
    r("실행환경", "미들웨어", "웹애플리케이션서버", "WAS", "Apache Tomcat", "Apache", "OSS", "Apache 2.0", "prof/community", "○", "", "○", "Spring Boot Embedded WAS로 사용 가능"),
    r("실행환경", "미들웨어", "웹애플리케이션서버", "WAS", "Undertow (Spring Boot Embedded Server)", "Jboss Community", "OSS", "Apache 2.0", "community", "○", "", "○",
      "JBoss/WildFly에 포함된 초경량 웹 서버. Embedded가 아니라 WAS 위에서 WEB을 실행하려면 JBoss 또는 WildFly 필요. 대량 트래픽에서 Tomcat보다 안정적으로 처리"),
    r("실행환경", "미들웨어", "웹서버", "WEB", "Oracle Http Server", "Oracle", "상용", "", "상용"),
    r("실행환경", "미들웨어", "웹서버", "WEB", "WebToB", "Tmax", "상용", "", "상용"),
    r("실행환경", "미들웨어", "웹서버", "WEB", "iPlanet", "Oracle", "상용", "", "상용"),
    r("실행환경", "미들웨어", "웹서버", "WEB", "Nginx", "Nginx", "OSS", "2-clause BSD", "community", "○", "", "○", "클라우드 환경에서는 Apache Http Server보다 Nginx를 더 선호. Nginx Plus라는 상용 Nginx 존재"),
    r("실행환경", "미들웨어", "웹서버", "WEB", "Apache Http Server", "Apache", "OSS", "Apache 2.0", "prof/community", "○", "", "○",
      "AWS ALB처럼 아이피가 유동적으로 변하는 상황에서는 장애포인트가 될 수 있음 (DNS 캐싱이 영구적 — 중간에 IP가 바뀔 경우 재기동 필요)"),
    # 6. 인터페이스
    r("실행환경", "인터페이스", "Open API", "Open API", "ONE API", "하나금융티아이", "상용", "", "상용", "○"),
    r("실행환경", "인터페이스", "Api Management", "Api Management", "MuleSoft Mule Runtime", "MuleSoft", "상용", "", "상용"),
    r("실행환경", "인터페이스", "Api Management", "Api Management", "Kong", "Kong, Inc.", "상용", "", "상용", "○"),
    r("실행환경", "인터페이스", "Api Management", "Api Management", "Google Apigee", "Google", "상용", "", "상용"),
    r("실행환경", "인터페이스", "API Gateway", "API Gateway", "Amazon API Gateway", "Amazon", "상용", "", "상용", "", "AWS", "public"),
    r("실행환경", "인터페이스", "API Gateway", "API Gateway", "Azure API Management", "Azure", "상용", "", "상용", "", "Azure", "public"),
    r("실행환경", "인터페이스", "API Gateway", "API Gateway", "Google Apigee", "Google", "상용", "", "상용", "", "GCP", "public"),
    r("실행환경", "인터페이스", "Service Mesh", "Service Mesh", "Istio", "Istio", "OSS", "Apache 2.0", "community", "○", "", "○",
      "K8S에 구성. 제일 널리 알려진 Service Mesh. 기능이 많지만 어렵고 무거움. 연계해서 K8S와 기존 VM도 Service Mesh 기능 사용 가능"),
    r("실행환경", "인터페이스", "Service Mesh", "Service Mesh", "Linkerd", "Buoyant, Inc.", "OSS", "Apache 2.0", "prof/community", "○", "", "○"),
    r("실행환경", "인터페이스", "Service Mesh", "Service Mesh", "Consul", "HashiCorp", "OSS", "Mozilla Public License 2.0", "community", "○", "", "○", "Application으로 구현"),
    r("실행환경", "인터페이스", "Service Mesh", "Service Mesh", "Spring Cloud Netflix Zuul + Eureka", "", "OSS", "Apache 2.0", "prof/community", "○", "", "○"),
    r("실행환경", "인터페이스", "Service Mesh", "Service Mesh Management Console", "Kiali", "", "OSS", "Apache 2.0", "community", "○", "", "○",
      "특정 Service Mesh만 지원 (ex. Istio, Redhat Service Mesh). ServiceMesh 모니터링 가능"),
    # 7. 데이터
    r("실행환경", "데이터", "DBMS", "OLTP", "MySql", "Oracle", "상용", "", "상용"),
    r("실행환경", "데이터", "DBMS", "OLTP", "MariaDB", "MariaDB", "OSS", "GPLv2, LGPL", "prof/community"),
    r("실행환경", "데이터", "DBMS", "OLTP", "PostgreSQL", "PostgreSQL", "OSS", "PostgreSQL License", "prof/community"),
    r("실행환경", "데이터", "DBMS", "OLTP", "Oracle", "Oracle", "상용", "", "상용", "", "", "", "OCI를 제외한 클라우드 환경에서는 RAC 구성이 안됨"),
    r("실행환경", "데이터", "DBMS", "DW", "Redshift", "Amazon", "상용", "", "상용", "", "AWS", "public"),
    r("실행환경", "데이터", "DBMS", "DW", "BigQuery", "Google", "상용", "", "상용", "", "GCP", "public"),
    r("실행환경", "데이터", "DBMS", "DW", "Synapse Analytics", "Azure", "상용", "", "상용", "", "Azure", "public"),
    r("실행환경", "데이터", "DBMS", "DW", "Snowflake", "Snowflake Inc", "상용", "", "상용", "", "", "", "클라우드 플랫폼 사용. DW, Data Lake, Data Engineering 등 Data platform"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Redis", "Redis", "OSS", "BSD", "prof/community", "", "", "", "NoSQL(Key-value Store), Cache, Session Clustering 용도로 사용 가능"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Hbase", "apache", "OSS", "Apache 2.0", "community"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "MongoDB", "MongoDB, Inc.", "OSS", "AGPL v3.0, Apache 2.0", "community"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "AWS DynamoDB", "AWS", "상용", "", "상용", "", "AWS", "public"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Azure Cosmos DB", "Azure", "상용", "", "상용", "", "Azure", "public"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Google Cloud Firestore", "GCP", "상용", "", "상용", "", "GCP", "public"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Google Cloud BigTable", "GCP", "상용", "", "상용", "", "GCP", "public"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Cloud DB for Redis", "Naver Cloud Platform", "상용", "", "상용", "", "Naver Cloud Platform", "public"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "Cloud DB for MongoDB", "Naver Cloud Platform", "상용", "", "상용", "", "Naver Cloud Platform", "public"),
    r("실행환경", "데이터", "DBMS", "NoSQL", "DBaaS for Redis", "KT Cloud", "상용", "", "상용", "", "KT Cloud", "public"),
    r("실행환경", "데이터", "Bigdata Platform", "eco system", "hadoop", "apache", "OSS", "Apache License 2.0", "community", "○"),
    # 8. 인프라
    r("실행환경", "인프라", "컨테이너", "Container", "Docker", "Docker", "OSS", "Apache 2.0", "prof/community", "○", "", "", "단, Docker Desktop의 경우에는 유료화됨"),
    r("실행환경", "인프라", "컨테이너", "Container Orchestration", "Kubernetes", "CNCF", "OSS", "Apache 2.0", "prof/community", "○", "", "",
      "Kubernetes 자체는 오픈소스이지만 설치 및 운영 유지 보수 등의 이유로 Kubernetes 기반의 상용 사용 가능"),
    r("실행환경", "인프라", "운영체제", "운영체제", "RHEL", "Redhat", "상용", "", "상용"),
    r("실행환경", "인프라", "운영체제", "운영체제", "CentOS", "CentOS", "OSS", "GPL & Various others", "prof/community", "", "", "", "CentOS 7 (EOS-2024.06.30), CentOS 8 (EOS-2021.12.31)"),
    r("실행환경", "인프라", "운영체제", "운영체제", "Oracle Linux", "Oracle", "상용", "others", "상용", "", "", "", "RHEL을 100% 지원 / 무료 사용가능"),
    r("실행환경", "인프라", "운영체제", "운영체제", "Rocky linux", "Rocky Enterprise Software Foundation", "OSS", "3-Clause BSD", "community", "", "", "",
      "CentOS 지원 종료 발표 후 CentOS 창시자가 CentOS 정책에 반발하며 만든 리눅스 / CentOS의 정신적 후계자 / Microsoft, Google, Amazon, Arm, VMware, Naver 등 지원"),
    r("실행환경", "인프라", "운영체제", "운영체제", "Amazon Linux 2", "Amazon", "상용", "", "상용", "", "AWS", "public", "AWS를 사용할 경우 사용 가능"),
    r("실행환경", "인프라", "운영체제", "Container Guest OS", "Alpine Linux", "Alpine linux", "OSS", "MIT License", "community", "○", "", "", "경량화 OS / Container Guest OS로 많이 사용"),
    r("실행환경", "인프라", "운영체제", "Container Guest OS", "Debian", "Software in the Public Interest, Inc", "OSS", "GPL & Various others", "prof/community", "○", "", "",
      "Docker hub의 Official Image에 사용되고 있는 OS"),
    # 9. 로깅
    r("실행환경", "로깅", "응용로그", "응용 로그 관리/조회", "ELK Stack", "Elastic", "OSS", "SSPL v1.1 또는 Elastic License v2", "prof/community", "○"),
    r("실행환경", "로깅", "응용로그", "응용 로그 관리/조회", "Google Cloud Logging", "GCP", "상용", "", "상용", "", "GCP", "public", "Kubernetes 응용 모니터링 가능"),
    r("실행환경", "로깅", "로그", "통합로그관리", "ELK Stack", "Elastic", "OSS", "", "prof/community", "○"),
    r("실행환경", "로깅", "로그", "통합로그관리", "Amazon CloudWatch Logs", "AWS", "상용", "", "상용", "", "AWS", "public"),
    r("실행환경", "로깅", "로그", "통합로그관리", "Azure Monitor Logs", "Azure", "상용", "", "상용", "", "AZURE", "public"),
    r("실행환경", "로깅", "로그", "통합로그관리", "Google Cloud Logging", "GCP", "상용", "", "상용", "", "GCP", "public"),
    r("실행환경", "로깅", "로그", "통합로그관리", "Oracle Cloud Infrastructure Logging", "Oracle Cloud Infrastructure", "상용", "", "상용", "", "Oracle Cloud Infrastructure", "public"),
    r("실행환경", "로깅", "로그", "통합로그관리", "Cloud Log Analytics", "Naver Cloud Platform", "상용", "", "상용", "", "Naver Cloud Platform", "public"),
    # 10. Backing Service
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "kafka", "Apache", "OSS", "Apache 2.0", "prof/community", "○", "", "",
      "Public Cloud의 경우 managed Service가 가능할 경우 Managed Service 권고"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "RabbitMQ", "VMware, Inc.", "OSS", "Mozilla Public License 2.0", "prof/community", "○"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "AWS MSK", "AWS", "상용", "", "상용", "", "AWS", "public", "AWS Managed Kafka"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "AWS SQS + SNS", "AWS", "상용", "", "상용", "", "AWS", "public"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "Azure Service Bus", "Azure", "상용", "", "상용", "", "AZURE", "public"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "GCP PubSub", "GCP", "상용", "", "상용", "", "GCP", "public"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "Simple RabbitMQ Service", "Naver Cloud Platform", "상용", "", "상용", "", "Naver Cloud Platform", "public"),
    r("실행환경", "Backing Service", "Message Queue", "Message Queue", "Message Queue - ActiveMQ", "KT Cloud", "상용", "", "상용", "", "KT Cloud", "public"),
    r("실행환경", "Backing Service", "Cache", "Cache", "Redis", "Redis Ltd.", "OSS", "BSD", "prof/community", "○", "", "",
      "Cache 용도 외 Session Clustering Server, Lua Script 실행, Pub/Sub 등 다양한 용도로 사용 가능"),
    r("실행환경", "Backing Service", "Cache", "Cache", "Memcached", "", "OSS", "3-Clause BSD", "community", "○"),
    r("실행환경", "Backing Service", "Cache", "Cache", "Amazon ElastiCache", "AWS", "상용", "", "상용", "", "AWS", "public", "AWS의 Managed Service (Redis, Memcached 지원)"),
    r("실행환경", "Backing Service", "Cache", "Cache", "Azure Cache for Redis", "Azure", "상용", "", "상용", "", "AZURE", "public"),
    r("실행환경", "Backing Service", "Cache", "Cache", "Cloud MemoryStore", "GCP", "상용", "", "상용", "", "GCP", "public", "GCP의 Managed Service (Redis, Memcached 지원)"),
    r("실행환경", "Backing Service", "Cache", "Cache", "Cloud DB for Redis", "Naver Cloud Platform", "상용", "", "상용", "", "Naver Cloud Platform", "public"),
    r("실행환경", "Backing Service", "Cache", "Cache", "DBaaS for Redis", "KT Cloud", "상용", "", "상용", "", "KT Cloud", "public"),
    # 11. 운영환경 — 모니터링
    r("운영환경", "모니터링", "인프라모니터링", "자원 모니터링", "WhaTap", "Whatap", "상용", "", "상용", "", "", "○"),
    r("운영환경", "모니터링", "인프라모니터링", "자원 모니터링", "DynaTrace", "DynaTrace, Inc", "상용", "", "상용", "", "", "○"),
    r("운영환경", "모니터링", "인프라모니터링", "자원 모니터링", "ELK + Beats", "Elastic", "OSS", "", "prof/community", "○", "", "○"),
    r("운영환경", "모니터링", "인프라모니터링", "자원 모니터링", "Prometheus + Grafana + Thanos", "", "OSS", "", "community", "○", "", "○",
      "Kubernetes Cluster마다 Prometheus 서버 구성한 후 Thanos를 통해 통합 모니터링 해야 함"),
    r("운영환경", "모니터링", "성능모니터링", "APM", "Jennifer", "Jennifer", "상용", "", "상용", "", "", "○"),
    r("운영환경", "모니터링", "성능모니터링", "APM", "WhaTap", "Whatap", "상용", "", "상용", "", "", "○"),
    r("운영환경", "모니터링", "성능모니터링", "APM", "Instana", "IBM", "상용", "", "상용", "", "", "○"),
    r("운영환경", "모니터링", "성능모니터링", "APM", "DynaTrace", "DynaTrace, Inc", "상용", "", "상용", "", "", "○"),
    r("운영환경", "모니터링", "Tracing", "API 분산 추적", "jaeger", "CNCF", "OSS", "", "community", "○", "", "○"),
    r("운영환경", "모니터링", "Tracing", "API 분산 추적", "zipkin", "zipkin", "OSS", "", "community", "○", "", "○"),
]


def main():
    wb = xlsxwriter.Workbook(str(OUT))

    title_fmt = wb.add_format({
        "bold": True, "font_size": 16, "font_name": "맑은 고딕",
        "align": "left", "valign": "vcenter",
    })
    sub_fmt = wb.add_format({
        "font_size": 10, "font_name": "맑은 고딕", "text_wrap": True, "valign": "top",
    })
    warn_fmt = wb.add_format({
        "bold": True, "font_size": 10, "font_name": "맑은 고딕",
        "font_color": "FFFFFF", "bg_color": "C00000", "align": "center", "valign": "vcenter",
    })
    label_fmt = wb.add_format({
        "bold": True, "font_name": "맑은 고딕", "font_size": 10,
        "bg_color": "F2F2F2", "border": 1, "valign": "vcenter",
    })
    value_fmt = wb.add_format({
        "font_name": "맑은 고딕", "font_size": 10, "border": 1, "valign": "vcenter", "text_wrap": True,
    })

    hdr_fmt = wb.add_format({
        "bold": True, "font_name": "맑은 고딕", "font_size": 9,
        "bg_color": "595959", "font_color": "FFFFFF",
        "align": "center", "valign": "vcenter", "text_wrap": True, "border": 1,
    })
    cell = wb.add_format({
        "font_name": "맑은 고딕", "font_size": 9, "valign": "vcenter", "border": 1, "text_wrap": True,
    })
    cell_c = wb.add_format({
        "font_name": "맑은 고딕", "font_size": 9, "align": "center", "valign": "vcenter", "border": 1,
    })
    oss_fmt = wb.add_format({
        "font_name": "맑은 고딕", "font_size": 9, "align": "center", "valign": "vcenter",
        "border": 1, "bg_color": "E2EFDA",
    })
    comm_fmt = wb.add_format({
        "font_name": "맑은 고딕", "font_size": 9, "align": "center", "valign": "vcenter",
        "border": 1, "bg_color": "FCE4D6",
    })
    l1_colors = {
        "개발환경": "D6EAF8",
        "개발관리": "D5F5E3",
        "실행환경": "FCF3CF",
        "운영환경": "FADBD8",
    }

    # --- 안내 ---
    ws0 = wb.add_worksheet("안내")
    ws0.hide_gridlines(2)
    ws0.set_column("A:A", 22)
    ws0.set_column("B:B", 90)
    ws0.set_row(0, 28)
    ws0.merge_range("A1:B1", "시스템 테크니컬 솔루션 자원 인벤토리", title_fmt)
    ws0.merge_range("A2:B2", "본 문서는 하나은행의 자산입니다. 대외 반출시 각별한 주의를 요망합니다.", warn_fmt)
    ws0.set_row(2, 8)
    meta = [
        ("문서", "1. Check List - 클라우드"),
        ("기준", "OSS TRM Inventory + CSP 특성 반영 (개발 환경·인프라)"),
        ("보조", "노란색으로 되어 있는 TRM 항목은 기존에 없는 솔루션으로 추가한 내용"),
        ("출처", "인쇄 장표 사진 전사(FACT). 원본 이미지 KakaoTalk_20260827_171035161.jpg ~ _04.jpg"),
        ("행 수", f"{len(ROWS)} (제품 1행 = 엑셀 1행)"),
        ("시트", "인벤토리 = 전체 목록 / 컬럼정의 = 헤더 범례"),
    ]
    for i, (k, v) in enumerate(meta, start=4):
        ws0.write(i, 0, k, label_fmt)
        ws0.write(i, 1, v, value_fmt)
        ws0.set_row(i, 22)

    # --- 컬럼정의 ---
    ws1 = wb.add_worksheet("컬럼정의")
    ws1.hide_gridlines(2)
    ws1.set_column("A:A", 18)
    ws1.set_column("B:B", 88)
    ws1.write_row(0, 0, ["컬럼", "헤더 원문·범례"], hdr_fmt)
    defs = [
        ("Level1~4", "계층 분류 (개발환경 / 개발관리 / 실행환경 / 운영환경)"),
        ("제품명", "제품명"),
        ("개발/공급자", "개발/공급자"),
        ("OSS/상용", "OSS/상용 여부"),
        ("라이선스", "OSS의 License 구분 (ex. Apache 2.0, MIT License)"),
        ("기술지원 여부", "상용: 상용소프트웨어 / community: community 지원 가능 / prof/community: 전문업체 지원 및 community 지원 가능"),
        ("컨테이너화", "○"),
        ("CSP 서비스", "제공 CSP 명시"),
        ("Cloud 사용", "○ : private / public cloud 모두 사용 가능  /  public : public 클라우드 사용"),
        ("비고", "비고"),
    ]
    for i, (k, v) in enumerate(defs, start=1):
        ws1.write(i, 0, k, label_fmt)
        ws1.write(i, 1, v, value_fmt)
        ws1.set_row(i, 28)
    ws1.freeze_panes(1, 0)

    # --- 인벤토리 ---
    ws = wb.add_worksheet("인벤토리")
    ws.hide_gridlines(2)
    widths = [12, 28, 22, 28, 42, 28, 12, 28, 16, 12, 26, 12, 62]
    for i, w in enumerate(widths):
        ws.set_column(i, i, w)
    ws.set_row(0, 36)
    ws.write_row(0, 0, COLS, hdr_fmt)
    ws.freeze_panes(1, 5)
    ws.autofilter(0, 0, len(ROWS), len(COLS) - 1)
    ws.repeat_rows(0)
    ws.set_landscape()
    ws.set_paper(8)  # A3
    ws.fit_to_pages(1, 0)
    ws.set_header("&C시스템 테크니컬 솔루션 자원 인벤토리")
    ws.set_footer("&L하나은행 자산 · 대외 반출 주의&R&P / &N")

    l1_fmts = {
        k: wb.add_format({
            "font_name": "맑은 고딕", "font_size": 9, "align": "center", "valign": "vcenter",
            "border": 1, "bg_color": c, "bold": True,
        })
        for k, c in l1_colors.items()
    }

    for ri, row in enumerate(ROWS, start=1):
        ws.set_row(ri, 32 if row["비고"] else 18)
        for ci, col in enumerate(COLS):
            val = row.get(col, "")
            if col == "Level1":
                ws.write(ri, ci, val, l1_fmts.get(val, cell_c))
            elif col == "OSS/상용":
                fmt = oss_fmt if val == "OSS" else (comm_fmt if val == "상용" else cell_c)
                ws.write(ri, ci, val, fmt)
            elif col in ("컨테이너화", "Cloud 사용", "기술지원 여부"):
                ws.write(ri, ci, val, cell_c)
            else:
                ws.write(ri, ci, val, cell)

    ws.conditional_format(1, 11, len(ROWS), 11, {
        "type": "text", "criteria": "containing", "value": "public",
        "format": wb.add_format({"bg_color": "D6EAF8", "font_name": "맑은 고딕", "font_size": 9, "align": "center", "border": 1}),
    })

    wb.close()
    print(f"wrote {OUT} rows={len(ROWS)}")


if __name__ == "__main__":
    main()
