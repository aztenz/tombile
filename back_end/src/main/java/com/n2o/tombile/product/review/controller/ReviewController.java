package com.n2o.tombile.product.review.controller;

import com.n2o.tombile.product.review.dto.PostReviewRQ;
import com.n2o.tombile.product.review.dto.PostReviewRSP;
import com.n2o.tombile.product.review.dto.ReviewDetails;
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
    public ResponseEntity<PostReviewRSP> addReview(
            @PathVariable int pId,
            @Valid @RequestBody PostReviewRQ request
    ) {
        PostReviewRSP postReviewRSP = reviewService.addReview(pId, request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postReviewRSP.getId())
                .toUri();

        return ResponseEntity.created(location).body(postReviewRSP);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDetails>> getProductReviews(@PathVariable int pId) {
        List<ReviewDetails> reviewDetails = reviewService.getProductReviews(pId);
        return reviewDetails.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(reviewDetails);
    }

    @DeleteMapping("/{rId}")
    public void deleteReview(@PathVariable int rId, @PathVariable String pId) { reviewService.deleteReview(rId); }
}
