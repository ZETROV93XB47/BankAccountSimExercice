package com.example.bank.demo.domain.usecase.integration;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.out.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.out.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.ports.out.saving_account.SavingAccountRepositoryPort;
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
import static com.example.bank.demo.domain.model.enumpackage.AccountType.SAVING_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOSIT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MakeDepositUseCaseServiceITest extends BaseIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DateProvider dateProvider;

    @Autowired
    private BankAccountRepositoryPort bankAccountRepositoryPort;

    @Autowired
    private SavingAccountRepositoryPort savingAccountRepositoryPort;

    @Autowired
    private OperationRepositoryPort operationRepositoryPort;

    @Autowired
    private TransactionalTestingService transactionalTestingService;

    @Value("classpath:/controllerTest/response/responseForMakingDeposit.json")
    private Resource responseForMakingDeposit;

    @Value("classpath:/controllerTest/response/responseForDepositLimitExceededException.json")
    private Resource responseForDepositLimitExceededException;

    @Test
    void shouldSucceedMakingADeposit() throws Throwable {

        BankAccount account = saveDataForBankAccount("100.00");

        BankAccount accountBeforeDeposit = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));
        BankAccount accountAfterDeposit = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("250.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        Operation expectedOperation1 = new Operation(1L, accountBeforeDeposit, DEPOSIT, accountBeforeDeposit.getBalance(), accountBeforeDeposit.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(2L, accountAfterDeposit, DEPOSIT, new BigDecimal("150.00"), accountAfterDeposit.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));

        String requestForMakingDeposit = new JSONObject()
                .put("accountId", account.getAccountId())
                .put("depositAmount", 150.00)
                .toString();

        LocalDateTime expectedDate2 = LocalDateTime.of(2024, 5, 14, 16, 24, 30);

        List<Operation> expectedOperations = List.of(expectedOperation1, expectedOperation2);

        when(dateProvider.getCurrentDate()).thenReturn(expectedDate2);


        mockMvc.perform(post("/bank/services/deposit")
                        .contentType(APPLICATION_JSON)
                        .content(requestForMakingDeposit))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(responseForMakingDeposit.getContentAsString(UTF_8), true));

        List<Operation> operations = operationRepositoryPort.findAll();

        assertThat(operations)
                .isNotNull()
                .hasSize(2)
                .containsAll(expectedOperations);
    }

    @Test
    void shouldFailMakingADepositOnSavingAccount() throws Throwable {

        SavingAccount account = saveDataForSavingAccount("1000.00");

        String requestForMakingDepositForFail = new JSONObject()
                .put("accountId", account.getAccountId())
                .put("depositAmount", 5000.00)
                .toString();

        SavingAccount accountBeforeDeposit = new SavingAccount(account.getAccountId(), UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("1000.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1500.00"));
        Operation expectedOperation1 = new Operation(null, accountBeforeDeposit, DEPOSIT, accountBeforeDeposit.getBalance(), accountBeforeDeposit.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));

        mockMvc.perform(post("/bank/services/deposit")
                        .contentType(APPLICATION_JSON)
                        .content(requestForMakingDepositForFail))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(responseForDepositLimitExceededException.getContentAsString(UTF_8), true));

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

    private SavingAccount saveDataForSavingAccount(final String balance) throws Throwable {
        SavingAccount account = new SavingAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal(balance), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1500.00"));
        Operation operation = new Operation(1L, account, DEPOSIT, account.getBalance(), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        SavingAccount savingAccount = savingAccountRepositoryPort.saveSavingAccount(account);

        operation.setAccountId(savingAccount);
        operationRepositoryPort.saveOperation(operation);
        return savingAccount;
    }
}
