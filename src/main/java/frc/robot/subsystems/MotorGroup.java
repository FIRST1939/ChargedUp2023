package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorGroup {
    
    public final WPI_TalonSRX backMotor;
    public final WPI_TalonSRX centerMotor;
    public final WPI_TalonSRX frontMotor;

    public MotorGroup (int backMotorID, int centerMotorID, int frontMotorID) {

        this.backMotor = new WPI_TalonSRX(backMotorID);
        this.backMotor.configFactoryDefault();

        this.centerMotor = new WPI_TalonSRX(centerMotorID);
        this.centerMotor.configFactoryDefault();

        this.frontMotor = new WPI_TalonSRX(frontMotorID);
        this.frontMotor.configFactoryDefault();
    }

    public void set (double speed) {

        this.backMotor.set(speed);
        this.centerMotor.set(speed);
        this.frontMotor.set(speed);
    }
}
