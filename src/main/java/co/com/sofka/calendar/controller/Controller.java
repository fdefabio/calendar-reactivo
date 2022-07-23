package co.com.sofka.calendar.controller;


import co.com.sofka.calendar.collections.Program;
import co.com.sofka.calendar.model.ProgramDate;
import co.com.sofka.calendar.repositories.ProgramDateRepository;
import co.com.sofka.calendar.repositories.ProgramRepository;
import co.com.sofka.calendar.services.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class Controller {



    @Autowired
    ProgramDateRepository programRepository;

    @Autowired
    ProgramRepository repository;


    @PostMapping("/saveProgramDate/{id}/{date}")
    Mono<ProgramDate> saveProgramDate(@RequestBody ProgramDate programDate){
        return programRepository.save(programDate);
    }
    @PostMapping("/save")
    Mono<Program> saveProgram(@RequestBody Program program){
        return repository.save(program);
    }

    @GetMapping("/getAll")
    Flux<Program> getAll(){
        return repository.findAll();
    }

    @GetMapping("/get/{id}")
    Mono<Program> getProgram(@PathVariable String id){
        return repository.findById(id);
    }
}
