package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    
    private static Shooter shooterInstance = null;

    private final Solenoid intakePiston;
    private final CANSparkMax intakeRollerMotor;

    public Shooter () {

        this.intakePiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.ShooterConstants.INTAKE_PISTON);
        this.intakeRollerMotor = new CANSparkMax(Constants.ShooterConstants.INTAKE_ROLLER_MOTOR, MotorType.kBrushed);

        this.intakeRollerMotor.restoreFactoryDefaults();
        this.intakeRollerMotor.setIdleMode(IdleMode.kBrake);
    }

    public static Shooter getInstance () {

        if (shooterInstance == null) { shooterInstance = new Shooter(); }
        return shooterInstance;
    }
}
