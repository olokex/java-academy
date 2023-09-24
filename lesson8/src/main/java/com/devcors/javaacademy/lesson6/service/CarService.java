package com.devcors.javaacademy.lesson6.service;

import com.devcors.javaacademy.lesson6.data.dto.CarDto;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarColor;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarType;
import com.devcors.javaacademy.lesson6.data.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void createCar(CarDto carDto) {
        validateCarEnums(carDto);

        Car car = Car.builder()
                     .brand(carDto.getBrand())
                     .type(carDto.getType())
                     .color(carDto.getColor())
                     .year(carDto.getYear())
                     .licencePlate(carDto.getLicencePlate())
                     .build();

        carRepository.save(car);
    }

    public CarDto findById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            Car carEntity = car.get();
            return CarDto.builder()
                .brand(carEntity.getBrand())
                .type(CarType.valueOf(carEntity.getType().name()))
                .color(CarColor.valueOf(carEntity.getColor().name()))
                .year(carEntity.getYear())
                .licencePlate(carEntity.getLicencePlate())
                .build();
        }
        return null;
    }

    public List<CarDto> getAll() {
        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = new ArrayList<>();

        for (Car car : cars) {
            CarDto carDto = convertToDto(car);
            carDtos.add(carDto);
        }

        return carDtos;
    }

    public void updateCar(Long id, CarDto carDtoUpdate) {
        Optional<Car> existingCar = carRepository.findById(id);
        if (existingCar.isEmpty()) {
            throw new RuntimeException();
        }

        validateCarEnums(carDtoUpdate);

        Car updatedCar = Car.builder()
                            .id(id)
                            .brand(carDtoUpdate.getBrand())
                            .type(carDtoUpdate.getType())
                            .color(carDtoUpdate.getColor())
                            .year(carDtoUpdate.getYear())
                            .licencePlate(carDtoUpdate.getLicencePlate())
                            .build();

        carRepository.save(updatedCar);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<CarDto> findByBrand(String brand) {
            List<Car> cars = carRepository.findByBrand(brand);

            return cars.stream()
               .map(CarService::convertToDto)
               .collect(Collectors.toList());
    }
    private void validateCarEnums(CarDto carDto) {
        if (!isValidEnumValue(CarType.class, carDto.getType().name())) {
            throw new IllegalArgumentException("Invalid car type: " + carDto.getType());
        }

        if (!isValidEnumValue(CarColor.class, carDto.getColor().name())) {
            throw new IllegalArgumentException("Invalid car color: " + carDto.getColor());
        }
    }
    private <T extends Enum<T>> boolean isValidEnumValue(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                     .map(Enum::name)
                     .collect(Collectors.toList())
                     .contains(value);
    }

    public static CarDto convertToDto(Car car) {
        return CarDto.builder()
                    .brand(car.getBrand())
                    .type(car.getType())
                    .color(car.getColor())
                    .year(car.getYear())
                    .licencePlate(car.getLicencePlate())
                    .build();
    }
}
