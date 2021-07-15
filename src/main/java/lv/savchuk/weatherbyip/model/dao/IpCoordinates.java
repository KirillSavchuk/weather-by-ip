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
@Table(name = "IP_COORDINATES", indexes = {
	@Index(name = "IP_COORDINATES_IP_ADDRESS", columnList = "IP_ADDRESS")
})
public class IpCoordinates implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private UUID id;

	@CreationTimestamp
	@Temporal(TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Setter
	@Column(name = "IP_ADDRESS", length = 15)
	private String ipAddress;

	@Column(name = "LATITUDE", precision = 200, scale = 100)
	private float latitude;

	@Column(name = "LONGITUDE", precision = 200, scale = 100)
	private float longitude;

	@ManyToOne(optional = false)
	@JoinColumn(name = "GEO_LOCATION_FK")
	private Geolocation geolocation;

}