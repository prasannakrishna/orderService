package com.bhagwat.scm.order.controller;

import com.bhagwat.scm.order.entity.CommunityOrderHeaderDocument;
import com.bhagwat.scm.order.service.CommunityOrderDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community-document-orders")
@RequiredArgsConstructor
public class CommunityOrderDocumentController {

    private final CommunityOrderDocumentService service;

    @GetMapping("/{communityId}")
    public CommunityOrderHeaderDocument getCommunityOrder(@PathVariable String communityId) {
        return service.getCommunityOrder(communityId);
    }
}
