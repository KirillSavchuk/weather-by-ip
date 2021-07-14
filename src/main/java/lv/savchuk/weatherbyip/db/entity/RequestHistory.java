package lv.savchuk.weatherbyip.db.entity;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Builder
@Table(name = "REQUEST_HISTORY", indexes = {
	@Index(name = "IDX_REQUEST_HISTORY", columnList = "IP_ADDRESS,COUNTRY,CITY")
})
public class RequestHistory {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private final UUID id;

	@Column(name = "IP_ADDRESS", length = 15)
	private final String ipAddress;

	@Column(name = "COUNTRY", length = 100)
	private final String country;

	@Column(name = "CITY")
	private final String city;

	@Lob
	@Column(name = "WEATHER")
	private final String weather;

	@CreationTimestamp
	@Temporal(TIMESTAMP)
	@Column(name = "CREATED_ON")
	private final Date createdOn;

}