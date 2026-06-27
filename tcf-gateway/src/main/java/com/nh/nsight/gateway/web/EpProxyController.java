package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ep")
public class EpProxyController extends AbstractBusinessProxyController {
    public EpProxyController(BusinessRouteService routeService) {
        super(routeService, "EP");
    }
}
