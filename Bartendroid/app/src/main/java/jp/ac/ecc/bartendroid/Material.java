package jp.ac.ecc.bartendroid;

public class Material {

    private String m_material_name;
    
    private int m_sweetness;
    private int m_clear;
    private int m_bitterness;
    private int m_sourness;
    private int m_shibumi;

    public Material (String name, int sweetness, int clear, int bitter, int sour, int shibumi) {
        this.m_material_name = name;
        this.m_sweetness = sweetness;
        this.m_clear = clear;
        this.m_bitterness = bitter;
        this.m_sourness = sour;
        this.m_shibumi = shibumi;
    }
    
    public String getMaterialName() {
        return m_material_name;
    }

    public void setMaterialName(String material_name) {
        this.m_material_name = material_name;
    }

    public int getSweetness() {
        return m_sweetness;
    }

    public void setSweetness(int sweetness) {
        this.m_sweetness = sweetness;
    }

    public int getClear() {
        return m_clear;
    }

    public void setClear(int clear) {
        this.m_clear = clear;
    }

    public int getBitterness() {
        return m_bitterness;
    }

    public void setBitterness(int bitterness) {
        this.m_bitterness = bitterness;
    }

    public int getSourness() {
        return m_sourness;
    }

    public void setSourness(int sourness) {
        this.m_sourness = sourness;
    }

    public int getShibumi() {
        return m_shibumi;
    }

    public void setShibumi(int shibumi) {
        this.m_shibumi = shibumi;
    }    
}
