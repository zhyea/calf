package org.chobit.calf.web.api;

import org.chobit.calf.model.RemoteRequest;
import org.chobit.calf.model.RemoteResponse;
import org.chobit.calf.service.RemoteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/remote")
public class RemoteCodeController {

    @Autowired
    private RemoteCodeService remoteCodeService;


    @PostMapping("/work")
    public RemoteResponse insert(@RequestHeader("code") String code,
                                 @RequestBody RemoteRequest request) {
        return remoteCodeService.handle(code, request);
    }


}
