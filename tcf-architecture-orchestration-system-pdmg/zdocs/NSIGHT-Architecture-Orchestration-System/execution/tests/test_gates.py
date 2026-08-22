import json
import tempfile
import unittest
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT))

from engine.gates import evaluate_gate
from engine.common import write_json

class GateTest(unittest.TestCase):
    def test_hard_missing_runtime_holds(self):
        with tempfile.TemporaryDirectory() as td:
            run = Path(td)
            gate = {"gateId":"HG90","allowConditional":False,"rules":[
                {"ruleId":"runtime","evaluator":"runtime_present","path":"50-RUNTIME/EVIDENCE-MANIFEST.json","hard":True}
            ]}
            result = evaluate_gate(gate, run)
            self.assertEqual(result["decision"], "HOLD")
            self.assertFalse(result["manualOverrideAllowed"])

    def test_evaluator_passes_json_exit_code(self):
        with tempfile.TemporaryDirectory() as td:
            run = Path(td)
            write_json(run / "40-TEST/test-result.json", {"exitCode": 0})
            gate = {"gateId":"G40","allowConditional":False,"rules":[
                {"ruleId":"test","evaluator":"json_field_equals","path":"40-TEST/test-result.json","field":"exitCode","threshold":0,"hard":True}
            ]}
            result = evaluate_gate(gate, run)
            self.assertEqual(result["decision"], "PASS")

if __name__ == "__main__":
    unittest.main()
