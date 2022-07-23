package co.com.sofka.calendar.repositories;

import co.com.sofka.calendar.collections.Program;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends ReactiveMongoRepository<Program, String> {

}