# APPENDIX C. Package / Class Index — Detailed Final

### C.1 Module / Package Index

| Module | Type | Package | Class/Stem | Architecture Role | 상태 |
|---|---|---|---|---|---|
| pdmg-service | Root Package | nhnis.mg.co.a.* |  | Business Application | [AS-IS] |
| pdmg-service | Handler Package | nhnis.mg.co.a.entry.handler |  | TCF ON Adapter | [AS-IS] |
| pdmg-service | Controller Package | nhnis.mg.co.a.application.controller |  | TCF OFF / MVC Adapter | [AS-IS] |
| pdmg-service | Facade Package | nhnis.mg.co.a.application.facade |  | Business Use Case Boundary | [AS-IS] |
| pdmg-service | Service Package | nhnis.mg.co.a.application.service |  | Business Logic | [AS-IS] |
| pdmg-service | DTO Package | nhnis.mg.co.a.application.dto |  | Input/Output DTO | [AS-IS] |
| pdmg-service | DAO Package | nhnis.mg.co.a.persistence.dao |  | Persistence Boundary | [AS-IS] |
| pdmg-fw | Framework Root | nhnis.fw.* |  | Framework Runtime | [AS-IS] |
| pdmg-fw | Framework Root | com.ims.superspring.* |  | Framework Runtime | [AS-IS] |
| pdmg-ui | UI Root | nhnis.mg.ui.* |  | UI/Presentation | [AS-IS] |
| pdmg-jwt | JWT Root | nhnis.mg.jw.a.* |  | Authentication/Token | [AS-IS] |
| Mapper | Resource Root | rdw.mg.co.a/ |  | MyBatis Mapper Resource | [AS-IS] |
| pdmg-service | Class | nhnis.mg.co.a.entry.handler | mgcoa9000Handler.java | Handler | [CONFIRMED] |
| pdmg-service | Class | nhnis.mg.co.a.application.controller | mgcoa9000Controller.java | Controller | [CONFIRMED] |
| pdmg-service | Class | nhnis.mg.co.a.application.facade | mgcoa9000Facade.java | Facade | [CONFIRMED] |
| pdmg-service | Class | nhnis.mg.co.a.application.service | mgcoa9000Service.java | Service | [CONFIRMED] |
| pdmg-service | Class | nhnis.mg.co.a.persistence.dao | mgcoa9000DAO.java | DAO | [CONFIRMED] |
| pdmg-service | DTO | nhnis.mg.co.a.application.dto | mgcoa9000S0DTOin.java | S0 Input | [CONFIRMED] |
| pdmg-service | DTO | nhnis.mg.co.a.application.dto | mgcoa9000S0DTOout.java | S0 Output | [CONFIRMED] |
| pdmg-service | DTO | nhnis.mg.co.a.application.dto | mgcoa9000C0DTOin.java | C0 Input | [CONFIRMED] |
| pdmg-service | DTO | nhnis.mg.co.a.application.dto | mgcoa9000U0DTOin.java | U0 Input | [CONFIRMED] |
| pdmg-service | DTO | nhnis.mg.co.a.application.dto | mgcoa9000D0DTOin.java | D0 Input | [CONFIRMED] |
| pdmg-service | Class Stem | nhnis.mg.co.a.entry.handler | mgcoa9001Handler | Handler | [CONFIRMED] |
| pdmg-service | Class Stem | nhnis.mg.co.a.application.controller | mgcoa9001Controller | Controller | [CONFIRMED] |
| pdmg-service | Class Stem | nhnis.mg.co.a.application.facade | mgcoa9001Facade | Facade | [CONFIRMED] |
| pdmg-service | Class Stem | nhnis.mg.co.a.application.service | mgcoa9001Service | Service | [CONFIRMED] |
| pdmg-service | Class Stem | nhnis.mg.co.a.persistence.dao | mgcoa9001DAO | DAO | [CONFIRMED] |
| pdmg-service | DAO FQCN | nhnis.mg.co.a.persistence.dao | mgcoa8888DAO | Mapper Namespace Exact Match Reference | [CONFIRMED] |
| pdmg-service | Handler Stem | nhnis.mg.co.a.entry.handler | mgcoa5530Handler | Handler Registry | [CONFIRMED] |
| pdmg-service | Handler Stem | nhnis.mg.co.a.entry.handler | mgcoa8888Handler | Handler Registry | [CONFIRMED] |
| pdmg-service | Handler Stem | nhnis.mg.co.a.entry.handler | mgcoa9100Handler | Handler Registry | [CONFIRMED] |
| pdmg-service | Handler Stem | nhnis.mg.co.a.entry.handler | mgcoa9999Handler | Handler Registry | [CONFIRMED] |

### C.2 Current Layer Direction

```text
Handler / Controller
      ↓
Facade
      ↓
Service
      ↓
Rule [optional]
      ↓
DAO
      ↓
Mapper / SQL
```

### C.3 Important Rules

- `pdmg-fw` Build Module을 Remote Server로 해석하지 않는다.
- `pdmg-service`와 `pdmg-fw`는 동일 Spring ApplicationContext에서 협력 가능하다.
- Controller/Handler → DAO 직접호출은 금지패턴이다.
- Business Service → Servlet API 직접의존은 금지한다.
- Rule Layer는 Current 전수 AS-IS로 확인되지 않았으므로 모든 Program에 강제 표시하지 않는다.
- Package/Class 전수 Index는 Source Scanner로 자동생성하는 것을 Target으로 한다.
