package com.spirent.drools.repository;

import com.spirent.drools.model.kpi.KpiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ysavi2
 * @since 06.12.2021
 */
@Repository
public interface KpiRepository extends JpaRepository<KpiModel, Long> {
}
