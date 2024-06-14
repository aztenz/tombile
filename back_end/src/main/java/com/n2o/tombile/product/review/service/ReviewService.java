package com.n2o.tombile.product.review.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.product.product.model.Product;
import com.n2o.tombile.product.product.repository.ProductRepository;
import com.n2o.tombile.product.review.dto.PostReviewRQ;
import com.n2o.tombile.product.review.dto.PostReviewRSP;
import com.n2o.tombile.product.review.dto.ReviewDetails;
import com.n2o.tombile.product.review.model.Review;
import com.n2o.tombile.product.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PRODUCT_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.ERROR_REVIEW_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ProductRepository<Product> productRepository;
    private final ReviewRepository reviewRepository;

    public PostReviewRSP addReview(int pId, PostReviewRQ request) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_PRODUCT_NOT_FOUND));
        Review review = Util.cloneObject(request, Review.class);
        review.setReviewDate(new Date());
        review.setUser(Util.getCurrentUser());
        review.setProduct(product);
        reviewRepository.save(review);
        return Util.cloneObject(review, PostReviewRSP.class);
    }

    public List<ReviewDetails> getProductReviews(int productId) {
        List<Review> reviews = reviewRepository.findProductReviews(productId);
        List<ReviewDetails> reviewDetails = new ArrayList<>();
        reviews.forEach(review -> {
            ReviewDetails reviewDetail = Util.cloneObject(review, ReviewDetails.class);
            reviewDetail.setUserFirstName(review.getUser().getUserData().getFirstName());
            reviewDetail.setUserLastName(review.getUser().getUserData().getLastName());
            reviewDetails.add(reviewDetail);
        });
        return reviewDetails;
    }

    public void deleteReview(int reviewId) {
        Review review = reviewRepository
                .findUserReview(reviewId, Util.getCurrentUserId())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
    }

}
