package com.spirent.drools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spirent.drools.model.kpi.filter.KpiThresholdFilterModel;
import org.springframework.stereotype.Repository;

/**
 * @author ysavi2
 * @since 22.12.2021
 */
@Repository
public interface KpiThresholdFilterRepository extends JpaRepository<KpiThresholdFilterModel, Long> {
}
