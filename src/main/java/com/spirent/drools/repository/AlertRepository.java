package com.spirent.drools.repository;

import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.model.alert.AlertModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Repository
public interface AlertRepository extends JpaRepository<AlertModel, Long> {
}
