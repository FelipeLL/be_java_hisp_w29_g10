package com.project.be_java_hisp_w29_g10.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {
    @Min(value = 1, message = "El id del vendedor debe ser mayor a 0")
    @NotNull(message = "El id del vendedor no puede ser nulo")
    private Long user_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate date;

    @NotNull(message = "La categoria no puede ser nula")
    private Integer category;

    @NotNull(message = "El precio no puede ser nulo")
    @Range(min = 0, max = 10000000, message = "El precio debe estar entre 0 y 10000000")
    private Double price;

    @Valid
    private ProductRequestDto product;

    @Builder.Default
    @Min(value = 0, message = "El descuento debe ser mayor o igual a 0")
    private Double discount = 0.0;

    @Builder.Default
    private Boolean has_promo = false;

}
