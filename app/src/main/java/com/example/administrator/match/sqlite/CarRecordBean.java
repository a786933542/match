package com.example.administrator.match.sqlite;

public class CarRecordBean {
    private int id;
    private String carId;
    private String money;
    private String people;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CarRecordBean() {
    }

    @Override
    public String toString() {
        return "CarRecordBean{" +
                "id=" + id +
                ", carId='" + carId + '\'' +
                ", money='" + money + '\'' +
                ", people='" + people + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
