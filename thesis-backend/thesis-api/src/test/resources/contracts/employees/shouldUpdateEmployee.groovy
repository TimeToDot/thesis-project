package contracts.employees

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("When a PUT request with a Employee is made, the updated employees's ID is returned")
    request {
        method 'PUT'
        url '/employees'
        body(
            id: "1",
            firstName: "Domyslaw-mod@email.com",
            lastName: "Domyśliciel",
            middleName: null,
            email: "mod@email.com",
            password: "",
            idCardNumber: null,
            pesel: "456456456456",
            sex: "male",
            position: [
                id: "d7c98cd5-7f64-4d60-aa2b-89dd8f89dbfe",
                name: "MANAGER_II"
            ],
            employmentDate: null,
            exitDate: null,
            imagePath: "ble",
            birthDate: null,
            birthPlace: null,
            phoneNumber: "123123123",
            city: "Domyslicemod@email.com",
            street: "Domyslnamod@email.com",
            houseNumber: "asdas",
            apartmentNumber: "fqeqw",
            postalCode: "12-123",
            country: null,
            privateEmail: "siema@email.com",
            accountNumber: "123123123123123123",
            contractType: "Commission contract",
            workingTime: null,
            wage: null,
            payday: null,
            active: true
        )
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
                id: 42
        )
        headers {
            contentType(applicationJson())
        }
    }
}
