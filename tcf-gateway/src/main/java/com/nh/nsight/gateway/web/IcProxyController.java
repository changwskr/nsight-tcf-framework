package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ic")
public class IcProxyController extends AbstractBusinessProxyController {
    public IcProxyController(BusinessRouteService routeService) {
        super(routeService, "IC");
    }
}
