package com.example.wecar;

public class Car {
    private String nameCar;
    private  String horse_power;//כוח סוס
    private  String owners; //בעלים
    private String color; //צבע
    //private  String drive_type; //סוג הנעה
    private  String car_num; //מספר הרכב
    private  String manufacturer; //יַצרָן
    private  String year; //שנת היצור
    private  String Car_model; //דגם רכב
    private  String test;
    private  String kilometre;
    private  String Engine_capacity; //קיבולת מנוע
    private  String Gear_shifting_model;  //דגם מעביר הילוכים
    private  String   price; //מחיר

    public Car() {
    }

    public Car(String nameCar, String horse_power, String owners, String color, /*String drive_type,*/ String car_num, String manufacturer, String year, String car_model, String test, String kilometre, String engine_capacity, String gear_shifting_model, String price) {
        this.nameCar = nameCar;
        this.horse_power = horse_power;
        this.owners = owners;
        this.color = color;
       // this.drive_type = drive_type;
        this.car_num = car_num;
        this.manufacturer = manufacturer;
        this.year = year;

        Car_model = car_model;
        this.test = test;
        this.kilometre = kilometre;
        Engine_capacity = engine_capacity;
        Gear_shifting_model = gear_shifting_model;
        this.price = price;
    }

    public String getNameCar() {
        return nameCar;
    }

    public void setNameCar(String nameCar) {
        this.nameCar = nameCar;
    }

    public String getHorse_power() {
        return horse_power;
    }

    public void setHorse_power(String horse_power) {
        this.horse_power = horse_power;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
/*
    public String getDrive_type() {
        return drive_type;
    }

    public void setDrive_type(String drive_type) {
        this.drive_type = drive_type;
    }*/

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getCar_model() {
        return Car_model;
    }

    public void setCar_model(String car_model) {
        Car_model = car_model;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getKilometre() {
        return kilometre;
    }

    public void setKilometre(String kilometre) {
        this.kilometre = kilometre;
    }

    public String getEngine_capacity() {
        return Engine_capacity;
    }

    public void setEngine_capacity(String engine_capacity) {
        Engine_capacity = engine_capacity;
    }

    public String getGear_shifting_model() {
        return Gear_shifting_model;
    }

    public void setGear_shifting_model(String gear_shifting_model) {
        Gear_shifting_model = gear_shifting_model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "nameCar='" + nameCar + '\'' +
                ", horse_power='" + horse_power + '\'' +
                ", owners='" + owners + '\'' +
                ", color='" + color + '\'' +
               /* ", drive_type='" + drive_type + '\'' +*/
                ", car_num='" + car_num + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", year='" + year + '\'' +
                ", Car_model='" + Car_model + '\'' +
                ", test='" + test + '\'' +
                ", kilometre='" + kilometre + '\'' +
                ", Engine_capacity='" + Engine_capacity + '\'' +
                ", Gear_shifting_model='" + Gear_shifting_model + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
