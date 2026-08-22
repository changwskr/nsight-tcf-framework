import tempfile
import unittest
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT))

from engine.scanner import scan_repository
from engine.reference import build_reference

class ScannerTest(unittest.TestCase):
    def test_scans_reference_shape_and_service_id(self):
        with tempfile.TemporaryDirectory() as td:
            repo = Path(td)
            for name in ["pdmg-ui","pdmg-fw","pdmg-service","pdmg-jwt"]:
                p = repo / name
                (p / "src/main/java/x/entry/handler").mkdir(parents=True)
                (p / "build.gradle").write_text("plugins { id 'org.springframework.boot' version '3.5.14' }\njava { toolchain { languageVersion = JavaLanguageVersion.of(21) } }\n", encoding="utf-8")
                (p / "settings.gradle").write_text(f"rootProject.name='{name}'\n", encoding="utf-8")
                (p / "src/main/java/x/entry/handler" / f"{name.replace('-','')}Handler.java").write_text(
                    "package x; import java.util.*; class SampleHandler { String x=\"mgcoa8888S0\"; }", encoding="utf-8")
            scan = scan_repository(repo, ["pdmg-ui","pdmg-fw","pdmg-service","pdmg-jwt"])
            self.assertEqual(scan["missingProjects"], [])
            self.assertEqual(len(scan["projects"]), 4)
            self.assertTrue(any("mgcoa8888S0" in p["serviceIdOwners"] for p in scan["projects"]))
            ref = build_reference(scan)
            self.assertEqual(ref["technologyConsensus"]["java"]["value"], "21")
            self.assertEqual(ref["technologyConsensus"]["springBoot"]["value"], "3.5.14")

if __name__ == "__main__":
    unittest.main()
