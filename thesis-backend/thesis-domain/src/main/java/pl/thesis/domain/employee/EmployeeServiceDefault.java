package pl.thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.*;
import pl.thesis.data.account.model.*;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.position.model.Position;
import pl.thesis.domain.employee.mapper.EmployeeDTOMapper;
import pl.thesis.domain.employee.model.EmployeeDTO;
import pl.thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import pl.thesis.domain.employee.model.EmployeesDTO;
import pl.thesis.domain.employee.model.PasswordUpdatePayloadDTO;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@AllArgsConstructor
@Service
public class EmployeeServiceDefault implements EmployeeService{

    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final PositionRepository positionRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SexRepository sexRepository;
    private final CountryRepository countryRepository;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @PostConstruct
    private void init(){
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    @Transactional
    public EmployeeDTO getEmployee(Long id) {
        var account = accountRepository.findById(id).orElseThrow();
        var details = accountDetailsRepository.findByAccount(account).orElseThrow();
        Position position = positionRepository.findByAccountsEquals(account).orElse(null);

        details.getAccount().setPosition(position);

        return employeeDTOMapper.map(details);
    }

    @Override
    public EmployeesDTO getEmployees(PagingSettings settings, Boolean active) {
        var status = Boolean.TRUE.equals(active) ? StatusType.ENABLE : StatusType.EXPIRED;

        var employees = accountRepository.findAllByStatus(status, settings.getPageable());
        var paging = PagingHelper.getPaging(settings, employees);
        var sorting = PagingHelper.getSorting(settings);

        return EmployeesDTO.builder()
                .employees(
                        employees
                                .map(Account::getDetails)
                                .map(employeeDTOMapper::map)
                                .toList()
                )
                .paging(paging)
                .sorting(sorting)
                .build();
    }


    @Override
    @Transactional
    public Long updateEmployee(EmployeeUpdatePayloadDTO payloadDTO) throws ParseException {
        var account = accountRepository.findById(payloadDTO.id()).orElseThrow();
        var accountDetails = accountDetailsRepository.findByAccount(account).orElseThrow();
        var sex = sexRepository.findByName(payloadDTO.sex().name()).orElseThrow();
        var country = getCountry(payloadDTO);
        var contractType = contractTypeRepository.findByName(payloadDTO.contractType().name()).orElseThrow();

        setAccountFields(payloadDTO, account);
        setAccountDetailsFields(payloadDTO, accountDetails, sex, country, contractType);
        //var accountDetails = employeeDTOMapper.mapToDetails(payloadDTO); //check mappers fith instance

        accountRepository.save(account);
        accountDetailsRepository.save(accountDetails);

        return account.getId();
    }

    @Override
    @Transactional
    public Long updateEmployeePassword(PasswordUpdatePayloadDTO payloadDTO){
        var account = accountRepository.findById(payloadDTO.id()).orElseThrow();

        account.setPass(passwordEncoder.encode(payloadDTO.password()));

        accountRepository.save(account);

        return account.getId();
    }

    private Country getCountry(EmployeeUpdatePayloadDTO payloadDTO) {
        return countryRepository
                .findByName(payloadDTO.country().name())
                .orElseGet(() -> {
                    var c = Country.builder()
                            .id(payloadDTO.country().id())
                            .name(payloadDTO.country().name())
                            .build();

                    countryRepository.save(c);

                    return c;
                });
    }
    private void setAccountDetailsFields(EmployeeUpdatePayloadDTO payloadDTO, AccountDetails accountDetails, Sex sex, Country country, ContractType contractType) throws ParseException {
        accountDetails.setName(payloadDTO.firstName());
        accountDetails.setSurname(payloadDTO.lastName());
        accountDetails.setMiddleName(payloadDTO.middleName());
        accountDetails.setSex(sex);
        accountDetails.setBirthDate(formatDate(payloadDTO.birthDate()));
        accountDetails.setBirthPlace(payloadDTO.birthPlace());
        accountDetails.setEmploymentDate(formatDate(payloadDTO.employmentDate()));
        accountDetails.setExitDate(formatDate(payloadDTO.exitDate()));
        accountDetails.setApartmentNumber(payloadDTO.apartmentNumber());
        accountDetails.setHouseNumber(payloadDTO.houseNumber());
        accountDetails.setStreet(payloadDTO.street());
        accountDetails.setCity(payloadDTO.city());
        accountDetails.setPostalCode(payloadDTO.postalCode());
        accountDetails.setCountry(country);
        accountDetails.setPesel(payloadDTO.pesel());
        accountDetails.setTaxNumber(payloadDTO.accountNumber());
        accountDetails.setIdCardNumber(payloadDTO.idCardNumber());
        accountDetails.setPhoneNumber(payloadDTO.phoneNumber());
        accountDetails.setPrivateEmail(payloadDTO.privateEmail());
        accountDetails.setWorkingTime(payloadDTO.workingTime());
        accountDetails.setWage(payloadDTO.wage());
        accountDetails.setPayday(payloadDTO.payday());
        accountDetails.setContractType(contractType);
    }

    private Date formatDate(Date date) throws ParseException {
        if (date != null) {
            return format.parse(format.format(date));
        }
        return null;
    }

    private void setAccountFields(EmployeeUpdatePayloadDTO payloadDTO, Account account) {
        if(payloadDTO.active() != null){
            if (Boolean.TRUE.equals(payloadDTO.active())) {
                account.setStatus(StatusType.ENABLE);
            }
            else {
                account.setStatus(StatusType.EXPIRED);
            }
        }
        if (payloadDTO.position().id() != null){
            var position = positionRepository.findById(payloadDTO.position().id()).orElseThrow();
            account.setPosition(position);
        }
        if (payloadDTO.email() != null){
            account.setEmail(payloadDTO.email());
        }
    }
}