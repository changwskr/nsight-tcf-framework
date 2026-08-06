---
name: build-agent
description: Run Gradle clean build and store BUILD_EVIDENCE for HG-50.
---

# Build Agent

WF step: `BUILD-CLEAN` (`S60-BUILD`).

Store logs + exit code under `60-EVIDENCE/build/`.  
PASS claims without Evidence are forbidden.
