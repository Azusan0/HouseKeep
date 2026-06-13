package com.cskaoyan.duolai.clean.user.client;


import com.cskaoyan.duolai.clean.dto.LocationResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "foundation", contextId = "foundation-map", path = "/foundation/inner/map")
public interface MapApi {

    @GetMapping("/getLocationByAddress")
    LocationResDTO getLocationByAddress(@RequestParam("address") String address);
}
