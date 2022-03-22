package ruben.friis.ronne.iot_honours_project;

public class Plant {
    private String name;
    private float tempMax;
    private float tempMin;
    private String lightMin;
    private String lightMax;

    public Plant(String name, float tempMax, float tempMin, String lightMax, String lightMin) {
        this.name = name;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.lightMax = lightMax;
        this.lightMin = lightMin;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public float getTempMax() { return tempMax; }

    public void setTempMax(float tempMax) { this.tempMax = tempMax; }

    public float getTempMin() { return tempMin; }

    public void setTempMin(float tempMin) { this.tempMin = tempMin; }

    public String getLightMax() { return lightMax; }

    public void setLightMax(String lightMax) { this.lightMax = lightMax; }

    public String getLightMin() { return lightMin; }

    public void setLightMin(String lightMin) { this.lightMin = lightMin; }
}
