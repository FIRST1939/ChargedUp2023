package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    
    private static Shooter shooterInstance = null;

    private final DoubleSolenoid leftIntakePiston;
    private final DoubleSolenoid rightIntakePiston;
    private final CANSparkMax intakeRollerMotor;

    private final CANSparkMax indexerMotor;

    public Shooter () {

        this.leftIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ShooterConstants.LEFT_INTAKE_PISTON_FORWARD, Constants.ShooterConstants.LEFT_INTAKE_PISTON_REVERSE);
        this.rightIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ShooterConstants.RIGHT_INTAKE_PISTON_FORWARD, Constants.ShooterConstants.RIGHT_INTAKE_PISTON_REVERSE);
        this.intakeRollerMotor = new CANSparkMax(Constants.ShooterConstants.INTAKE_ROLLER_MOTOR, MotorType.kBrushed);

        this.indexerMotor = new CANSparkMax(Constants.ShooterConstants.INDEXER_MOTOR, MotorType.kBrushless);

        this.intakeRollerMotor.restoreFactoryDefaults();
        this.intakeRollerMotor.setIdleMode(IdleMode.kCoast);

        this.indexerMotor.restoreFactoryDefaults();
        this.indexerMotor.setIdleMode(IdleMode.kBrake);
    }

    public static Shooter getInstance () {

        if (shooterInstance == null) { shooterInstance = new Shooter(); }
        return shooterInstance;
    }

    public void setIntakePistons (Boolean intake) {

        DoubleSolenoid.Value pistonValue;

        if (intake != null) {

            if (intake.equals(true)) { pistonValue = DoubleSolenoid.Value.kForward; }
            else { pistonValue = DoubleSolenoid.Value.kReverse; }
        } else { pistonValue = DoubleSolenoid.Value.kOff; }

        this.leftIntakePiston.set(pistonValue);
        this.rightIntakePiston.set(pistonValue);
    }
}
