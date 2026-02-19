package frc.robot;

public final class StateTracker {
    public static ShooterState currentShooterState = ShooterState.OFF;
    public static ClimberState currentClimberState = ClimberState.STORED;
    public static ElevatorState currentElevatorState = ElevatorState.IDLE;
    public static IntakeState currentIntakeState = IntakeState.STORED;
    public static IndexerState currentIndexerState = IndexerState.BOTH_OFF;

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
        currentIndexerState = IndexerState.BOTH_OFF;
    }

    public enum ShooterState {
        OFF,
        REVING,
        AT_SPEED,
        SLOWING_DOWN
    }

    public enum ClimberState {
        STORED,
        DEPLOYED
    }

    public enum ElevatorState {
        IDLE,
        ZEROING,
        RAISING,
        AT_MAX,
        AT_ZERO
    }

    public enum IntakeState {
        STORED,
        EXTENDING,
        EXTENDED,
        CONTRACTING
    }

    public enum IndexerState {
        STAGE1_ON_STAGE2_OFF,
        BOTH_ON,
        BOTH_OFF
    }
}
