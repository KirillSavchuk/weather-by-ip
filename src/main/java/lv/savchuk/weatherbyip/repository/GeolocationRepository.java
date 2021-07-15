package lv.savchuk.weatherbyip.repository;

import lv.savchuk.weatherbyip.model.dao.Geolocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeolocationRepository extends CrudRepository<Geolocation, UUID> {

}