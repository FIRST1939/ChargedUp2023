package frc.lib.drivetrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MotorGroup {
    
    public final CANSparkMax backMotor;
    public final CANSparkMax centerMotor;
    public final CANSparkMax frontMotor;

    public MotorGroup (int backMotorID, int centerMotorID, int frontMotorID) {

        this.backMotor = new CANSparkMax(backMotorID, MotorType.kBrushless);
        this.backMotor.restoreFactoryDefaults();
        this.backMotor.setIdleMode(IdleMode.kCoast);
        this.backMotor.setOpenLoopRampRate(0.25);

        this.centerMotor = new CANSparkMax(centerMotorID, MotorType.kBrushless);
        this.centerMotor.restoreFactoryDefaults();
        this.centerMotor.setIdleMode(IdleMode.kBrake);
        this.centerMotor.setOpenLoopRampRate(0.25);

        this.frontMotor = new CANSparkMax(frontMotorID, MotorType.kBrushless);
        this.frontMotor.restoreFactoryDefaults();
        this.frontMotor.setIdleMode(IdleMode.kBrake);
        this.frontMotor.setOpenLoopRampRate(0.25);
    }

    public void set (double speed) {

        //this.backMotor.set(speed);
        this.centerMotor.set(speed);
        this.frontMotor.set(speed);
    }
}
