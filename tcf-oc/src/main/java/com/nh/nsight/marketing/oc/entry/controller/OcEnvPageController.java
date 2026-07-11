package com.nh.nsight.marketing.oc.entry.controller;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@Controller

public class OcEnvPageController {



    @GetMapping("/traceenvironment")

    public String legacyRoot() {

        return "redirect:/oc/env-001.html";

    }



    @GetMapping("/traceenvironment/env-001")

    public String legacyEnv001() {

        return "redirect:/oc/env-001.html";

    }



    @GetMapping("/traceenvironment/env-002")

    public String legacyEnv002() {

        return "redirect:/oc/env-002.html";

    }



    @GetMapping("/traceenvironment/env-003")

    public String legacyEnv003() {

        return "redirect:/oc/env-003.html";

    }



    @GetMapping("/traceenvironment/env-004")

    public String legacyEnv004() {

        return "redirect:/oc/env-004.html";

    }



    @GetMapping("/traceenvironment/check")

    public String legacyCheck() {

        return "redirect:/oc/check.html";

    }



    @GetMapping("/traceenvironment/rule-check")

    public String legacyRuleCheck() {

        return "redirect:/oc/rule-check.html";

    }



    @GetMapping({"/oc/env", "/oc/env/"})

    public String ocEnvRoot() {

        return "redirect:/oc/env-001.html";

    }



    @GetMapping("/oc/env-001")

    public String env001() {

        return "redirect:/oc/env-001.html";

    }



    @GetMapping("/oc/env-002")

    public String env002() {

        return "redirect:/oc/env-002.html";

    }



    @GetMapping("/oc/env-003")

    public String env003() {

        return "redirect:/oc/env-003.html";

    }



    @GetMapping("/oc/env-004")

    public String env004() {

        return "redirect:/oc/env-004.html";

    }



    @GetMapping("/oc/check")

    public String check() {

        return "redirect:/oc/check.html";

    }



    @GetMapping("/oc/rule-check")

    public String ruleCheck() {

        return "redirect:/oc/rule-check.html";

    }

}


