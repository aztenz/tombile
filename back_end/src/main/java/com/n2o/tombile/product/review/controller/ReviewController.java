package com.n2o.tombile.product.review.controller;

import com.n2o.tombile.product.review.dto.RQPostReview;
import com.n2o.tombile.product.review.dto.RSPPostReview;
import com.n2o.tombile.product.review.dto.RSPReviewDetails;
import com.n2o.tombile.product.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products/{pId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<RSPPostReview> addReview(
            @PathVariable int pId,
            @Valid @RequestBody RQPostReview request
    ) {
        RSPPostReview response = reviewService.addReview(pId, request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RSPReviewDetails>> getProductReviews(@PathVariable int pId) {
        List<RSPReviewDetails> response = reviewService.getProductReviews(pId);
        return response.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @DeleteMapping("/{rId}")
    public void deleteReview(@PathVariable int rId, @PathVariable String pId) { reviewService.deleteReview(rId); }
}
