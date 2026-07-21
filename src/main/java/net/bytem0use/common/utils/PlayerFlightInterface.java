package net.bytem0use.common.utils;

public interface PlayerFlightInterface {

    FlyingState getFlightState();

    void setFlightState(FlyingState state1);

    int getFlightTicks();

    void setFlightTicks(int var1);

    boolean isFlying();

    void getIsFlying(boolean bool1);

    int getFlightSpeed();

    void setFlightSpeed(int var1);
}
