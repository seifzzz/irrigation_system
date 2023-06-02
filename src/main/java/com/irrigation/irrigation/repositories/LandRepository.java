package repositories;

import land.land;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRepository extends CrudRepository<land,String>  {

}
