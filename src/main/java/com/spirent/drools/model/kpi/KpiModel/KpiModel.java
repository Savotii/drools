package com.spirent.drools.model.kpi.KpiModel;

import com.spirent.drools.dto.kpi.KpiPhase;
import com.spirent.drools.model.LocationModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ysavi2
 * @since 06.12.2021
 */
@Entity
@Table(name = "kpis")
@Setter
@Getter
public class KpiModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private KpiPhase phase;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationModel location;

    @Column
    private boolean failed;

    @Column
    private long timestamp;

}
