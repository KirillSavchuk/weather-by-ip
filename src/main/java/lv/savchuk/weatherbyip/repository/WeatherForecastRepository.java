package lv.savchuk.weatherbyip.repository;

import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeatherForecastRepository extends CrudRepository<WeatherForecast, UUID> {

	@Query("SELECT wf FROM WeatherForecast wf " +
		"WHERE wf.geolocation = :geolocation " +
		"AND wf.createdOn >= :expiredTimestamp " +
		"ORDER BY wf.createdOn DESC")
	Optional<WeatherForecast> findOneBeforeByGeolocation(
		@Param("geolocation") Geolocation geolocation,
		@Param("expiredTimestamp") Timestamp expiredTimestamp
	);

}