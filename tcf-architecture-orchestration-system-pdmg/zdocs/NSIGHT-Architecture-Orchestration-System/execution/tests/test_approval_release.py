import tempfile
import unittest
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT))

from engine.approval import create_approval, validate_approval
from engine.common import write_json
from engine.release import release

class ApprovalReleaseTest(unittest.TestCase):
    def test_approval_invalid_after_artifact_change(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            artifact = root / "ADR.md"
            artifact.write_text("v1", encoding="utf-8")
            approval = root / "approval.json"
            create_approval(artifact, "APR-1", "alice", "ArchitectureBoard", "APPROVED", "ok", None, approval)
            self.assertTrue(validate_approval(approval)["valid"])
            artifact.write_text("v2", encoding="utf-8")
            self.assertFalse(validate_approval(approval)["valid"])

    def test_release_requires_final_pass(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            run = root / "run"
            run.mkdir()
            write_json(run / "x.json", {"a": 1})
            gate = root / "gate.json"
            write_json(gate, {"gateId":"HG90","decision":"PASS"})
            out = root / "releases"
            data = release(run, gate, out, "target")
            self.assertEqual(data["status"], "RELEASED")
            self.assertTrue((out / data["architectureBaselineId"] / "BASELINE-RELEASE.json").exists())

if __name__ == "__main__":
    unittest.main()
