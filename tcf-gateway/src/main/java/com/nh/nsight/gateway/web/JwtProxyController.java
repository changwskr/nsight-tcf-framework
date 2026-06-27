package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtProxyController extends AbstractBusinessProxyController {

    public JwtProxyController(BusinessRouteService routeService) {
        super(routeService, "JWT");
    }
}
