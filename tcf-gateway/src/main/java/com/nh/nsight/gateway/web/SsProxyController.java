package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ss")
public class SsProxyController extends AbstractBusinessProxyController {
    public SsProxyController(BusinessRouteService routeService) {
        super(routeService, "SS");
    }
}
