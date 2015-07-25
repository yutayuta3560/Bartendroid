package jp.ac.ecc.bartendroid;

public class Material {

    public String m_material_name;

    public int m_sweetness;
    public int m_clear;
    public int m_bitterness;
    public int m_sourness;
    public int m_shibumi;
    public boolean m_isAlcohol;



    public Material(String name, int sweetness, int clear, int bitter, int sour, int shibumi) {
        // non-setting default non-alcohol.
        this(name, sweetness, clear, bitter, sour, shibumi, 0);
    }

    public Material(String name, int sweetness, int clear, int bitter, int sour, int shibumi, boolean isAlcohol) {
        this.m_material_name = name;
        this.m_sweetness = sweetness;
        this.m_clear = clear;
        this.m_bitterness = bitter;
        this.m_sourness = sour;
        this.m_shibumi = shibumi;
        setAlcohol(isAlcohol);
    }

    public Material(String name, int sweetness, int clear, int bitter, int sour, int shibumi, int isAlcohol) {
        this.m_material_name = name;
        this.m_sweetness = sweetness;
        this.m_clear = clear;
        this.m_bitterness = bitter;
        this.m_sourness = sour;
        this.m_shibumi = shibumi;
        setAlcohol(isAlcohol);
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

    public void setAlcohol(boolean alcohol) {
        m_isAlcohol = alcohol;
    }

    public void setAlcohol(int i) {
        m_isAlcohol = (i == 1);
    }

    public boolean getAlcohol_bool() {
        return m_isAlcohol;
    }

    public int getAlcohol_int() {
        return m_isAlcohol ? 1 : 0;
    }
}