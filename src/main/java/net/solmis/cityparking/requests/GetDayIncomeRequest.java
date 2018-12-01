package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class GetDayIncomeRequest {
    @NotNull(message="Attribute day is required.")
    public LocalDate day;

    @NotNull(message="Attribute secretToken is required.")
    public String secretToken;

    @JsonCreator
    public GetDayIncomeRequest(@JsonProperty("day") final String day,
                               @JsonProperty("secretToken") final String secretToken) {
        if (day != null)
            this.day = LocalDate.parse(day);
        this.secretToken = secretToken;
    }
}
