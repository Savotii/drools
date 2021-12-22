package com.spirent.drools.model.alert;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Entity
@Table(name = "failed_kpis")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class FailedKpiModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -3310230806139031559L;

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private long latency;
    @Column
    private long threshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_id")
    private AlertModel alert;
}
