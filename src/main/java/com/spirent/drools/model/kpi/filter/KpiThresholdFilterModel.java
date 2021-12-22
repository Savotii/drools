package com.spirent.drools.model.kpi.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author ysavi2
 * @since 22.12.2021
 */
@Entity
@Table(name = "kpi_threshold")
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class KpiThresholdFilterModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 6147841877633169884L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String testId;

    @Column
    private String overlayId;

    @Column
    private Long value;
}
