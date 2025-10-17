package com.api.intrachat.models.agrupation;

import com.api.intrachat.models.message.RespondMessage;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "headquarters")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Headquarters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHeadquarters;
    private String headquarters;

    @ManyToMany
    @JoinTable(
            name = "headquarters_has_campaigns",
            joinColumns = @JoinColumn(name = "id_headquarters"),
            inverseJoinColumns = @JoinColumn(name = "id_campaign")
    )
    private Set<Campaign> headquartersCampaigns = new HashSet<>();

    public void addHeadquartersCampaigns(Campaign campaign) {
        headquartersCampaigns.add(campaign);
    }

}
