package com.lgzarturo.api.personal.api.ticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketRequest {
    @NotNull
    private Long customerId;
    @NotNull
    private Long flightId;
}
