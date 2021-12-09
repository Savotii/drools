package com.spirent.drools.model;

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
@NoArgsConstructor
@Accessors(chain = true)
public class LocationModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String code;
}
