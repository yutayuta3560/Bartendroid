package jp.ac.ecc.bartendroid;

/**
 * Created by yulth on 2015/07/25.
 */
public class MaterialBring {
    public Material material;
    public int amount;
    public String unit;

    public MaterialBring(Material material, int amount, String unit) {
        this.material = material;
        this.amount = amount;
        this.unit = unit;
    }

    public boolean tryuseMaterial(int useAmount) {
        return (useAmount <= amount);
    }

    // 使えるほど量がなければfalseを返す
    public boolean userMaterial(int useAmount) {
        if (tryuseMaterial(useAmount)) {
            amount -= useAmount;
            return true;
        }
        return false;
    }
}
