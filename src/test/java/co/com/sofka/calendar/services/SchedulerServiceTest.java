package co.com.sofka.calendar.services;

import co.com.sofka.calendar.collections.CourseTime;
import co.com.sofka.calendar.collections.Program;
import co.com.sofka.calendar.collections.Time;
import co.com.sofka.calendar.model.ProgramDate;
import co.com.sofka.calendar.repositories.ProgramDateRepository;
import co.com.sofka.calendar.repositories.ProgramRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @InjectMocks
    SchedulerService schedulerService;


    @Mock
    ProgramRepository repository;


    @Test
        //TODO: modificar el test para que el act sea reactivo, usando stepverifier
    void generateCalendar() {
        var programId = "xxxx";
        var startDate = LocalDate.of(2022, 01, 01);
        Program program = getProgramDummy();
        Mockito.when(repository.findById(programId)).thenReturn(Mono.just(program));
        Flux<ProgramDate> response = schedulerService.generateCalendar(programId, startDate);


        StepVerifier.create(response)
                .expectNextMatches(programDate ->
                        programDate.getDate().toString().equals("2022-01-03")
                                && programDate.getCategoryName().equals("Principios")
                ).expectNextMatches(programDate ->
                        programDate.getDate().toString().equals("2022-01-04")
                                && programDate.getCategoryName().equals("Bases")
                ).expectNextMatches(programDate ->
                        programDate.getDate().toString().equals("2022-01-05")
                                && programDate.getCategoryName().equals("Bases")
                ).expectNextMatches(programDate ->
                        programDate.getDate().toString().equals("2022-01-06")
                                && programDate.getCategoryName().equals("Fundamentos")
                ).expectNextMatches(programDate ->
                        programDate.getDate().toString().equals("2022-01-07")
                                && programDate.getCategoryName().equals("Fundamentos avanzados")

                ).verifyComplete();

    }

    @Test
    void programNoFound() {
        var programId = "sdfd";
        var startDate = LocalDate.of(2022, 1, 1);

        Mockito.when(repository.findById(programId)).thenReturn(Mono.error(new RuntimeException("El programa academnico no existe")));
        //TODO: hacer de otro modo
        Flux<ProgramDate> response = schedulerService.generateCalendar(programId, startDate);
        //TODO: hacer una subscripción de el servicio reactivo
        response.subscribe();
        StepVerifier.create(response)
                .expectErrorMessage("El programa academnico no existe")
                .verify();

        Mockito.verify(repository).findById(programId);

    }

    //no tocar
    private Program getProgramDummy() {
        var program = new Program();
        program.setId("xxxx");
        program.setName("Programa 2022");
        program.setCourses(new ArrayList<>());
        var timesForCourse1 = new ArrayList<Time>();
        timesForCourse1.add(new Time("1", 2, "Principios", List.of()));
        timesForCourse1.add(new Time("2", 2, "Bases", List.of()));
        timesForCourse1.add(new Time("3", 4, "Fundamentos", List.of()));
        timesForCourse1.add(new Time("3", 5, "Fundamentos avazandos", List.of()));

        program.getCourses().add(new CourseTime("xxx-z", "Introducción", timesForCourse1));
        return program;
    }

    //no tocar
    private String getSnapResult() {
        return "[{\"categoryName\":\"Principios\",\"date\":{\"year\":2022,\"month\":1,\"day\":3}},{\"categoryName\":\"Principios\",\"date\":{\"year\":2022,\"month\":1,\"day\":4}},{\"categoryName\":\"Bases\",\"date\":{\"year\":2022,\"month\":1,\"day\":5}},{\"categoryName\":\"Bases\",\"date\":{\"year\":2022,\"month\":1,\"day\":6}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":7}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":10}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":11}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":12}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":13}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":14}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":17}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":18}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":19}}]";
    }


}