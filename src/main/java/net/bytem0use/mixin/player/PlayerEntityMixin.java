package net.bytem0use.mixin.player;

import net.bytem0use.common.config.PlayerFlightConfig;
import net.bytem0use.common.utils.FlyingState;
import net.bytem0use.common.utils.PlayerFlightInterface;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerEntity.class})
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerFlightInterface {
    @Unique
    private static final TrackedData<Byte> FLIGHT_STATE;
    @Unique
    private static final TrackedData<Float> FLIGHT_THROTTLE;
    @Unique
    private static final TrackedData<Boolean> FLIGHT_ACCELERATING;
    @Unique
    private static final TrackedData<Float> HOVER_FORWARD;
    @Unique
    private static final TrackedData<Float> HOVER_SIDEWAYS;
    @Unique
    private static final TrackedData<Boolean> SPEED_LOCKED;
    @Unique
    private static final TrackedData<Integer> FLIGHT_TICKS;
    @Unique
    private static final TrackedData<Integer> TAKEOFF_TICKS;
    @Unique
    private float prevFlightThrottle = 0.0F;
    @Unique
    private float clientLocalThrottle = 0.0F;
    @Unique
    private float prevClientLocalThrottle = 0.0F;
    @Unique
    private boolean isClientLocalPlayer = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public FlyingState getFlightState() {
        return FlyingState.values()[(Byte) this.getDataTracker().get(FLIGHT_STATE)];
    }

    public void setFlightState(FlyingState state) {
        FlyingState currentState = this.getFlightState();
        if (currentState != state && (state == FlyingState.GROUND || currentState == FlyingState.GROUND)) {
            this.setFlightThrottle(0.0F);
            this.prevFlightThrottle = 0.0F;
            this.setFlightAccelerating(false);
            this.clientLocalThrottle = 0.0F;
            this.prevClientLocalThrottle = 0.0F;
        }

        this.getDataTracker().set(FLIGHT_STATE, (byte) state.ordinal());
    }

    public float getFlightThrottle() {
        return (Float) this.getDataTracker().get(FLIGHT_THROTTLE);
    }

    public void setFlightThrottle(float throttle) {
        this.getDataTracker().set(FLIGHT_THROTTLE, throttle);
    }

    public boolean isFlightAccelerating() {
        return (Boolean) this.getDataTracker().get(FLIGHT_ACCELERATING);
    }

    public void setFlightAccelerating(boolean accelerating) {
        this.getDataTracker().set(FLIGHT_ACCELERATING, accelerating);
    }

    public float getLerpedFlightThrottle(float tickDelta) {
        return this.getWorld().isClient && this.isClientLocalPlayer() ? MathHelper.lerp(tickDelta, this.prevClientLocalThrottle, this.clientLocalThrottle) : MathHelper.lerp(tickDelta, this.prevFlightThrottle, this.getFlightThrottle());
    }

    public float getHoverForward() {
        return (Float) this.getDataTracker().get(HOVER_FORWARD);
    }

    public void setHoverForward(float forward) {
        this.getDataTracker().set(HOVER_FORWARD, forward);
    }

    public float getHoverSideways() {
        return (Float) this.getDataTracker().get(HOVER_SIDEWAYS);
    }

    public void setHoverSideways(float sideways) {
        this.getDataTracker().set(HOVER_SIDEWAYS, sideways);
    }

    public boolean isSpeedLocked() {
        return (Boolean) this.getDataTracker().get(SPEED_LOCKED);
    }

    public void setSpeedLocked(boolean locked) {
        this.getDataTracker().set(SPEED_LOCKED, locked);
    }

    public int getFlightTicks() {
        return (Integer) this.getDataTracker().get(FLIGHT_TICKS);
    }

    public void setFlightTicks(int ticks) {
        this.getDataTracker().set(FLIGHT_TICKS, ticks);
    }

    public int getTakeoffTicks() {
        return (Integer) this.getDataTracker().get(TAKEOFF_TICKS);
    }

    public void setTakeoffTicks(int ticks) {
        this.getDataTracker().set(TAKEOFF_TICKS, ticks);
    }

    public boolean isClientLocalPlayer() {
        return this.isClientLocalPlayer;
    }

    public void setClientLocalPlayer(boolean isLocal) {
        this.isClientLocalPlayer = isLocal;
    }

    @Inject(
            method = {"initDataTracker()V"},
            at = {@At("TAIL")}
    )
    protected void onInitDataTracker(CallbackInfo ci) {
        this.getDataTracker().startTracking(FLIGHT_STATE, (byte) FlyingState.GROUND.ordinal());
        this.getDataTracker().startTracking(FLIGHT_THROTTLE, 0.0F);
        this.getDataTracker().startTracking(FLIGHT_ACCELERATING, false);
        this.getDataTracker().startTracking(HOVER_FORWARD, 0.0F);
        this.getDataTracker().startTracking(HOVER_SIDEWAYS, 0.0F);
        this.getDataTracker().startTracking(SPEED_LOCKED, false);
        this.getDataTracker().startTracking(FLIGHT_TICKS, 0);
        this.getDataTracker().startTracking(TAKEOFF_TICKS, 0);
    }

    @Inject(
            method = {"tick()V"},
            at = {@At("TAIL")}
    )
    private void onTick(CallbackInfo ci) {
        if (this.getTakeoffTicks() > 0) {
            this.setTakeoffTicks(this.getTakeoffTicks() - 1);
            this.setVelocity(this.getVelocity().x, 2.8, this.getVelocity().z);
            this.velocityModified = true;
        }

        FlyingState currentState = this.getFlightState();
        if (currentState != FlyingState.GROUND) {
            this.setSprinting(false);
            float throttleBeforeTick = this.getFlightThrottle();
            if (!this.isSpeedLocked()) {
                if (this.isFlightAccelerating()) {
                    this.setFlightThrottle(Math.min(1.0F, this.getFlightThrottle() + 0.017F));
                } else {
                    this.setFlightThrottle(Math.max(0.0F, this.getFlightThrottle() - 0.034F));
                }

                if (this.getWorld().isClient && this.isClientLocalPlayer()) {
                    if (this.isFlightAccelerating()) {
                        this.clientLocalThrottle = Math.min(1.0F, this.clientLocalThrottle + 0.017F);
                    } else {
                        this.clientLocalThrottle = Math.max(0.0F, this.clientLocalThrottle - 0.034F);
                    }
                }

                if (this.getWorld().isClient && this.isClientLocalPlayer()) {
                    if (this.getFlightThrottle() != 0.0F && (!this.isOnGround() && !this.horizontalCollision || currentState != FlyingState.FLYING && currentState != FlyingState.BOOSTING)) {
                        if (Math.abs(this.clientLocalThrottle - this.getFlightThrottle()) > 0.2F) {
                            this.clientLocalThrottle = this.getFlightThrottle();
                        }
                    } else {
                        this.clientLocalThrottle = 0.0F;
                        this.prevClientLocalThrottle = 0.0F;
                    }
                }
            }

            if (this.getFlightThrottle() > 0.5F) {
                this.setFlightTicks(Math.min(400, this.getFlightTicks() + 1));
            } else {
                this.setFlightTicks(Math.max(0, this.getFlightTicks() - 2));
            }

            //if (this.getWorld().isClient && throttleBeforeTick < 0.6F && this.getFlightThrottle() >= 0.6F && ViltrumiteConfigClient.INSTANCE.enableSonicBoomSound) {
                //this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSounds.SONIC_BOOM, SoundCategory.PLAYERS, ViltrumiteConfigClient.INSTANCE.sonicBoomVolume, 1.0F, false);
           // }

            if (currentState == FlyingState.FLYING || currentState == FlyingState.BOOSTING) {
                Vec3d lookVec = this.getRotationVector();
                float maxSpeed = PlayerFlightConfig.INSTANCE.maxFlightSpeed;
                float currentSpeed = this.getFlightThrottle() * maxSpeed;
                this.setVelocity(lookVec.x * (double) currentSpeed, lookVec.y * (double) currentSpeed, lookVec.z * (double) currentSpeed);
            }

            if (!this.getWorld().isClient) {
                FlyingState newState = FlyingState.HOVERING;
                if (this.getFlightThrottle() >= 0.8F) {
                    newState = FlyingState.BOOSTING;
                } else if (this.getFlightThrottle() > 0.0F) {
                    newState = FlyingState.FLYING;
                }

                if (currentState != newState) {
                    this.setFlightState(newState);
                }

                if ((this.isOnGround() || this.horizontalCollision) && (currentState == FlyingState.FLYING || currentState == FlyingState.BOOSTING)) {
                    this.handleFlightCollision();
                }

                if (this.isOnGround() && currentState == FlyingState.HOVERING) {
                    this.stopFlight();
                }
            }
        } else if (this.getFlightTicks() > 0) {
            this.setFlightTicks(Math.max(0, this.getFlightTicks() - 2));
        }

    }

    public void stopFlight() {
        this.setFlightState(FlyingState.GROUND);
        this.setSpeedLocked(false);
        this.setFlightThrottle(0.0F);
        this.prevFlightThrottle = 0.0F;
        this.setFlightAccelerating(false);

        this.clientLocalThrottle = 0.0F;
        this.prevClientLocalThrottle = 0.0F;
    }

    @Unique
    public void switchToHover() {
        this.setFlightState(FlyingState.HOVERING);
        this.setSpeedLocked(false);
        this.setFlightThrottle(0.0F);
        this.prevFlightThrottle = 0.0F;
        this.setFlightAccelerating(false);
        this.clientLocalThrottle = 0.0F;
        this.prevClientLocalThrottle = 0.0F;
    }

    public void handleFlightCollision() {
        this.switchToHover();
    }

    @Inject(
            method = {"tick()V"},
            at = {@At("HEAD")}
    )
    private void onTickHead(CallbackInfo ci) {
        if (this.getWorld().isClient) {
            this.prevFlightThrottle = this.getFlightThrottle();
            this.prevClientLocalThrottle = this.clientLocalThrottle;
        }

    }

    @Inject(
            method = {"writeCustomDataToNbt"},
            at = {@At("TAIL")}
    )
    private void writeCustomData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("PlayerFlightState", this.getFlightState().name());
        nbt.putFloat("PlayerFlightThrottle", this.getFlightThrottle());
    }

    @Inject(
            method = {"readCustomDataFromNbt"},
            at = {@At("TAIL")}
    )
    private void readCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("PlayerFlightState")) {
            try {
                this.setFlightState(FlyingState.valueOf(nbt.getString("PlayerFlightState")));
            } catch (IllegalArgumentException var4) {
                this.setFlightState(FlyingState.GROUND);
            }
        }

        if (nbt.contains("PlayerFlightThrottle")) {
            this.setFlightThrottle(nbt.getFloat("PlayerFlightThrottle"));
        }

    }

    static {
        FLIGHT_STATE = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.BYTE);
        FLIGHT_THROTTLE = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
        FLIGHT_ACCELERATING = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
        HOVER_FORWARD = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
        HOVER_SIDEWAYS = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
        SPEED_LOCKED = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
        FLIGHT_TICKS = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);
        TAKEOFF_TICKS = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
