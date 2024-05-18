package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceDto {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int amount;

    @NotNull(message = "Brand cannot be left blank")
    @Length(min = 1,max = 256,message = "Brand length must be between {min} and {max} values")
    private String brand;

    @NotNull(message = "Equipment cannot be left blank")
    @Length(min = 1,max = 256,message = "Equipment length must be between {min} and {max} values")
    private String equipment;

}
