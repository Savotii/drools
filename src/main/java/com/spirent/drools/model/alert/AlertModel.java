package com.spirent.drools.model.alert;

import com.spirent.drools.dto.alert.AlertCategory;
import com.spirent.drools.dto.alert.AlertLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Entity
@Table(name = "alerts")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class AlertModel {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "agent_id")
    private String agentId;
    @Column(name = "agent_test_name")
    private String agentTestName;
    @Column(name = "test_session_id")
    private String testSessionId;
    @Column(name = "test_id")
    private String testId;
    @Column(name = "agent_test_id")
    private String agentTestId;
    @Column(name = "workflow_id")
    private String workflowId;
    @Column(name = "overlay_id")
    private String overlayId;
    @Column(name = "network_element_id")
    private String networkElementId;
    @Enumerated(EnumType.ORDINAL)
    private AlertCategory category;
    @Column(name = "package")
    private String pkg;
    @Column(name = "test_name")
    private String testName;
    @Column
    private Instant timestamp;
    @Column(name = "alert_id")
    private String alertId;
    @Column
    private String alertName;
    @Enumerated(EnumType.ORDINAL)
    private AlertLevel level;

    @OneToMany(mappedBy = "alert", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval = true)
//    @JoinColumn(name = "alert_id", referencedColumnName = "id")
    private List<FailedKpiModel> failedKpis;
}
