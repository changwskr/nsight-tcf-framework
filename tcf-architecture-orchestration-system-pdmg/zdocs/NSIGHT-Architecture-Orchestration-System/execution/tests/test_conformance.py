import unittest
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT))

from engine.conformance import compare

class ConformanceTest(unittest.TestCase):
    def test_detects_technology_gap(self):
        reference = {"promotionStatus":"RELEASED","rules":[
            {"ruleId":"JAVA","title":"java","status":"CONFIRMED","severity":"HIGH","evaluator":"technology_equal","field":"java","expected":"21"}
        ]}
        target = {"repositoryRoot":"/tmp/x","projects":[{"name":"x","technology":{"java":"17"}}]}
        result = compare(reference, target)
        self.assertEqual(result["summary"]["GAP"], 1)

if __name__ == "__main__":
    unittest.main()
