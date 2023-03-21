package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Cubert extends SubsystemBase {
    
    private static Cubert shooterInstance = null;

    private final DoubleSolenoid leftIntakePiston;
    private final DoubleSolenoid rightIntakePiston;
    private final CANSparkMax intakeRollerMotor;

    private final CANSparkMax indexerMotor;
    private final WPI_TalonFX shooterMotor;

    // TODO Beam Break Entry
    private final GenericEntry intakePistonsEntry;
    private final GenericEntry shooterRPMEntry;

    public Cubert () {

        this.leftIntakePiston = new DoubleSolenoid(Constants.ElectronicConstants.PNEUMATICS_HUB, PneumaticsModuleType.REVPH, Constants.CubertConstants.LEFT_INTAKE_PISTON_FORWARD, Constants.CubertConstants.LEFT_INTAKE_PISTON_REVERSE);
        this.rightIntakePiston = new DoubleSolenoid(Constants.ElectronicConstants.PNEUMATICS_HUB, PneumaticsModuleType.REVPH, Constants.CubertConstants.RIGHT_INTAKE_PISTON_FORWARD, Constants.CubertConstants.RIGHT_INTAKE_PISTON_REVERSE);
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

        this.intakePistonsEntry = Shuffleboard.getTab("Competition")
            .add("Intake Pistons", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withPosition(0, 4)
            .withSize(1, 1)
            .getEntry();

        this.shooterRPMEntry = Shuffleboard.getTab("Competition")
            .add("Shooter RPM", 0.0)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(4, 4)
            .withSize(1, 1)
            .getEntry();
    }

    public static Cubert getInstance () {

        if (shooterInstance == null) { shooterInstance = new Cubert(); }
        return shooterInstance;
    }

    public void periodic () { this.shooterRPMEntry.setDouble(this.shooterMotor.getSelectedSensorVelocity()); }

    public void setIntakePistons (Boolean intake) {
       
        DoubleSolenoid.Value pistonValue;
        
        if (intake != null) {
            if (intake.equals(true)) { 
                
                pistonValue = DoubleSolenoid.Value.kForward;
                this.intakePistonsEntry.setBoolean(true);
            } else { 
                
                pistonValue = DoubleSolenoid.Value.kReverse; 
                this.intakePistonsEntry.setBoolean(false);
            }
        } else { 
            
            pistonValue = DoubleSolenoid.Value.kOff; 
        }
     
        this.leftIntakePiston.set(pistonValue);
        this.rightIntakePiston.set(pistonValue);
    }

    /**
     * Sets the intake roller motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at 70% power for safety reasons.
     */
    // TODO Beam Breaks
    public void setIntakeRollers (double velocity) { this.intakeRollerMotor.set(velocity * 0.7); }

    /**
     * Sets the indexer motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at 85% power for safety reasons.
     */
    public void setIndexer (double velocity) { this.indexerMotor.set(velocity  * 0.85); }

    /**
     * Sets the shooter motor to the given velocity, based upon input from the XBox Controller.
     * No restriction is placed upon the inputs
    */
    public void setShooter (double velocity) { this.shooterMotor.set(ControlMode.Velocity, velocity); }
    public double getShooter () { return this.shooterMotor.getSelectedSensorVelocity(); }
}
