package com.n2o.tombile.service;

import com.n2o.tombile.dto.request.review.PostReviewRQ;
import com.n2o.tombile.dto.response.review.PostReviewRSP;
import com.n2o.tombile.dto.response.review.ReviewDetails;
import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.model.Product;
import com.n2o.tombile.model.Review;
import com.n2o.tombile.repository.ReviewRepository;
import com.n2o.tombile.repository.product.ProductRepository;
import com.n2o.tombile.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private static final String PRODUCT_NOT_FOUND = "Couldn't find a product with the given product id";
    private static final String REVIEW_NOT_FOUND = "Couldn't find a review for the given user";
    private final ProductRepository<Product> productRepository;
    private final ReviewRepository reviewRepository;

    public PostReviewRSP addReview(int pId, PostReviewRQ request) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new ItemNotFoundException(PRODUCT_NOT_FOUND));
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
                .orElseThrow(() -> new ItemNotFoundException(REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
    }

}
