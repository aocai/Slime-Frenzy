package azunaapps.slimefrenzy;

/**
 * Created by Shino on 19/08/2014.
 */
public class Slime {
    private int slimeState;
    private int scale;
    private float slimeX;
    private float slimeY;
    private float headingX;
    private float headingY;
    private float SPEED = 0.5f;

    public Slime(int state, int Scale, float SlimeX, float SlimeY, float HeadingX, float HeadingY, float Speed) {
        slimeState = state;
        scale = Scale;
        slimeX = SlimeX;
        slimeY = SlimeY;
        headingX = HeadingX;
        headingY = HeadingY;
        SPEED = Speed;
    }

    //bState == 0-200; 0 = delete; 1 = default, 2-40 = slimeLeft/slimeRight, 41-80 = slimeA, 81-120 = slimeB, 121-160 = slimeC, 161-200 = slimeD
    public void setSlimeState(int state) {
        slimeState = state;
    }
    public void setScale(int Scale) {
        scale = Scale;
    }
    public void setX(float BubbleX) {
        slimeX = BubbleX;
    }
    public void setY(float BubbleY) {
        slimeY = BubbleY;
    }
    public void setHeadingX(float HeadingX) {
        headingX = HeadingX;
    }
    public void setHeadingY(float HeadingY) {
        headingY = HeadingY;
    }
    public int getSlimeState() {
        return slimeState;
    }
    public int getScale() {
        return scale;
    }
    public float getX() {
        return slimeX;
    }
    public float getY() {
        return slimeY;
    }
    public float getHeadingX() {
        return headingX;
    }
    public float getHeadingY() {
        return headingY;
    }
    public float getSpeed() {
        return SPEED;
    }
}
