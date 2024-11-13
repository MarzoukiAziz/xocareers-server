package com.xoauto.xocareers.service.interfaces;

import com.xoauto.xocareers.model.JobOffer;
import com.xoauto.xocareers.model.TypeJobType;

import java.util.List;

public interface IJobOfferService {
    List<JobOffer> getAllJobOffers();
    List<JobOffer> getActiveJobOffers();
    List<JobOffer> getJobOffersByType(TypeJobType type, boolean active);
    List<JobOffer> searchJobOffers(String search, TypeJobType type, boolean active);
    JobOffer getJobOfferById(long id);
    JobOffer addJobOffer(JobOffer jobOffer, long recruiterId);
    JobOffer updateJobOffer(JobOffer jobOffer, long recruiterId);
    void deleteJobOffer(long id);
}
