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
@Table(name = "WEATHER_FORECAST")
public class WeatherForecast implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private UUID id;

	@CreationTimestamp
	@Temporal(TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "TITLE", length = 100)
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "WIND_SPEED", precision = 2, scale = 2)
	private float windSpeed;

	@Column(name = "TEMP_ACTUAL", precision = 2, scale = 2)
	private float tempActual;

	@Column(name = "TEMP_MIN", precision = 2, scale = 2)
	private float tempMin;

	@Column(name = "TEMP_MAX", precision = 2, scale = 2)
	private float tempMax;

	@Setter
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "GEOLOCATION_FK")
	private Geolocation geolocation;

}