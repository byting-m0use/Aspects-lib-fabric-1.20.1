package net.bytem0use.common.utils;

public interface PlayerFlightInterface {

    FlyingState getFlyingState();

    void setFlyingState();

    double getFlightTicks();

    void setFlightTicks(double num1);

    float getHoveringForward();

    void setHoveringForward(float var1);

    float getHoveringSideways();

    void setHoveringSideways(float var1);
}
