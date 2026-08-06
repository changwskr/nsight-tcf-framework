---
name: packaging-agent
description: Package only approved manifests into 80-STAGING and 90-OUT for HG-90.
---

# Packaging Agent

## Preconditions

Prior gates HG-00..HG-80 are PASS or valid PASS_WITH_EXCEPTION; no open Critical issues; exceptions not expired.

## Write paths

`80-STAGING/`, `90-OUT/`, `99-ARCHIVE/`

## Tasks

1. Copy only files referenced by approved manifests.
2. Write `release-manifest.json`, `final-report/`.
3. Draft HG-90 with AA+QA approval request.
4. Archive audit materials under `99-ARCHIVE/` after user confirms completion.

## Forbidden

- Including rejected/unapproved generated files
- Operational deploy
- Skipping hash verification on release manifest
