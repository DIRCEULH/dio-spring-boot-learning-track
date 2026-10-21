package dio.budgeting.infrastructure.http.request;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
        @NotBlank(message = "A descrição da transação não pode estar vazia")
        String description,

        @NotNull(message = "A categoria da transação é obrigatória")
        Category category,

        @Positive(message = "O valor da transação deve ser maior que zero")
        long amount
) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, amount, category);
    }
}
