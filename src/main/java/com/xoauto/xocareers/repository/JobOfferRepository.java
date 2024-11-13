package com.xoauto.xocareers.repository;

import com.xoauto.xocareers.model.JobOffer;
import com.xoauto.xocareers.model.TypeJobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    @Query("Select jo From JobOffer jo where jo.active=true")
    List<JobOffer> findAllActive();

    @Query("Select jo from JobOffer jo where jo.active=:active and jo.jobType=:type")
    List<JobOffer> findAllByType(@Param("type") TypeJobType type, @Param("active") boolean active);

    @Query("SELECT jo FROM JobOffer jo WHERE (LOWER(jo.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(jo.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND jo.active = :active and jo.jobType=:type")
    List<JobOffer> searchJobOfferBy(String search, TypeJobType type, boolean active);

}
