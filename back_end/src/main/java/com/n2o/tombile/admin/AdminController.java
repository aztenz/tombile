package com.n2o.tombile.admin;

import com.n2o.tombile.admin.dto.RSPRequestDetails;
import com.n2o.tombile.admin.dto.RSPRequestListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<RSPRequestListItem>> getRequests() {
        return ResponseEntity.ok(adminService.getCreateAccountRequests());
    }

    @GetMapping("/{uId}")
    public ResponseEntity<RSPRequestDetails> getRequestDetails(@PathVariable int uId) {
        return ResponseEntity.ok(adminService.getCreateAccountRequestById(uId));
    }

    @PutMapping("/approve")
    public ResponseEntity<String> approveAccount(@RequestParam int uId) {
        return ResponseEntity.ok(adminService.approveAccount(uId));
    }

    @PutMapping("/reject")
    public ResponseEntity<String> rejectAccount(@RequestParam int uId) {
        return ResponseEntity.ok(adminService.rejectAccount(uId));
    }
}
