package com.irrigation.irrigation.repositories;

import com.irrigation.irrigation.model.slot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends CrudRepository<slot,Long> {

}
