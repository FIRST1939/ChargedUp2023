package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Manipulator extends SubsystemBase {
    
    private final WPI_TalonFX armMotor;
    private final CANSparkMax scoreMotor;

    public Manipulator () {

        this.armMotor = new WPI_TalonFX(Constants.ManipulatorConstants.ARM_MOTOR);
        this.scoreMotor = new CANSparkMax(Constants.ManipulatorConstants.SCORE_MOTOR, MotorType.kBrushless);

        this.armMotor.configFactoryDefault();
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.armMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.armMotor.configNominalOutputForward(0,30);
        this.armMotor.configNominalOutputReverse(0,30);
        this.armMotor.configPeakOutputForward(1,30);
        this.armMotor.configPeakOutputReverse(-1,30);

        this.scoreMotor.restoreFactoryDefaults();
        this.scoreMotor.setIdleMode(IdleMode.kCoast);
    }

    /**
     * @param velocity encoder clicks per 100ms 
     */
    public void setArm (double velocity) { 
        
        if (Constants.ManipulatorConstants.ARM_MINIMUM_EXTENSION < this.getArmPosition() && Constants.ManipulatorConstants.ARM_MAXIMUM_EXTENSION > this.getArmPosition()) {

            this.armMotor.set(ControlMode.Velocity, velocity); 
        }
    }

    /**
     * @param velocity -1 to 1
     */
    public void setRollers (double velocity) { this.scoreMotor.set(velocity); }

    public double getArmPosition () { return this.armMotor.getSelectedSensorPosition(); }
}
