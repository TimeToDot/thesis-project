package pl.thesis.api.employee;

import pl.thesis.api.employee.model.EmployeeResponse;
import pl.thesis.api.employee.model.EmployeesResponse;
import pl.thesis.domain.employee.model.EmployeeDTO;
import pl.thesis.domain.employee.model.EmployeeProjectDetailsDTO;
import pl.thesis.domain.employee.model.EmployeesDTO;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

public record EmployeesFactory() {

    EmployeesDTO getDummyEmployeesDTO(){
        List<EmployeeDTO> employeesList = List.of(
                createDummyEmployeeDTOWithId(1),
                createDummyEmployeeDTOWithId(2),
                createDummyEmployeeDTOWithId(3)
        );

        var paging = new Paging(1, 10L, 5);
        var sorting = new Sorting("fieldName", "ASC");

        return new EmployeesDTO(employeesList, paging, sorting);
    }

    EmployeesResponse getDummyEmployeesResponse(){
        List<EmployeeResponse> employeesList = List.of(
                createDummyEmployeeResponseWithId("123"),
                createDummyEmployeeResponseWithId("1234"),
                createDummyEmployeeResponseWithId("123345")
        );

        var paging = new Paging(1, 10L, 5);
        var sorting = new Sorting("fieldName", "ASC");

        return new EmployeesResponse(employeesList, paging, sorting);
    }

    EmployeeProjectDetailsDTO getDummyEmployeeProjectDetailsDTO(Long id){
        return new EmployeeProjectDetailsDTO(id,
                "Project Name",
                "Project Description",
                null,
                null,
                null,
                true);
    }

    EmployeeDTO createDummyEmployeeDTOWithId(long id){
        return new EmployeeDTO(id,
                "firstName",
                "lastName",
                "middleName",
                "email",
                "password",
                "idCardNumber",
                "pesel",
                null,
                null,
                null,
                null,
                "imagePath",
                "birthDate",
                "birthPlace",
                "phoneNumber",
                "city",
                "street",
                "houseNumber",
                "apartmentNumber",
                "postalCode",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );
    }

    EmployeeResponse createDummyEmployeeResponseWithId(String id){
        return new EmployeeResponse(id,
                "firstName",
                "lastName",
                "middleName",
                "email",
                "password",
                "idCardNumber",
                "pesel",
                null,
                null,
                null,
                null,
                "imagePath",
                "birthDate",
                "birthPlace",
                "phoneNumber",
                "city",
                "street",
                "houseNumber",
                "apartmentNumber",
                "postalCode",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );
    }

}
