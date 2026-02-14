package frc.robot;

public final class StateTracker {
    public static ShooterState currentShooterState = ShooterState.OFF;
    public static ClimberState currentClimberState = ClimberState.STORED;
    public static ElevatorState currentElevatorState = ElevatorState.IDLE;
    public static IntakeState currentIntakeState = IntakeState.STORED;
    public static IndexerState currentIndexerState = IndexerState.OFF;

    public static void defaultShooterState() {
        currentShooterState = ShooterState.OFF;
    }

    public static void defaultClimberState() {
        currentClimberState = ClimberState.STORED;
    }

    public static void defaultElevatorState() {
        currentElevatorState = ElevatorState.IDLE;
    }

    public static void defaultIntakeState() {
        currentIntakeState = IntakeState.STORED;
    }

    public static void defaultIndexerState() {
        currentIndexerState = IndexerState.OFF;
    }

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
}
