# APPENDIX B. Application Code Registry

## NSIGHT Application Groups

```text
MP  Marketing Platform
RD  RDW
AD  ADW
BI  BI Portal
DG  Data Governance
IM  IT Service / Business Support
```

Application Code는 `Group + App`을 Canonical Key로 관리한다.

```text
RD-SR ≠ AD-SR
```

PDMG Source의 `mg`와 NSIGHT Target의 `MP`는 자동 치환하지 않으며 Mapping Registry / ADR가 필요하다.
