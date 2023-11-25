package carsharing.entities;

public class Car {

    private String name;
    private long companyId;

    public Car() {
    }

    public Car(String name) {
        this.name = name;
    }

    public Car(String name, long companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
