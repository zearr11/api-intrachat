package com.api.intrachat.models.agrupation;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCampaign;
    private String campaignName;

    @ManyToMany
    @JoinTable(
            name = "campaign_has_areas",
            joinColumns = @JoinColumn(name = "id_campaign"),
            inverseJoinColumns = @JoinColumn(name = "id_area")
    )
    private Set<Area> campaignAreas = new HashSet<>();

    public void addCampaignAreas(Area area) {
        campaignAreas.add(area);
    }

}
