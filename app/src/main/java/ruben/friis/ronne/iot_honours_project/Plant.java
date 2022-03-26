package ruben.friis.ronne.iot_honours_project;

public class Plant {
    private String name;
    private float tempMin;
    private String light;

    public Plant(String name, float tempMin, String light) {
        this.name = name;
        this.tempMin = tempMin;
        this.light = light;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public float getTempMin() { return tempMin; }

    public void setTempMin(float tempMin) { this.tempMin = tempMin; }

    public String getLight() { return light; }

    public void setLight(String light) { this.light = light; }
}
