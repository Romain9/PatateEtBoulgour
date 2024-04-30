package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    public Activity findById(long id);

    @Transactional
    @Modifying
    default void addUser(User user, Activity activity){
        activity.addUser(user);
        save(activity);
    }

    public List<Activity> findAllByOrderByLabelDesc(Pageable page);
    public List<Activity> findAllByOrderByLabelDesc();

}
