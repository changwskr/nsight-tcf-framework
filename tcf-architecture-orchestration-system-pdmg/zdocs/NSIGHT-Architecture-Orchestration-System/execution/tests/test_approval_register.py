import tempfile
import unittest
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT))

from engine.approval import add_required_approval, create_approval, validate_approval_register

class ApprovalRegisterTest(unittest.TestCase):
    def test_all_required_approvals_must_be_hash_valid_and_role_matched(self):
        with tempfile.TemporaryDirectory() as td:
            root=Path(td)
            approvals=root/'approvals'; approvals.mkdir()
            artifact=root/'ADR.json'; artifact.write_text('{"decision":"PENDING"}', encoding='utf-8')
            register=approvals/'REQUIRED-APPROVALS.json'
            add_required_approval(register,'APR-1',artifact,'ArchitectureBoard','baseline promotion')
            invalid=validate_approval_register(register,approvals,1)
            self.assertFalse(invalid['valid'])
            create_approval(artifact,'APR-1','alice','ArchitectureBoard','APPROVED','ok',None,approvals/'APR-1.json')
            valid=validate_approval_register(register,approvals,1)
            self.assertTrue(valid['valid'])

if __name__ == '__main__':
    unittest.main()
