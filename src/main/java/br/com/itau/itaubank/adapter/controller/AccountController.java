package br.com.itau.itaubank.adapter.controller;


import br.com.itau.itaubank.adapter.presentation.AccountRequest;
import br.com.itau.itaubank.adapter.presentation.AccountResponse;
import br.com.itau.itaubank.application.ports.in.CreateAccountUseCase;
import br.com.itau.itaubank.application.ports.in.FindByCustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final FindByCustomerUseCase findByCustomerUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    @Operation(summary = "Cadastra uma conta para um cliente cadastrado", description = "Retorna a conta criada e associada a um cliente no Body")
    public AccountResponse create( @Valid @RequestBody AccountRequest accountRequest) {
        var customer = findByCustomerUseCase.execute(accountRequest.getIdCustomer());
        var domain = accountRequest.toUserDomain();
        return AccountResponse.fromDomain(
                createAccountUseCase.execute(customer.get().getId(), domain));
    }

    @Autowired
    public AccountController(CreateAccountUseCase createAccountUseCase,
                             FindByCustomerUseCase findByCustomerUseCase
                             ){
        this.createAccountUseCase = createAccountUseCase;
        this.findByCustomerUseCase = findByCustomerUseCase;
    }
}
