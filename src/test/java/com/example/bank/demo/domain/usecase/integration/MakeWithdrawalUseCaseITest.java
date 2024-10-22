package com.example.bank.demo.domain.usecase.integration;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.persistance.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.persistance.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.utils.DateProvider;
import com.example.bank.demo.infrastructure.utils.BaseIntegTest;
import com.example.bank.demo.infrastructure.utils.TransactionalTestingService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOSIT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.WITHDRAWAL;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MakeWithdrawalUseCaseITest extends BaseIntegTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DateProvider dateProvider;

    @Autowired
    private OperationRepositoryPort operationRepositoryPort;

    @Autowired
    private BankAccountRepositoryPort bankAccountRepositoryPort;

    @Autowired
    private TransactionalTestingService transactionalTestingService;

    @Value("classpath:/controllerTest/response/responseForMakingWithdrawal.json")
    private Resource responseForMakingWithdrawal;

    @Value("classpath:/controllerTest/response/responseForWithdrawalAmountBiggerThanBalanceException.json")
    private Resource responseForWithdrawalAmountBiggerThanBalanceException;

    @Test
    void shouldSuccedMakingAWithdrawal() throws Throwable {
        BankAccount account = saveDataForBankAccount("250.00");

        JSONObject requestForMakingWithdrawalJson = new JSONObject();
        requestForMakingWithdrawalJson.put("accountId", account.getAccountId());
        requestForMakingWithdrawalJson.put("withdrawalAmount", 150.00);

        BankAccount accountBeforeWithdrawal = new BankAccount(account.getAccountId(), UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("250.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));
        BankAccount accountAfterWithdrawal = new BankAccount(account.getAccountId(), UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        Operation expectedOperation1 = new Operation(null, accountBeforeWithdrawal, DEPOSIT, accountBeforeWithdrawal.getBalance(), accountBeforeWithdrawal.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(null, accountAfterWithdrawal, WITHDRAWAL, new BigDecimal("150.00"), accountAfterWithdrawal.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));

        LocalDateTime expectedDate2 = LocalDateTime.of(2024, 5, 14, 16, 24, 30);

        when(dateProvider.getCurrentDate()).thenReturn(expectedDate2);


        mockMvc.perform(post("/bank/services/withdrawal")
                        .contentType(APPLICATION_JSON)
                        .content(requestForMakingWithdrawalJson.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(responseForMakingWithdrawal.getContentAsString(UTF_8), true));

        List<Operation> operations = operationRepositoryPort.findAll();


        assertThat(operations)
                .isNotNull()
                .hasSize(2)
                .anySatisfy(operation -> assertThat(operation).usingRecursiveComparison().ignoringFields("id", "accountId").isEqualTo(expectedOperation1))
                .anySatisfy(operation -> assertThat(operation).usingRecursiveComparison().ignoringFields("id", "accountId").isEqualTo(expectedOperation2))
                .map(operation -> operation.getAccountId().getAccountId())
                .contains(account.getAccountId());
    }

    @Test
    void shouldFailMakingAWithdrawalOnNormalAccount() throws Throwable {

        BankAccount account = saveDataForBankAccount("250.00");

        String requestForMakingWithdrawalForFail = new JSONObject()
                .put("accountId", account.getAccountId())
                .put("withdrawalAmount", 500.00)
                .toString();

        BankAccount accountBeforeWithdrawal = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("250.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));
        Operation expectedOperation1 = new Operation(null, accountBeforeWithdrawal, DEPOSIT, accountBeforeWithdrawal.getBalance(), accountBeforeWithdrawal.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));

        mockMvc.perform(post("/bank/services/withdrawal")
                        .contentType(APPLICATION_JSON)
                        .content(requestForMakingWithdrawalForFail))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(responseForWithdrawalAmountBiggerThanBalanceException.getContentAsString(UTF_8), true));

        List<Operation> operations = operationRepositoryPort.findAll();


        assertThat(operations)
                .isNotNull()
                .hasSize(1)
                .anySatisfy(operation -> assertThat(operation).usingRecursiveComparison().ignoringFields("id", "accountId").isEqualTo(expectedOperation1))
                .map(operation -> operation.getAccountId().getAccountId())
                .contains(account.getAccountId());
    }

    private BankAccount saveDataForBankAccount(final String balance) throws Throwable {
        BankAccount bankAccount = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal(balance), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal(0));
        Operation operation = new Operation(1L, bankAccount, DEPOSIT, bankAccount.getBalance(), bankAccount.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        BankAccount savedAccount = bankAccountRepositoryPort.saveBankAccount(bankAccount);

        operation.setAccountId(savedAccount);
        operationRepositoryPort.saveOperation(operation);
        return savedAccount;
    }
}
