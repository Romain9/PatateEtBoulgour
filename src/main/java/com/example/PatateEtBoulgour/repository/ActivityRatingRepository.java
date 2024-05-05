package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.ActivityRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRatingRepository extends JpaRepository<ActivityRating, Long> {

    public Optional<ActivityRating> findActivityRatingByActivity_IdAndUser_Id(Long activity_id, Long user_id);

    @Query("SELECT AVG(ar.rating) as rating, ar.activity, ar.user, ar.id FROM ActivityRating ar GROUP BY ar.activity")
    public List<ActivityRating> findRatedActivityRating();

}
