package lv.savchuk.weatherbyip.model.dao;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GEOLOCATION")
public class Geolocation implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private UUID id;

	@CreationTimestamp
	@Temporal(TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "CITY")
	private String city;

}