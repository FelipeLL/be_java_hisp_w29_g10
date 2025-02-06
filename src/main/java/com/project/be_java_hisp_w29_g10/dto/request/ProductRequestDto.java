package com.project.be_java_hisp_w29_g10.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {
        @NotNull(message = "El id del producto no puede ser nulo")
        @Min(value = 1, message = "El id del producto debe ser mayor a 0")
        private Long product_id;

        @NotBlank(message = "El nombre del producto no puede ser nulo o vacío")
        @Size(min = 1, max = 40, message = "El nombre del producto debe tener entre 1 y 40 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9 ñÑ]*$", message = "El nombre del producto solo puede contener letras y números")
        private String product_name;

        @NotBlank(message = "El tipo de producto no puede ser nulo o vacío")
        @Size(min = 1, max = 15, message = "El tipo de producto debe tener entre 1 y 15 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9 ñÑ]*$", message = "El tipo de producto solo puede contener letras y números")
        private String type;

        @NotBlank(message = "La marca del producto no puede ser nulo o vacío")
        @Size(min = 1, max = 25, message = "La marca del producto debe tener entre 1 y 25 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9 ñÑ]*$", message = "La marca del producto solo puede contener letras y números")
        private String brand;

        @NotBlank(message = "El color del producto no puede ser nulo o vacío")
        @Size(min=1, max=15, message = "El color del producto debe tener entre 1 y 15 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9 ñÑ]*$", message = "El color del producto solo puede contener letras y números")
        private String color;

        @Size(max = 80, message = "Las notas del producto no puede tener más de 80 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9 ñÑ]*$", message = "Las notas del producto solo puede contener letras y números")
        private String notes;
}
