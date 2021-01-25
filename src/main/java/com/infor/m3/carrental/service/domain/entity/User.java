package com.infor.m3.carrental.service.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This entity represents a specific user
 *
 */
@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(name = "uc_email", columnNames = { "Email" }))
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class User {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", insertable = false, updatable = false, nullable = false)
	private Long id;
	
	@Getter
	@NotNull
	@Column(name = "Created", nullable = false)
	private LocalDateTime created = LocalDateTime.now();

	@Getter
	@NotNull
	@NonNull
	@Length(min = 1, max = 255)
	@Column(name = "Name", nullable = false)
	private String name;

	@Getter
	@NotNull
	@NonNull
	@Length(min = 1, max = 255)
	@Column(name = "Email", nullable = false)
	private String email;
	
	@Getter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
