package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sv")
public class SvProxyController extends AbstractBusinessProxyController {
    public SvProxyController(BusinessRouteService routeService) {
        super(routeService, "SV");
    }
}
