package com.embedika.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Long id;

    @NotBlank(message = "Номер автомобиля должен быть заполнен")
    private String number;

    private Long person_id;

    @NotBlank(message = "Марка автомобиля должен быть заполнен")
    private String brand;

    @NotBlank(message = "Цвет автомобиля должен быть заполнен")
    private String color;

    @NotBlank(message = "Год выпуска автомобиля должен быть заполнен")
    private String release;
}
