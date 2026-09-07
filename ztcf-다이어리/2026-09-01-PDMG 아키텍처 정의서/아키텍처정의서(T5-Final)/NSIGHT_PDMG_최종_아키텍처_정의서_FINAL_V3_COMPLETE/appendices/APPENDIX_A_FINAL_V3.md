# 별첨 A. PDMG Application / Module Architecture

## A.1 Current Module Baseline

```text
PDMG
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-fw
├─ pdmg-service
└─ pdmg-om [CURRENT DETAIL UNKNOWN]
```

## A.2 Module과 Runtime의 구분

```text
Build Module
≠ Process
≠ Spring Context
≠ Logical Node
≠ Physical Server
```

`pdmg-fw`는 별도 Build Module이지만 Current Evidence만으로 Remote Server라고 정의하지 않는다. `pdmg-service`의 Spring Application Runtime에서 `pdmg-fw` Bean과 Business Bean이 협력할 수 있다.

## A.3 Business Layer

```text
Entry
Handler / Controller
 ↓
Facade
 ↓
Service
 ↓
Rule [optional / current not universal]
 ↓
DAO
 ↓
Mapper
 ↓
DB
```

Current General Rule Layer는 전수 확인되지 않았으므로 모든 거래에 강제 삽입하지 않는다.

## A.4 Current Package Reference

```text
Business Root
nhnis.mg.co.a.*

Framework
nhnis.fw.*
com.ims.superspring.*

JWT
nhnis.mg.jw.a.*

Mapper Resource
rdw.mg.co.a/
```
