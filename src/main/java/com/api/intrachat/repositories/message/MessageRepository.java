package com.api.intrachat.repositories.message;

import com.api.intrachat.models.message.Message;
import com.api.intrachat.repositories.message.projections.MessageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

public interface MessageRepository extends JpaRepository<Message, Long> {

    /*
    @Query(value = """
            SELECT msg.id_message AS idMessage,
                   msg.message_type AS messageType,
                   msg.date_send AS dateSend,
                   msg.fk_id_user AS idUserSender,
                   COALESCE(txt.id_message_text, fl.id_message_file) AS idTypeMessage
            FROM messages msg
            INNER JOIN individual_messages imsg ON msg.id_message = imsg.id_message
            LEFT JOIN message_texts txt ON msg.id_message = txt.fk_id_message
            LEFT JOIN message_files fl ON msg.id_message = fl.fk_id_message
            WHERE imsg.id_individual = :idIndividual
            ORDER BY msg.date_send DESC
            """, nativeQuery = true)
    Page<MessageProjection> getPreviosChatsByIdIndividual(Long idIndividual, Pageable pageable);
    */
}
