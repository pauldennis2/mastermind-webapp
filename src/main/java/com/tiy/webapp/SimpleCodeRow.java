package com.tiy.webapp;

/**
 * Created by Paul Dennis on 1/23/2017.
 */
public class SimpleCodeRow {

    Color first;
    Color second;
    Color third;
    Color fourth;

    int rightColorWrongSpot;
    int rightColorRightSpot;

    public SimpleCodeRow () {

    }

    public static CompareResult compareTwoRows (SimpleCodeRow secret, SimpleCodeRow guess) {
        int rightColorWrongSpot = 0;
        int rightColorRightSpot = 0;

        if (secret.getFirst() == guess.getFirst()) {
            rightColorRightSpot++;
        }
        if (secret.getSecond() == guess.getSecond()) {
            rightColorRightSpot++;
        }
        return null;
    }

    public Color getFirst() {
        return first;
    }

    public Color getSecond() {
        return second;
    }

    public Color getThird() {
        return third;
    }

    public Color getFourth() {
        return fourth;
    }

    public int getRightColorWrongSpot() {
        return rightColorWrongSpot;
    }

    public int getRightColorRightSpot() {
        return rightColorRightSpot;
    }

    public void setFirst(Color first) {
        this.first = first;
    }

    public void setSecond(Color second) {
        this.second = second;
    }

    public void setThird(Color third) {
        this.third = third;
    }

    public void setFourth(Color fourth) {
        this.fourth = fourth;
    }

    public void setRightColorWrongSpot(int rightColorWrongSpot) {
        this.rightColorWrongSpot = rightColorWrongSpot;
    }

    public void setRightColorRightSpot(int rightColorRightSpot) {
        this.rightColorRightSpot = rightColorRightSpot;
    }
}
