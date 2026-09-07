# 별첨 G. Naming / Program / ServiceId

## G.1 Program

```text
mg | co | a | 9001
2  + 2  + 1 + 4
= 9 chars

mgcoa9001
```

## G.2 ServiceId

```text
mg | co | a | 9001 | S | 0
2  + 2  + 1 + 4    +1 +1
= 11 chars

mgcoa9001S0
```

Regex:

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

## G.3 Current Handler Registry — 13

```text
mgcoa5530S0

mgcoa8888S0
mgcoa8888D0

mgcoa9000S0
mgcoa9000C0
mgcoa9000U0
mgcoa9000D0

mgcoa9001S0
mgcoa9001C0
mgcoa9001U0
mgcoa9001D0

mgcoa9100S0
mgcoa9999S0
```

Duplicate ServiceId는 startup error로 처리한다.

## G.4 NSIGHT Mapping

```text
NSIGHT MP
 ↓ Mapping Registry / ADR
PDMG mg

MP ≠ mg automatically
```
