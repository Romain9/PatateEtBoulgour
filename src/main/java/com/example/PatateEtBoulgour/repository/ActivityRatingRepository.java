package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.ActivityRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRatingRepository extends JpaRepository<ActivityRating, Long> {

    public Optional<ActivityRating> findActivityRatingByActivity_IdAndUser_Id(Long activity_id, Long user_id);

}
