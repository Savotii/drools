package com.spirent.drools.model.rule;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Table(name = "rules")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class RuleModel implements Serializable {
	@Serial
	private static final long serialVersionUID = 6167361538548193796L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false, unique = true)
	private String ruleKey;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false, unique = true)
	private String version;
	@Column(nullable = true, unique = true)
	private String lastModifyTime;
	@Column(nullable = false)
	private String createTime;
}