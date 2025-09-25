package com.api.intrachat.repositories.mode;

import com.api.intrachat.models.mode.IndividualUser;
import com.api.intrachat.repositories.mode.projections.IndividualUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface IndividualUserRepository extends JpaRepository<IndividualUser, Long> {

    @Query(value = """
            SELECT ihu1.fk_id_user AS idUserOne,
                   ihu2.fk_id_user AS idUserTwo,
                   ihu1.fk_id_individual AS idIndividual
            FROM individual_has_users ihu1
            JOIN individual_has_users ihu2 ON ihu1.fk_id_individual = ihu2.fk_id_individual
            WHERE ihu1.fk_id_user = :idUserOneVar
            AND ihu2.fk_id_user = :idUserTwoVar
            """, nativeQuery = true)
    Optional<IndividualUserProjection> validateIndividualChatExisting(@Param("idUserOneVar") Long idUserOneVar,
                                                                      @Param("idUserTwoVar") Long idUserTwoVar);

}
