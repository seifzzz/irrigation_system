package com.irrigation.irrigation.repositories;

import com.irrigation.irrigation.model.land;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRepository extends CrudRepository<land,Long>  {

}
