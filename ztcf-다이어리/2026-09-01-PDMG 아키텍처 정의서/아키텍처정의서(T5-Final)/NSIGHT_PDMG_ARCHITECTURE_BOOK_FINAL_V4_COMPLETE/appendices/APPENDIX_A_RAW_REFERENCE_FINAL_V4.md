# APPENDIX A. ServiceId Registry

## Current Handler Registry — 13 ServiceIds

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

## Naming Rule

```text
Program   = 2 + 2 + 1 + 4 = 9 chars
ServiceId = 2 + 2 + 1 + 4 + 1 + 1 = 11 chars
```

Regex:

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

Registry와 `handle()` branch는 일치해야 하며 Duplicate는 startup fail 원칙이다.
