package lv.savchuk.weatherbyip.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REQUEST_HISTORY")
public class RequestHistory {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private UUID id;

	@CreationTimestamp
	@Temporal(TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IP_COORDINATES_FK")
	private IpCoordinates ipCoordinates;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "WEATHER_FORECAST_FK")
	private WeatherForecast weatherForecast;

}