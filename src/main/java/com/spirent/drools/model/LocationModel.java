package com.spirent.drools.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ysavi2
 * @since 03.12.2021
 */
@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class LocationModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long latitude;

    @Column
    private long longitude;
}
