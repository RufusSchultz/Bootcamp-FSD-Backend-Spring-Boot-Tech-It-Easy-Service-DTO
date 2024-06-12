package com.servicedto.techiteasy.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class TelevisionInputDto {
    public Long id;
    @NotBlank
    //make constraint for uniqueness here
    public String type;
    @NotBlank
    public String brand;
    @NotBlank
    public String name;
    @Positive
    @NotNull
    public double price;
    @Positive
    @NotNull
    public double availableSize;
    @Positive
    @NotNull
    public double refreshRate;
    @NotBlank
    @NotNull
    public String screenType;
    @NotBlank
    @NotNull
    public String screenQuality;
    public boolean smartTv;
    public boolean wifi;
    public boolean voiceControl;
    public boolean hdr;
    public boolean bluetooth;
    public boolean ambiLight;
    @NotNull
    public Integer originalStock;
    @PositiveOrZero
    public Integer sold;

}
