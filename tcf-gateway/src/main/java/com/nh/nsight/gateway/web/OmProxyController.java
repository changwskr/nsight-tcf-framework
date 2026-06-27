package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/om")
public class OmProxyController extends AbstractBusinessProxyController {

    public OmProxyController(BusinessRouteService routeService) {
        super(routeService, "OM");
    }
}
