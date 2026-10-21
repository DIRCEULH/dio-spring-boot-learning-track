package dio.budgeting;

import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionControllerValidationTest {

    private final TransactionRepository inMemoryRepository = new TransactionRepository() {
        @Override
        public Transaction save(Transaction transaction) {
            return transaction;
        }

        @Override
        public List<Transaction> findAllByCategory(Category category) {
            return List.of();
        }
    };

    private final PersistTransactionUseCase useCase = new PersistTransactionUseCase(inMemoryRepository);

    @Test
    void shouldRejectBlankDescription() {
        var input = new PersistTransactionInput("   ", 1500, Category.GROCERIES);

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("descrição");
    }

    @Test
    void shouldRejectNonPositiveAmount() {
        var input = new PersistTransactionInput("Compra no mercado", 0, Category.GROCERIES);

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("maior que zero");
    }
}
