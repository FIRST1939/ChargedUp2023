package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Cubert extends SubsystemBase {
    
    private static Cubert shooterInstance = null;

    /**
    private final DoubleSolenoid leftIntakePiston;
    private final DoubleSolenoid rightIntakePiston;
    */
    private final CANSparkMax intakeRollerMotor;

    private final CANSparkMax indexerMotor;
    private final WPI_TalonFX shooterMotor;

    public Cubert () {

        /**
        this.leftIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CubertConstants.LEFT_INTAKE_PISTON_FORWARD, Constants.CubertConstants.LEFT_INTAKE_PISTON_REVERSE);
        this.rightIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CubertConstants.RIGHT_INTAKE_PISTON_FORWARD, Constants.CubertConstants.RIGHT_INTAKE_PISTON_REVERSE);
        */
        this.intakeRollerMotor = new CANSparkMax(Constants.CubertConstants.INTAKE_ROLLER_MOTOR, MotorType.kBrushed);

        this.indexerMotor = new CANSparkMax(Constants.CubertConstants.INDEXER_MOTOR, MotorType.kBrushless);
        this.shooterMotor = new WPI_TalonFX(Constants.CubertConstants.SHOOTER_MOTOR);

        this.intakeRollerMotor.restoreFactoryDefaults();
        this.intakeRollerMotor.setIdleMode(IdleMode.kCoast);

        this.indexerMotor.restoreFactoryDefaults();
        this.indexerMotor.setIdleMode(IdleMode.kBrake);

        this.shooterMotor.configFactoryDefault();
        this.shooterMotor.setNeutralMode(NeutralMode.Coast);
        this.shooterMotor.setInverted(InvertType.InvertMotorOutput);
        this.shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.shooterMotor.configNominalOutputForward(0,30);
        this.shooterMotor.configNominalOutputReverse(0,30);
        this.shooterMotor.configPeakOutputForward(1,30);
        this.shooterMotor.configPeakOutputReverse(-1,30);

        this.shooterMotor.config_kF(0, 0.10792);
        this.shooterMotor.config_kP(0, 0.0164);
    }

    public static Cubert getInstance () {

        if (shooterInstance == null) { shooterInstance = new Cubert(); }
        return shooterInstance;
    }

    /**
    public void setIntakePistons (Boolean intake) {

        DoubleSolenoid.Value pistonValue;

        if (intake != null) {

            if (intake.equals(true)) { pistonValue = DoubleSolenoid.Value.kForward; }
            else { pistonValue = DoubleSolenoid.Value.kReverse; }
        } else { pistonValue = DoubleSolenoid.Value.kOff; }

        this.leftIntakePiston.set(pistonValue);
        this.rightIntakePiston.set(pistonValue);
    }
    */

    /**
     * Sets the intake roller motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~70% power for safety reasons.
     */
    // TODO Beam Breaks
    public void setIntakeRollers (double velocity) { this.intakeRollerMotor.set(velocity / 1.4); }

    /**
     * Sets the indexer motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~50% power for safety reasons.
     */
    public void setIndexer (double velocity) { this.indexerMotor.set(velocity / 2.0); }

    /**
     * Sets the shooter motor to the given velocity, based upon input from the XBox Controller.
     * No restriction is placed upon the inputs
    */
    public void setShooter (double velocity) { this.shooterMotor.set(ControlMode.Velocity, velocity); }
}
