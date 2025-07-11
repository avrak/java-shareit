package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {
	@Positive(message = "ID вещи должен быть положительным числом")
	private long itemId;
	@FutureOrPresent(message = "Дата начала должна быть текущей или в будущем")
	private LocalDateTime start;
	@Future(message = "Дата окончания должна быть будущем")
	private LocalDateTime end;

	@AssertTrue(message = "Дата окончания бронирования должна быть после даты начала")
	public boolean isEndAfterStart() {
		return end == null || start == null || end.isAfter(start);
	}

}
