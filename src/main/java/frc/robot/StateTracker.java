package frc.robot;

public final class StateTracker {
    public static ShooterState currentShooterState = ShooterState.OFF;
    public static ClimberState currentClimberState = ClimberState.STORED;
    public static ElevatorState currentElevatorState = ElevatorState.IDLE;
    public static IntakeState currentIntakeState = IntakeState.STORED;
    public static IndexerState indexerState = IndexerState.OFF;
    public static FeederState feederState = FeederState.OFF;

    enum ShooterState {
        OFF,
        REVING,
        AT_SPEED,
        SLOWING_DOWN
    }

    enum ClimberState {
        STORED,
        DEPLOYED
    }

    enum ElevatorState {
        IDLE,
        ZEROING,
        RAISING,
        AT_MAX,
        AT_ZERO
    }

    enum IntakeState {
        STORED,
        EXTENDING,
        EXTENDED,
        CONTRACTING
    }

    enum IndexerState {
        ON,
        OFF
    }

    enum FeederState {
        ON,
        OFF
    }
}
