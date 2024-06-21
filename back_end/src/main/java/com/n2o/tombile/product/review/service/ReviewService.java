package com.n2o.tombile.product.review.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.product.product.model.Product;
import com.n2o.tombile.product.product.repository.ProductRepository;
import com.n2o.tombile.product.review.dto.RQPostReview;
import com.n2o.tombile.product.review.dto.RSPPostReview;
import com.n2o.tombile.product.review.dto.RSPReviewDetails;
import com.n2o.tombile.product.review.model.Review;
import com.n2o.tombile.product.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PRODUCT_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.ERROR_REVIEW_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ProductRepository<Product> productRepository;
    private final ReviewRepository reviewRepository;

    public RSPPostReview addReview(int pId, RQPostReview request) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_PRODUCT_NOT_FOUND));
        Review review = Util.cloneObject(request, Review.class);
        review.setReviewDate(Instant.now());
        review.setUser(Util.getCurrentUser());
        review.setProduct(product);
        reviewRepository.save(review);
        return Util.cloneObject(review, RSPPostReview.class);
    }

    public List<RSPReviewDetails> getProductReviews(int productId) {
        List<Review> reviews = reviewRepository.findProductReviews(productId);
        List<RSPReviewDetails> responses = new ArrayList<>();
        reviews.forEach(review -> {
            RSPReviewDetails response = Util.cloneObject(review, RSPReviewDetails.class);
            response.setUserFirstName(review.getUser().getUserData().getFirstName());
            response.setUserLastName(review.getUser().getUserData().getLastName());
            responses.add(response);
        });
        return responses;
    }

    public void deleteReview(int reviewId) {
        Review review = reviewRepository
                .findUserReview(reviewId, Util.getCurrentUserId())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
    }

}
