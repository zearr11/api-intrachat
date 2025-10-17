package com.api.intrachat.repositories.agrupation;

import com.api.intrachat.models.agrupation.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
