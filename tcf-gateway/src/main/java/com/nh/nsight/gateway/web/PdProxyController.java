package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pd")
public class PdProxyController extends AbstractBusinessProxyController {
    public PdProxyController(BusinessRouteService routeService) {
        super(routeService, "PD");
    }
}
