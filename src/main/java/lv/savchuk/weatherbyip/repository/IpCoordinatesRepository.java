package lv.savchuk.weatherbyip.repository;

import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IpCoordinatesRepository extends CrudRepository<IpCoordinates, UUID> {

	Optional<IpCoordinates> findByIpAddress(@Param("ipAddress") String ipAddress);

}