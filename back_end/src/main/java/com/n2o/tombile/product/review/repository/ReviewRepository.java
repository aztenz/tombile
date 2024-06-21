package com.n2o.tombile.product.review.repository;

import com.n2o.tombile.product.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r from Review r WHERE r.product.id = :productId")
    List<Review> findProductReviews(int productId);

    @Query("SELECT r from Review r WHERE r.id = :rId AND r.user.id = :uId")
    Optional<Review> findUserReview(int rId, int uId);
}
